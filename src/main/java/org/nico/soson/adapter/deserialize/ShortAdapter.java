package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ShortAdapter extends DeserializeAdapter<Short> {

	@Override
	public Short toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return Short.valueOf(value);
	}

}
