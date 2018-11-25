package org.nico.soson.parser;

import org.nico.soson.feature.SerializeFeature;

public abstract class AbstractParser {

	public abstract Object parse(char[] chars, Class<?> clazz, SerializeFeature... features);
	
}
