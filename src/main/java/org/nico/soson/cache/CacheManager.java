package org.nico.soson.cache;

import java.lang.reflect.Field;
import java.util.Map;

import org.nico.soson.cache.impl.FieldCache;

public class CacheManager {

	private static ThreadLocal<Cache<Class<?>, Map<String, Field>>> fieldCacheManager = new ThreadLocal<>();
	
	public static Cache<Class<?>, Map<String, Field>> getFieldCacheManager(){
		Cache<Class<?>, Map<String, Field>> cache = fieldCacheManager.get();
		if(cache == null) {
			cache = new FieldCache();
			fieldCacheManager.set(cache);
		}
		return cache;
	}
}
