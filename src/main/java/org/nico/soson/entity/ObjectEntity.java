package org.nico.soson.entity;

import java.util.HashMap;
import java.util.Map;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ObjectEntity {

	private Object obj;
	
	private Class<?> type;
	
	private String key;
	
	public ObjectEntity(Object obj, Class<?> type) {
		super();
		this.obj = obj;
		this.type = type;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public boolean isType(Class<?> parent){
		return parent.isAssignableFrom(this.type);
	}

	@Override
	public String toString() {
		return "ObjectEntity [obj=" + obj + ", type=" + type + ", key=" + key + "]";
	}
	
}
