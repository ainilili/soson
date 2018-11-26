package org.nico.soson.parser.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.parser.exception.UnSupportedException;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ObjectManager {

//	public static ObjectEntity getObjectEntity(Class<?> clazz){
//		if(Map.class.isAssignableFrom(clazz)){
//			return new ObjectEntity(new LinkedHashMap<String, Object>(), Map.class);
//		}else if(Collection.class.isAssignableFrom(clazz)){
//			return new ObjectEntity(new ArrayList<>(), Collection.class);
//		}else{
//			throw new UnSupportedException("Unsupport this class [" + clazz + "] for manager");
//		}
//	}
	
	public static ObjectEntity getMap() {
		return new ObjectEntity(new LinkedHashMap<String, Object>(), Map.class);
	}
	
	public static ObjectEntity getCollection() {
		return new ObjectEntity(new ArrayList<Object>(), List.class);
	}
}
