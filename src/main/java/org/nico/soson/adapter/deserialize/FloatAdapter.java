package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class FloatAdapter extends DeserializeAdapter<Float> {

	@Override
	public Float toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return Float.valueOf(value);
	}

}
