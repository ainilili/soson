package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class StringAdapter extends DeserializeAdapter<String> {

	@Override
	public String toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return value;
	}

}
