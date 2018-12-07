package org.nico.soson.cache.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.nico.soson.cache.CacheManager;

public class FieldCacheHelper {

	public static Field getField(Class<?> type, String key) {
		Map<String, Field> fieldMap = CacheManager.getFieldCacheManager().get(type);
		return fieldMap.get(key);
	}
	
}
