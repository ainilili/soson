package org.nico.soson.adapter;

import java.util.Map;

import org.nico.soson.adapter.deserialize.*;
import org.nico.soson.exception.DeserializeException;
import org.nico.soson.feature.DeserializeFeature;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public abstract class DeserializeAdapter<T> extends AbstractAdapter{

	public abstract T toObject(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures);
	
	private final static Map<Class<?>, DeserializeAdapter<?>> UN_SERIALIZE_FACTORY = new HashMap<Class<?>, DeserializeAdapter<?>>(){
		private static final long serialVersionUID = 1L;
		{
			put(Byte.class, new ByteAdapter());
			put(Short.class, new ShortAdapter());
			put(Integer.class, new IntegerAdapter());
			put(Long.class, new LongAdapter());
			put(Float.class, new FloatAdapter());
			put(Double.class, new DoubleAdapter());
			
			put(byte.class, new ByteAdapter());
			put(short.class, new ShortAdapter());
			put(int.class, new IntegerAdapter());
			put(long.class, new LongAdapter());
			put(float.class, new FloatAdapter());
			put(double.class, new DoubleAdapter());
			
			put(BigDecimal.class, new BigDecimalAdapter());
			put(BigInteger.class, new BigIntegerAdapter());
			put(Boolean.class, new BooleanAdapter());
			put(boolean.class, new BooleanAdapter());
			put(Date.class, new DateAdapter());
			put(Enum.class, new EnumAdapter());
			
			put(null, new NullAdapter());
			put(String.class, new StringAdapter());
		}};
		
	public static Object adapter(String value, Class<?> clazz, DeserializeFeature... deserializeFeatures){
		Class<?> key = null;
		if(clazz != null){
			key = clazz.isEnum() ? Enum.class : clazz;
		}
		DeserializeAdapter<?> da = UN_SERIALIZE_FACTORY.get(key);
		if(da == null){
			throw new DeserializeException("Not have " + clazz + " deserialize adapter!");
		}else{
			return da.toObject(value, clazz, deserializeFeatures);
		}
	}
}
