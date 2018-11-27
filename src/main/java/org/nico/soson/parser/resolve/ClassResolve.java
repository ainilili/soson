package org.nico.soson.parser.resolve;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.nico.soson.entity.Complex;

public class ClassResolve implements SosonResolve<List<Class<?>>>{

	private Class<?> clazz;
	
	private List<Class<?>> results;

	public ClassResolve(Class<?> clazz) {
		this.clazz = clazz;
		this.results = new ArrayList<>();
	}
	
	@Override
	public List<Class<?>> excute() {
		lop(clazz.getGenericSuperclass());
		
		System.out.println(results);
		
		return results;
	}
	
	private void lop(Type type) {
		if(type instanceof ParameterizedType) {
			Class<?> curClass = (Class<?>)((ParameterizedType) type).getRawType();
			add(curClass);
			Type[] subTypes = ((ParameterizedType) type).getActualTypeArguments();
			if(subTypes != null && subTypes.length > 0) {
				for(Type subType: subTypes) {
					lop(subType);
				}
			}
		}else if(type instanceof Class){
			add((Class<?>) type);
		}
	}
	
	private void add(Class<?> clazz) {
		if(clazz != Complex.class) {
			results.add(clazz);
		}
	}
}
