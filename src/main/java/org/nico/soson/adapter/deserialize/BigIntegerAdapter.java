package org.nico.soson.adapter.deserialize;

import java.math.BigDecimal;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class BigIntegerAdapter extends DeserializeAdapter<BigDecimal> {

	@Override
	public BigDecimal toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return new BigDecimal(value);
	}

}
