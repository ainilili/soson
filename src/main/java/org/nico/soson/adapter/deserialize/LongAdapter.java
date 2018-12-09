package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class LongAdapter extends DeserializeAdapter<Long> {

	@Override
	public Long toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return Long.valueOf(value);
	}

}
