package org.nico.soson.parser;

import org.nico.soson.feature.SerializeFeature;

public abstract class AbstractParser {

	public abstract Object parse(String json, Class<?> clazz, SerializeFeature... features);
	
}
