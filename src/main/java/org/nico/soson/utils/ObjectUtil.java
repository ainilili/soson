package org.nico.soson.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.nico.soson.cache.helper.FieldCacheHelper;
import org.nico.soson.entity.ObjectEntity;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ObjectUtil {

	public static void put(ObjectEntity op, String key, Object value){
		if(op.isBean()) {
			try {
				Field field = FieldCacheHelper.getField(op.getType(), key);
				try {
					field.set(op.getObj(), value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}else {
			((Map)op.getObj()).put(key, value);
		}
	}

	public static void add(ObjectEntity op, Object value){
		((Collection)op.getObj()).add(value);
	}
}
