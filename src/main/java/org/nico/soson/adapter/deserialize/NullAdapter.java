package org.nico.soson.adapter.deserialize;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class NullAdapter extends DeserializeAdapter<Object> {

	@Override
	public Object toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
			return adapter(value, Boolean.class, deserializeFeatures);
		}else{
			try{
				return Double.valueOf(value);
			}catch(NumberFormatException e){
				return value;
			}
		}
	}

}
