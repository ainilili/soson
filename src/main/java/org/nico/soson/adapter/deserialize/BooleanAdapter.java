package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class BooleanAdapter extends DeserializeAdapter<Boolean> {

	@Override
	public Boolean toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return Boolean.valueOf(value);
	}

}
