package org.nico.soson.adapter.deserialize;

import java.math.BigInteger;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class BigDecimalAdapter extends DeserializeAdapter<BigInteger> {

	@Override
	public BigInteger toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return new BigInteger(value);
	}

}
