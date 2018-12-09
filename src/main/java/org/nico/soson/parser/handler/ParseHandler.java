package org.nico.soson.parser.handler;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Stack;

import org.nico.soson.cache.helper.FieldCacheHelper;
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

	private Genericity genericRoot;

	private HandleModel model;

	private KVEntity key;

	private KVEntity value;
	
	private ObjectEntity pre;
	
	private ObjectEntity cur;
	
	public ParseHandler(Genericity genericRoot) {
		this.stack = new Stack<>();
		this.genericRoot = genericRoot;
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
		String k = key.value();
		key.clear();
		return k;
	}
	
	public Object valueGet(){
		String v = value.value();
		value.clear();
		return v;
	}
	
	public ObjectEntity getInstance(LocaterType lt) {
		if(pre != null && pre.isBean()) {
			return ObjectManager.getInstance(pre.getType(), null);
		}else {
			if(lt == LocaterType.BRACE) {
				return ObjectManager.getMap(null);
			}else if(lt == LocaterType.BRACKET) {
				return ObjectManager.getCollection(null);
			}else {
				throw new InstanceException("Generic types are instantiated as empty");
			}
		}
	}
	
	public ObjectEntity getInstance(Genericity generic) {
		return ObjectManager.getInstance(generic.getRawType(), generic);
	}
	
	public ObjectEntity getInstance(Class<?> clazz) {
		return ObjectManager.getInstance(clazz, null);
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
		if(pre == null) {
			if(genericRoot.isArray()){
				if(! genericRoot.getRawType().isArray()){
					genericRoot.setRawType(Array.newInstance(genericRoot.getGenericities()[0].getRawType(), 0).getClass());
				}
			}
			cur = genericRoot == null ? getInstance(locaterType) : getInstance(genericRoot);
		}else {
			if(keyEnough()) pre.setKey(keyGet());
			if(pre.isBean()) {
				Field targetField = FieldCacheHelper.getField(pre.getType(), pre.getKey());
				if(targetField != null) {
					Genericity preGenericity = pre.getGeneric();
					Class<?> targetFieldType = targetField.getType();
					Type targetFieldGenericType = targetField.getGenericType();
					if(targetFieldGenericType == null) {
						cur = getInstance(targetFieldType);
					}else if(targetFieldGenericType instanceof TypeVariable){
						Genericity childGenericity = preGenericity.getTagGenericity(((TypeVariable<?>) targetFieldGenericType).getTypeName());
						if(childGenericity.isArray()) {
							if(! childGenericity.getRawType().isArray()){
								childGenericity.setRawType(Array.newInstance(childGenericity.getGenericities()[0].getRawType(), 0).getClass());
							}
						}
						cur = getInstance(childGenericity);
					}else if(targetFieldGenericType instanceof ParameterizedType){
						Genericity genericity = GenericityUtil.parser(targetFieldGenericType);
						Genericity[] childs = genericity.getGenericities();
						for(int index = 0; index < childs.length; index ++) {
							if(childs[index] == null) {
								childs[index] = preGenericity.getTagGenericity(preGenericity.getGenericityTags()[index].getTypeName());
							}
						}
						cur = getInstance(genericity);
					}else if(targetFieldGenericType instanceof Class){
						cur = getInstance(targetFieldType);
					}
				}else {
					throw new InstanceException("The field " + pre + " for " + pre.getType() + " was not found !");
				}
			}else {
				Genericity generic = pre.getGeneric();
				if(generic != null 
						&& generic.getGenericities() != null) {
					if(generic.isArray()) {
						Genericity childGenericity = generic.getGenericities()[0];
						if(childGenericity.isArray()) {
							if(! childGenericity.getRawType().isArray()){
								childGenericity.setRawType(Array.newInstance(childGenericity.getGenericities()[0].getRawType(), 0).getClass());
							}
						}
						cur = getInstance(generic.getGenericities()[0]);
					}else {
						if(pre.isMap()) {
							cur = getInstance(generic.getGenericities()[1]);
						}else if(pre.isCollection()){
							cur = getInstance(generic.getGenericities()[0]);
						}
					}
				}else {
					cur = getInstance(locaterType);
				}
			}
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
				if(pre.isMap()){
					ObjectUtil.put(pre, pre.getKey(), castObj);
					model = HandleModel.KEY;
				}else if(pre.isCollection() || pre.isArray()){
					ObjectUtil.add(pre, castObj);
					model = HandleModel.VALUE;
				}else if(pre.isBean()) {
					Field field = FieldCacheHelper.getField(pre.getType(), pre.getKey());
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
			ObjectUtil.put(stackPeek(), keyGet(), value);
			keyClear();
			valueClear();
			model = HandleModel.KEY;
		}else if(valueEnough()){
			ObjectUtil.add(stackPeek(), value);
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
