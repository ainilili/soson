package org.nico.soson.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.cache.helper.FieldCacheHelper;
import org.nico.soson.entity.KVEntity;
import org.nico.soson.entity.ObjectEntity;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ObjectUtil {

	public static void put(ObjectEntity op, String key, KVEntity value){
		Field field = FieldCacheHelper.getField(op.getType(), key);
		put(op, key, getValue(op, value, field));
	}
	
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

	public static void add(ObjectEntity op, KVEntity value){
		add(op, getValue(op, value));
	}
	
	public static Object getValue(ObjectEntity cur, KVEntity value){
		return getValue(cur, value, null);
	}
	
	public static Object getValue(ObjectEntity cur, KVEntity value, Field field){
		Class<?> type = null;
		if(cur.isBean()){
			type = field.getType();
		}else{
			if(cur.getGeneric() != null){
				if(cur.getGeneric().isArray()){
					type = cur.getGeneric().getRawType().getComponentType();
				}
			}else{
				if(value.isStr()){
					type = String.class;
				}else{
					type = null;
				}
			}
		}
		Object target = DeserializeAdapter.adapter(value.value(), type);
		return target;
	}
	
}
