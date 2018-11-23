package org.nico.soson;

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
	
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String json, Class<T> clazz, SerializeFeature... features) {
		return (T) parser.parse(json, clazz, features);
	}
}
