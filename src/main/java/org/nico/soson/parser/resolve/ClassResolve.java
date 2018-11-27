package org.nico.soson.parser.resolve;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nico.soson.entity.Complex;
import org.nico.soson.exception.UnSupportedException;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;

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
		Class<?> clazz = parserType(type);
		if(clazz != Complex.class){
			results.add(new Genericity(new Class<?>[]{clazz}));
		}
		parserByTier(type);
	}
	
	private void parserByTier(Type type){
		if(type instanceof ParameterizedType) {
			Type[] subTypes = ((ParameterizedType) type).getActualTypeArguments();
			if(subTypes != null && subTypes.length > 0) {
				Class<?>[] array = new Class<?>[subTypes.length];
				for(int index = 0; index < subTypes.length; index ++){
					array[index] = parserType(subTypes[index]);
				}
				results.add(new Genericity(array));
				Type lastTypes = subTypes[subTypes.length - 1];
				parserByTier(lastTypes);
			}
		}
	}
	
	private Class<?> parserType(Type type){
		if(type instanceof ParameterizedType) {
			return (Class<?>)((ParameterizedType) type).getRawType();
		}else if(type instanceof Class){
			return (Class<?>) type;
		}else{
			throw new UnSupportedException(type.getClass().getName());
		}
	}
	
	public static class Genericity{
		
		private Class<?>[] genericityArray;

		public Genericity(Class<?>[] genericityArray) {
			this.genericityArray = genericityArray;
		}

		public Class<?>[] getGenericityArray() {
			return genericityArray;
		}

		public void setGenericityArray(Class<?>[] genericityArray) {
			this.genericityArray = genericityArray;
		}

		@Override
		public String toString() {
			return Arrays.toString(genericityArray) + "\n";
		}
		
	}
}
