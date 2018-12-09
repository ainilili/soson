package org.nico.soson.adapter.deserialize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.nico.soson.adapter.DeserializeAdapter;
import org.nico.soson.exception.DeserializeException;
import org.nico.soson.feature.DeserializeFeature;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class DateAdapter extends DeserializeAdapter<Date> {

	private static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Date toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures) {
		try {
			return DEFAULT_SDF.parse(value);
		} catch (ParseException e) {
			throw new DeserializeException("yyyy-MM-dd HH:mm:ss not match " + value);
		}
	}

}
