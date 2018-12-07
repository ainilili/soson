package org.nico.soson.entity;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
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
	
	private Genericity generic;
	
	public ObjectEntity(Object obj, Class<?> type, Genericity generic, boolean isBean) {
		this.obj = obj;
		this.type = type;
		this.cast = type;
		this.isBean = isBean;
		this.generic = generic;
	}
	
	public ObjectEntity(Object obj, Class<?> type, Class<?> cast, Genericity generic, boolean isBean) {
		this.obj = obj;
		this.type = type;
		this.cast = cast;
		this.isBean = isBean;
		this.generic = generic;
	}

	public final Genericity getGeneric() {
		return generic;
	}

	public final void setGeneric(Genericity generic) {
		this.generic = generic;
	}

	public final boolean isBean() {
		return isBean;
	}
	
	public final boolean isMap() {
		return Map.class.isAssignableFrom(type);
	}
	
	public final boolean isCollection() {
		return Collection.class.isAssignableFrom(type);
	}
	
	public final boolean isArray() {
		return Array.class.isAssignableFrom(type);
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
	
	public Object target() {
		if(type == cast) {
			return obj;
		}else {
			if(cast.isArray()) {
				Collection<?> list = ((Collection<?>)obj);
				Class<?> c = null;
				for(Object o: list) {
					c = o.getClass();
					break;
				}
				Object array = null;
				if(c != null) {
					array = Array.newInstance(c, list.size());
				}else {
					array = Array.newInstance(cast.getComponentType(), list.size());
				}
				
				int index = 0;
				for(Object obj: list) {
					Array.set(array, index ++, obj);
				}
				return array;
			}else {
				return null;
			}
		}
	}

	@Override
	public String toString() {
		return "ObjectEntity [obj=" + obj + ", type=" + type + ", key=" + key + "]";
	}
	
}
