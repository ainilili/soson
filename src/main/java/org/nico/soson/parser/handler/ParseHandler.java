package org.nico.soson.parser.handler;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

import org.nico.soson.entity.HandleModel;
import org.nico.soson.entity.KVEntity;
import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.parser.manager.ObjectManager;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;
import org.nico.soson.utils.CharacterUtil;
import org.nico.soson.utils.ObjectUtil;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */
public class ParseHandler {

	private Stack<ObjectEntity> stack;

	private Genericity gtree;

	private HandleModel model;

	private KVEntity key;

	private KVEntity value;
	
	private ObjectEntity pre;
	
	private ObjectEntity cur;
	
	public ParseHandler(Genericity gtree) {
		this.stack = new Stack<>();
		this.gtree = gtree;
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

	/**
	 * handle "{"
	 * 
	 * @param c
	 */
	public void handleBrace(char c){
		pre = stackPeek();
		cur = ObjectManager.getMap();
		stack.push(cur);
		model = HandleModel.KEY;
		if(pre != null){
			if(keyEnough()){
				pre.setKey(keyGet());
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
		handlePop();
	}

	/**
	 * handle "["
	 * 
	 * @param c
	 */
	public void handleBracket(char c){
		pre = stackPeek();
		cur = ObjectManager.getInstance(gtree.getRawType());
		stack.push(cur);
		model = HandleModel.VALUE;
		if(pre != null){
			if(keyEnough()){
				pre.setKey(keyGet());
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
