package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ByteAdapter extends DeserializeAdapter<Byte> {

	@Override
	public Byte toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		return Byte.valueOf(value);
	}

}
