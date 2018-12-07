package org.nico.soson.parser.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.exception.InstanceException;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */
public class ObjectManager {

	public static ObjectEntity getMap(Genericity generic) {
		return new ObjectEntity(new LinkedHashMap<String, Object>(), Map.class, generic, false);
	}
	
	public static ObjectEntity getCollection(Genericity generic) {
		return new ObjectEntity(new ArrayList<Object>(), List.class, generic, false);
	}
	
	public static ObjectEntity getArray(Class<?> arrayClass, Genericity generic) {
		return new ObjectEntity(new ArrayList<Object>(), List.class, arrayClass, generic, false);
	}
	
	public static ObjectEntity getInstance(Class<?> clazz, Genericity generic) {
		try {
			if(Map.class.isAssignableFrom(clazz)) {
				return getMap(generic);
			}else if(Collection.class.isAssignableFrom(clazz)) {
				return getCollection(generic);
			}else if(clazz.isArray()) {
				return getArray(clazz, generic);
			}else {
				return new ObjectEntity(clazz.newInstance(), clazz, generic, true);
			}
		} catch (InstantiationException e) {
			throw new InstanceException(e);
		} catch (IllegalAccessException e) {
			throw new InstanceException(e);
		}
	}
}
