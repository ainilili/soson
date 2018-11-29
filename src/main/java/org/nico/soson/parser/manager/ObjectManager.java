package org.nico.soson.parser.manager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.exception.InstanceException;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */
public class ObjectManager {

	public static ObjectEntity getMap() {
		return new ObjectEntity(new LinkedHashMap<String, Object>(), Map.class);
	}
	
	public static ObjectEntity getCollection() {
		return new ObjectEntity(new ArrayList<Object>(), List.class);
	}
	
	public static ObjectEntity getArray() {
		return new ObjectEntity(new ArrayList<Object>(), List.class, Array.class);
	}
	
	public static ObjectEntity getInstance(Class<?> clazz) {
		try {
			if(Map.class.isAssignableFrom(clazz)) {
				return getMap();
			}else if(Collection.class.isAssignableFrom(clazz)) {
				return getCollection();
			}else if(Array.class.isAssignableFrom(clazz)) {
				return getArray();
			}else {
				return new ObjectEntity(clazz.newInstance(), clazz);
			}
		} catch (InstantiationException e) {
			throw new InstanceException(e);
		} catch (IllegalAccessException e) {
			throw new InstanceException(e);
		}
	}
}
