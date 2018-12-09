package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class EnumAdapter extends DeserializeAdapter<Enum<?>> {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enum<?> toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
		return Enum.valueOf(enumClass, value);
	}


}
