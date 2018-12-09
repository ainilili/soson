package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class DoubleAdapter extends DeserializeAdapter<Double> {

	@Override
	public Double toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return Double.valueOf(value);
	}

}
