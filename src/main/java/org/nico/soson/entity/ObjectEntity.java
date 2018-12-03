package org.nico.soson.entity;

import java.lang.reflect.Type;
import java.util.Map;

import org.nico.soson.parser.resolve.ClassResolve.Genericity;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ObjectEntity {

	private Object obj;
	
	private Class<?> type;
	
	private Class<?> cast;
	
	private String key;
	
	private boolean isBean;
	
	public ObjectEntity(Object obj, Class<?> type, boolean isBean) {
		this.obj = obj;
		this.type = type;
		this.cast = type;
		this.isBean = isBean;
	}
	
	public ObjectEntity(Object obj, Class<?> type, Class<?> cast, boolean isBean) {
		this.obj = obj;
		this.type = type;
		this.cast = cast;
		this.isBean = isBean;
	}

	public final boolean isBean() {
		return isBean;
	}

	public final void setBean(boolean isBean) {
		this.isBean = isBean;
	}

	public final Class<?> getCast() {
		return cast;
	}

	public final void setCast(Class<?> cast) {
		this.cast = cast;
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
