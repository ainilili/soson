package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class IntegerAdapter extends DeserializeAdapter<Integer> {

	@Override
	public Integer toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return Integer.valueOf(value);
	}

}
