package org.nico.soson.parser.handler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
import org.nico.soson.parser.resolve.ClassResolve;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;
import org.nico.soson.utils.ArrayUtil;
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
					cur = getInstance(fieldType);
					
					//instancing new genericity tree
					//push new genericity into table
					Type genericType = field.getGenericType();
					if(genericType == null) {
						Genericity genericity = new Genericity();
						genericity.setRawType(field.getType());
						table.push(genericity);
					}else {
						Genericity genericity = GenericityUtil.parser(genericType);
						table.push(genericity);
					}
				}else {
					throw new InstanceException("The field " + pre + " for " + pre.getType() + " was not found !");
				}
			}else {
				cur = getInstance(locaterType);
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
				if(pre.isType(Map.class)){
					ObjectUtil.put(pre, pre.getKey(), cur.getObj());
					model = HandleModel.KEY;
				}else if(pre.isType(Collection.class)){
					ObjectUtil.add(pre, cur.getObj());
					model = HandleModel.VALUE;
				}else if(pre.isBean()) {
					Field field = objectFieldCache.get(pre.getType()).get(pre.getKey());
					try {
						field.set(pre.getObj(), cur.getObj());
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
			return stack.get(0).getObj();
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
		
		Genericity point = table.peek();
		Genericity[] subGenericities = point.getGenericities();
		if(subGenericities != null) {
			if(point.isMap()) {
				table.push(subGenericities[1]);
			}else if(point.isCollection()) {
				table.push(subGenericities[0]);
			}
		}
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
