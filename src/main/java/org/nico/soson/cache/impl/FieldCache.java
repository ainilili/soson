package org.nico.soson.cache.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.nico.soson.cache.Cache;
import org.nico.soson.cache.CacheInit;

public class FieldCache implements Cache<Class<?>, Map<String, Field>>, CacheInit<Class<?>>{

	private static Map<Class<?>, Map<String, Field>> cacheMap = new HashMap<>();

	@Override
	public void set(Class<?> key, Map<String, Field> value) {
		cacheMap.putIfAbsent(key, value);
	}

	@Override
	public void clear() {
		cacheMap.clear();
	}

	@Override
	public Map<String, Field> get(Class<?> key) {
		Map<String, Field> target = cacheMap.get(key);
		if(target == null) {
			init(key);
			target = cacheMap.get(key);
		}
		return target;
	}

	@Override
	public void init(Class<?> input) {
		Map<String, Field> map = new HashMap<>();
		loaderFields(input, map);
		cacheMap.put(input, map);
	}
	
	public void loaderFields(Class<?> clazz, Map<String, Field> map) {
		Field[] fields = clazz.getDeclaredFields();
		if(fields != null && fields.length > 0) {
			for(Field f: fields) {
				f.setAccessible(true);
				map.putIfAbsent(f.getName(), f);
			}
		}
		if(clazz.getSuperclass() != null) {
			loaderFields(clazz.getSuperclass(), map);
		}
	}
	
}
