package org.nico.soson.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.exception.UnSupportedException;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ObjectUtil {

	public static void put(ObjectEntity op, String key, Object value){
		//		if(op.isType(Map.class)){
		((Map)op.getObj()).put(key, value);
		//		}else{
		//			throw new UnSupportedException("Unsupport class " + op.getType() + " to excute put method !");
		//		}
	}

	public static void add(ObjectEntity op, Object value){
		//		if(op.isType(Collection.class)){
		((Collection)op.getObj()).add(value);
		//		}else{
		//			throw new UnSupportedException("Unsupport class " + op.getType() + " to excute add method !");
		//		}
	}
}
