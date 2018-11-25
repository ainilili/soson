package org.nico.soson.parser.handler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.nico.soson.entity.HandleModel;
import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.parser.manager.ObjectManager;
import org.nico.soson.utils.ObjectUtil;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */
public class ParseHandler {

	private Stack<ObjectEntity> stack;

	private List<Class<?>> dic;

	private HandleModel model;

	private StringBuilder keyBuilder;

	private StringBuilder valueBuilder;

	public ParseHandler(List<Class<?>> dic) {
		this.stack = new Stack<>();
		this.dic = dic;
		this.keyBuilder = new StringBuilder();
		this.valueBuilder = new StringBuilder();
	}

	public boolean keyEnough(){
		return keyBuilder.length() > 0;
	}

	public boolean valueEnough(){
		return valueBuilder.length() > 0;
	}

	public void keyClear(){
		keyBuilder.setLength(0);
	}

	public void valueClear(){
		valueBuilder.setLength(0);
	}
	
	public String keyGet(){
		String key = keyBuilder.toString().trim();
		
		return keyBuilder.toString();
	}
	
	public Object valueGet(){
		return valueBuilder.toString();
	}

	/**
	 * handle "{"
	 * 
	 * @param c
	 */
	public void handleBrace(char c){
		ObjectEntity preObj = stackPeek();
		stack.add(ObjectManager.getObjectEntity(Map.class));
		model = HandleModel.KEY;
		if(preObj != null){
			if(keyEnough() && preObj.isType(Map.class)){
				preObj.setKey(keyGet());
				keyClear();
			}
		}
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
		if(stack.size() > 1){
			ObjectEntity curObj = stackPop();
			ObjectEntity preObj = stackPeek();
			if(preObj != null){
				if(preObj.getKey() != null && preObj.isType(Map.class)){
					ObjectUtil.put(preObj, preObj.getKey(), curObj.getObj());
				}else if(preObj.isType(Collection.class)){
					ObjectUtil.add(preObj, curObj.getObj());
				}
			}
		}
	}

	/**
	 * handle "["
	 * 
	 * @param c
	 */
	public void handleBracket(char c){
		ObjectEntity preObj = stackPeek();
		stack.add(ObjectManager.getObjectEntity(Collection.class));
		model = HandleModel.VALUE;
		if(preObj != null){
			if(keyEnough() && preObj.isType(Map.class)){
				preObj.setKey(keyGet());
				keyClear();
			}
		}
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
		if(stack.size() > 1){
			ObjectEntity curObj = stackPop();
			ObjectEntity preObj = stackPeek();
			if(preObj != null){
				if(preObj.getKey() != null && preObj.isType(Map.class)){
					ObjectUtil.put(preObj, preObj.getKey(), curObj.getObj());
				}else if(preObj.isType(Collection.class)){
					ObjectUtil.add(preObj, curObj.getObj());
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
			keyBuilder.append(c);
		}else if(model == HandleModel.VALUE){
			valueBuilder.append(c);
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
		ObjectEntity curObj = stackPeek();
		if(keyEnough() && valueEnough() && curObj.isType(Map.class)){
			ObjectUtil.put(curObj, keyGet(), valueGet());
			keyClear();
			valueClear();
			model = HandleModel.KEY;
		}else if(valueEnough() && curObj.isType(Collection.class)){
			ObjectUtil.add(curObj, valueGet());
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
			return stack.pop();
		}else{
			return null;
		}
	}

	public ObjectEntity stackPeek(){
		if(! stack.isEmpty()){
			return stack.peek();
		}else{
			return null;
		}
	}
	
}
