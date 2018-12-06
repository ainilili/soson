package org.nico.soson.parser.handler;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;

import org.nico.soson.cache.Cache;
import org.nico.soson.cache.ObjectFieldCache;
import org.nico.soson.entity.HandleModel;
import org.nico.soson.entity.KVEntity;
import org.nico.soson.entity.LocaterType;
import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.exception.InstanceException;
import org.nico.soson.exception.ReflectException;
import org.nico.soson.parser.manager.ObjectManager;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;
import org.nico.soson.utils.CharacterUtil;
import org.nico.soson.utils.GenericityUtil;
import org.nico.soson.utils.ObjectUtil;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */
public class ParseHandler {

	private Stack<ObjectEntity> stack;

	private Stack<Genericity> table;

	private HandleModel model;

	private KVEntity key;

	private KVEntity value;
	
	private ObjectEntity pre;
	
	private ObjectEntity cur;
	
	private Cache<Class<?>, Map<String, Field>> objectFieldCache = new ObjectFieldCache();
	
	public ParseHandler(Genericity g) {
		this.stack = new Stack<>();
		this.table = new Stack<>();
		this.table.push(g);
		this.key = new KVEntity();
		this.value = new KVEntity();
	}

	public boolean keyEnough(){
		return key.length() > 0;
	}

	public boolean valueEnough(){
		return value.length() > 0;
	}

	public void keyClear(){
		key.clear();
	}

	public void valueClear(){
		value.clear();
	}
	
	public String keyGet(){
		return key.value();
	}
	
	public Object valueGet(){
		return value.value();
	}
	
	public ObjectEntity getInstance(LocaterType lt) {
		if(pre != null && pre.isBean()) {
			return ObjectManager.getInstance(pre.getType());
		}else {
			if(lt == LocaterType.BRACE) {
				return ObjectManager.getMap();
			}else if(lt == LocaterType.BRACKET) {
				return ObjectManager.getCollection();
			}else {
				throw new InstanceException("Generic types are instantiated as empty");
			}
		}
	}
	
	public ObjectEntity getInstance(Class<?> clazz) {
		return ObjectManager.getInstance(clazz);
	}

	/**
	 * handle "{"
	 * 
	 * @param c
	 */
	public void handleBrace(char c){
		model = HandleModel.KEY;
		handlePush(LocaterType.BRACE);
	}

	/**
	 * handle "}"
	 * 
	 * @param c
	 */
	public void handleUnBrace(char c){
		if(keyEnough()){
			handleComma(c);
		}
		handlePop();
	}

	/**
	 * handle "["
	 * 
	 * @param c
	 */
	public void handleBracket(char c){
		model = HandleModel.VALUE;
		handlePush(LocaterType.BRACKET);
	}
	
	/**
	 * handle push
	 */
	public void handlePush(LocaterType locaterType) {
		//current genericity is java bean
		//special handle --> get java bean field type
		pre = stackPeek();
		if(pre != null){
			if(keyEnough()){
				pre.setKey(keyGet());
				keyClear();
			}
			if(pre.isBean()) {
				Field field = objectFieldCache.get(pre.getType()).get(pre.getKey());
				if(field != null) {
					Class<?> fieldType = field.getType();
					
					//instancing new genericity tree
					//push new genericity into table
					Type genericType = field.getGenericType();
					if(genericType == null) {
						cur = getInstance(fieldType);
						table.push(new Genericity().setRawType(fieldType));
					}else if(genericType instanceof TypeVariable){
						Genericity g = table.peek().getTagGenericity(((TypeVariable)genericType).getTypeName());
						if(g.isArray()) {
							cur = getInstance(Array.newInstance(g.getGenericities()[0].getRawType(), 0).getClass());
						}else {
							cur = getInstance(g.getRawType());
						}
						table.push(g);
					}else {
						cur = getInstance(fieldType);
						Genericity genericity = GenericityUtil.parser(genericType);
						Genericity[] childs = genericity.getGenericities();
						for(int index = 0; index < childs.length; index ++) {
							if(childs[index].getRawType() == null) {
								childs[index] = table.peek().getTagGenericity(table.peek().getGenericityTags()[index].getTypeName());
							}
						}
						table.push(genericity);
					}
				}else {
					throw new InstanceException("The field " + pre + " for " + pre.getType() + " was not found !");
				}
			}else {
				if(table.peek().isBean()) {
					cur = getInstance(table.peek().getRawType());
				}else {
					Genericity g = table.peek();
					if(g.getGenericities() != null) {
						Genericity target = null;
						if(g.getGenericities().length == 1) {
							target = g.getGenericities()[0];
						}else if(g.getGenericities().length == 2){
							target = g.getGenericities()[1];
						}
						if(target != null) {
							if(target.isArray()) {
								cur = getInstance(Array.newInstance(target.getGenericities()[0].getRawType(), 0).getClass());
							}else {
								cur = getInstance(target.getRawType());
							}
						}
						table.push(target);
					}else {
						cur = getInstance(locaterType);
					}
				}
			}
		}else {
			cur = getInstance(table.peek().getRawType());
		}
		stackPush(cur);
	}

	/**
	 * handle "]"
	 * 
	 * @param c
	 */
	public void handleUnBracket(char c){
		if(valueEnough()){
			handleComma(c);
		}
		handlePop();
	}
	
	/**
	 * handle pop
	 */
	public void handlePop() {
		if(stack.size() > 1){
			cur = stackPop();
			pre = stackPeek();
			if(pre != null){
				Object castObj = cur.target();
				
				if(pre.isType(Map.class)){
					ObjectUtil.put(pre, pre.getKey(), castObj);
					model = HandleModel.KEY;
				}else if(pre.isType(Collection.class)){
					ObjectUtil.add(pre, castObj);
					model = HandleModel.VALUE;
				}else if(pre.isBean()) {
					Field field = objectFieldCache.get(pre.getType()).get(pre.getKey());
					try {
						field.set(pre.getObj(), castObj);
					} catch (IllegalArgumentException e) {
						throw new ReflectException(e);
					} catch (IllegalAccessException e) {
						throw new ReflectException(e);
					}
				}
			}
		}
	}

	/**
	 * handle normal char
	 * 
	 * @param c
	 */
	public void handleChar(char c){
		if(model == HandleModel.KEY){
			if(key.length() == 0) {
				if(CharacterUtil.isQuotation(c)) {
					key.setStrFlag(true);
					return;
				}
			}
			key.append(c);
		}else if(model == HandleModel.VALUE){
			if(value.length() == 0) {
				if(CharacterUtil.isQuotation(c)) {
					return;
				}
			}
			value.append(c);
		}
	}

	/**
	 * handle ":"
	 * 
	 * @param c
	 */
	public void handleColon(char c){
		model = HandleModel.VALUE;
	}

	/**
	 * handle ","
	 * 
	 * @param c
	 */
	public void handleComma(char c){
		if(keyEnough()){
			ObjectUtil.put(stackPeek(), keyGet(), valueGet());
			keyClear();
			valueClear();
			model = HandleModel.KEY;
		}else if(valueEnough()){
			ObjectUtil.add(stackPeek(), valueGet());
			valueClear();
			model = HandleModel.VALUE;
		}
	}

	public Object getTarget(){
		if(! stack.isEmpty()){
			return stack.get(0).target();
		}else{
			return null;
		}
	}

	public ObjectEntity stackPop(){
		if(! stack.isEmpty()){
			if(! table.peek().isBean()) {
				table.pop();
			}
			return stack.pop();
		}else{
			return null;
		}
	}
	
	public ObjectEntity stackPush(ObjectEntity oe) {
		stack.push(oe);
		return oe;
	}

	public ObjectEntity stackPeek(){
		if(! stack.isEmpty()){
			return stack.peek();
		}else{
			return null;
		}
	}
	
}
