package org.nico.soson.utils;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Stack;

import org.nico.soson.entity.Complex;
import org.nico.soson.exception.UnSupportedException;
import org.nico.soson.parser.resolve.ClassResolve.Genericity;

public class GenericityUtil {

	public static Genericity parser(Type type) {
		Class<?> clazz = parserType(type);
		if(clazz == Complex.class){
			return parser(((ParameterizedType)type).getActualTypeArguments()[0]);
		}else if(clazz.isArray()){
			Genericity parent = new Genericity();
			parent.setRawType(Array.class);
			parserArray(clazz.getComponentType(), parent);
			return parent;
		}else {
			Genericity parent = new Genericity().setRawType(clazz).setGenericityTags(clazz.getTypeParameters());
			parserByDistribute(type, parent);
			return parent;
		}
	}
	
	private static void parserArray(Class<?> clazz, Genericity parent){
		if(clazz.isArray()){
			Genericity child = new Genericity();
			child.setRawType(Array.class);
			parent.setGenericities(new Genericity[]{child});
			parserArray(clazz.getComponentType(), child);
		}else{
			Genericity child = new Genericity();
			child.setRawType(clazz);
			parent.setGenericities(new Genericity[]{child});
			
			parserByDistribute(clazz.getGenericSuperclass(), child);
		}
	}
	
	private static void parserByDistribute(Type type, Genericity parent){
		if(type instanceof ParameterizedType) {
			ParameterizedType ptype = ((ParameterizedType) type);
			Type[] subTypes = ptype.getActualTypeArguments();
			parent.setGenericities(parserType(subTypes, parent));
			for(int index = 0; index < subTypes.length; index ++) {
				parserByDistribute(subTypes[index], parent.getGenericities()[index]);
			}
		}else if(type instanceof GenericArrayType) {
			Type subType = ((GenericArrayType) type).getGenericComponentType();
			parent.setGenericities(parserType(new Type[] {subType}, parent));
			parserByDistribute(subType, parent.getGenericities()[0]);
		}
	}
	
	private static Genericity[] parserType(Type[] subTypes, Genericity parent) {
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
			
			if(subType instanceof TypeVariable) {
				childs[index] = null;
			}else {
				child.setParent(parent);
				childs[index] = child;
			}
		}
		return childs;
	}
	
	private static Class<?> parserType(Type type){
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
	
	public static void println(Genericity generic){
		Stack<Genericity> stack = new Stack<>();
		stack.push(generic);
		
		while(! stack.isEmpty()){
			Genericity  g = stack.pop();
			System.out.println(g.getRawType());
			
			if(g.getGenericities() != null){
				for(Genericity g1: g.getGenericities()){
					stack.push(g1);
				}
			}
		}
		
	}
}
