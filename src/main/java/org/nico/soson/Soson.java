package org.nico.soson;

import org.nico.soson.entity.Complex;
import org.nico.soson.feature.SerializeFeature;
import org.nico.soson.parser.AbstractParser;
import org.nico.soson.parser.DefaultParser;

/**
 * A super fast Json tool
 * 
 * @author nico
 */
public class Soson {
	
	static AbstractParser parser = new DefaultParser();
	
	public static String toString(Object o) {
		return null;
	}
	
	public static <T> T toObject(String json, Class<T> clazz, SerializeFeature... features) {
		return (T) parser.parse(json.toCharArray(), clazz, features);
	}
	
	public static <T> T toObject(String json, Class<T> clazz) {
		return toObject(json, clazz, null);
	}
	
	public static <T> T toObject(String json, Complex<T> complex) {
		return (T) parser.parse(json.toCharArray(), complex.getClass(), null);
	}
}
