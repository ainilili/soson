package org.nico.soson.utils;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.Collection;
import java.util.Map;

public class ClassUtil {

	public static boolean isPrimitive(Class<?> clazz) {
		return clazz.isPrimitive();
	}
	
	public static boolean isNumber(Class<?> clazz) {
		return Number.class.isAssignableFrom(clazz)
				|| clazz == byte.class
				|| clazz == char.class
				|| clazz == short.class
				|| clazz == int.class
				|| clazz == long.class
				|| clazz == float.class
				|| clazz == double.class;
	}
	
	public static boolean isBoolean(Class<?> clazz) {
		return Boolean.class.isAssignableFrom(clazz)
				|| boolean.class == clazz;
	}
	
	public static boolean isDate(Class<?> clazz) {
		return Date.class.isAssignableFrom(clazz);
	}
	
	public static boolean isEnum(Class<?> clazz) {
		return Enum.class.isAssignableFrom(clazz);
	}
	
	public static boolean isMap(Class<?> clazz) {
		return Map.class.isAssignableFrom(clazz);
	}
	
	public static boolean isCollection(Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz);
	}
	
	public static boolean isArray(Class<?> clazz) {
		return Array.class.isAssignableFrom(clazz);
	}
	
	public static boolean isBean(Class<?> clazz) {
		return ! isArray(clazz)
				&& ! isCollection(clazz)
				&& ! isMap(clazz)
				&& ! isEnum(clazz)
				&& ! isDate(clazz)
				&& ! isBoolean(clazz)
				&& ! isNumber(clazz);
	}
	
}
