package org.nico.soson.utils;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.nico.soson.entity.Complex;
import org.nico.soson.exception.UnSupportedException;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;

public class GenericityUtil {

	public static Genericity parser(Type type) {
		Class<?> clazz = parserType(type);
		if(clazz != Complex.class){
			Genericity parent = new Genericity().setRawType(clazz).setGenericityTags(clazz.getTypeParameters());
			parserByTier(type, parent);
			return parent;
		}else {
			return parser(((ParameterizedType)type).getActualTypeArguments()[0]);
		}
	}
	
	public static void parserByTier(Type type, Genericity parent){
		if(type instanceof ParameterizedType) {
			ParameterizedType ptype = ((ParameterizedType) type);
			Type[] subTypes = ptype.getActualTypeArguments();
			parent.setGenericities(parserType(subTypes, parent));
			for(int index = 0; index < subTypes.length; index ++) {
				parserByTier(subTypes[index], parent.getGenericities()[index]);
			}
		}else if(type instanceof GenericArrayType) {
			Type subType = ((GenericArrayType) type).getGenericComponentType();
			parent.setGenericities(parserType(new Type[] {subType}, parent));
			parserByTier(subType, parent.getGenericities()[0]);
		}else if(type instanceof Class) {
			Genericity child = new Genericity();
			child.setRawType((Class<?>)type);
			child.setParent(parent);
			parent.setGenericities(new Genericity[] {child});
		}
	}
	
	public static Genericity[] parserType(Type[] subTypes, Genericity parent) {
		Genericity[] childs = new Genericity[subTypes.length];
		for(int index = 0; index < subTypes.length; index ++) {
			Type subType = subTypes[index];
			Genericity child = new Genericity();
			if(subType instanceof ParameterizedType) {
				Class<?> clazz = (Class<?>) ((ParameterizedType) subType).getRawType();
				child.setRawType(clazz);
				child.setGenericityTags(clazz.getTypeParameters());
			}else if(subType instanceof Class){
				child.setRawType((Class<?>) subType);
			}else if(subType instanceof GenericArrayType){
				child.setRawType(Array.class);
			}
			child.setParent(parent);
			childs[index] = child;
		}
		return childs;
	}
	
	public static Class<?> parserType(Type type){
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
}
