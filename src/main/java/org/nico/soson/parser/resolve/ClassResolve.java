package org.nico.soson.parser.resolve;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nico.soson.entity.Complex;
import org.nico.soson.exception.UnSupportedException;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;

import com.squareup.moshi.internal.Util.GenericArrayTypeImpl;

public class ClassResolve implements SosonResolve<List<Genericity>>{

	private Class<?> clazz;
	
	private List<Genericity> results;

	public ClassResolve(Class<?> clazz) {
		this.clazz = clazz;
		this.results = new ArrayList<>();
	}
	
	@Override
	public List<Genericity> excute() {
		parser(clazz.getGenericSuperclass());
		
		System.out.println(results);
		
		return results;
	}
	
	private void parser(Type type) {
		if(type != null) {
			Class<?> clazz = parserType(type);
			if(clazz != Complex.class){
				results.add(new Genericity().setGenericityTypes(new Type[]{type}));
			}
			parserByTier(type);
		}
	}
	
	private void parserByTier(Type type){
		if(type instanceof ParameterizedType) {
			Type[] subTypes = ((ParameterizedType) type).getActualTypeArguments();
			if(subTypes != null && subTypes.length > 0) {
				results.add(new Genericity().setGenericityTypes(subTypes));
				Type lastTypes = subTypes[subTypes.length - 1];
				parserByTier(lastTypes);
			}
		}
	}
	
	private Class<?> parserType(Type type){
		System.out.println(type);
		if(type instanceof ParameterizedType) {
			return (Class<?>)((ParameterizedType) type).getRawType();
		}else if(type instanceof Class){
			return (Class<?>) type;
		}else if(type instanceof GenericArrayType){
			Type t = ((GenericArrayType) type).getGenericComponentType();
			return parserType(t);
		}else if(type instanceof TypeVariable){
			throw new UnSupportedException(type.getClass().getName());
		}else{
			throw new UnSupportedException(type.getClass().getName());
		}
	}
	
	public static class Genericity{
		
		private Type[] genericityTypes;
		
		public Type[] getGenericityTypes() {
			return genericityTypes;
		}

		public Genericity setGenericityTypes(Type[] genericityTypes) {
			this.genericityTypes = genericityTypes;
			return this;
		}

		@Override
		public String toString() {
			return Arrays.toString(genericityTypes) + "\n";
		}
		
	}
}
