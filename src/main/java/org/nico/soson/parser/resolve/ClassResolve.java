package org.nico.soson.parser.resolve;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import org.nico.soson.parser.resolve.ClassResolve.Genericity;
import org.nico.soson.utils.ArrayUtil;
import org.nico.soson.utils.ClassUtil;
import org.nico.soson.utils.GenericityUtil;

public class ClassResolve implements SosonResolve<Genericity>{

	private Class<?> clazz;
	
	private Genericity genericityTree;

	public ClassResolve(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Genericity excute() {
		Type type = clazz.getGenericSuperclass();
		if(type instanceof ParameterizedType) {
			genericityTree = GenericityUtil.parser(type);
		}else {
			genericityTree = GenericityUtil.parser(clazz);
		}
		return genericityTree;
	}
	
//	private Genericity parser(Type type) {
//		if(type == null) {
//			return new Genericity().setRawType(clazz).setGenericityTags(clazz.getTypeParameters());
//		}else {
//			return GenericityUtil.parser(type);
//		}
//	}
	
	public static class Genericity{
		
		private Genericity[] genericities;
		
		private Genericity parent;
		
		private TypeVariable<?>[] genericityTags;
		
		private Class<?> rawType;
		
		private Map<String, Genericity> genericityTagsMap;
		
		private boolean isBean;
		
		private boolean isMap;
		
		private boolean isCollection;
		
		private boolean isArray;
		
		public final Map<String, Genericity> getGenericityTagsMap() {
			return genericityTagsMap;
		}

		public final void setGenericityTagsMap(Map<String, Genericity> genericityTagsMap) {
			this.genericityTagsMap = genericityTagsMap;
		}

		public final boolean isBean() {
			return isBean;
		}

		public final void setBean(boolean isBean) {
			this.isBean = isBean;
		}

		public final Genericity getParent() {
			return parent;
		}

		public final void setParent(Genericity parent) {
			this.parent = parent;
		}

		public final boolean isMap() {
			return isMap;
		}

		public final void setMap(boolean isMap) {
			this.isMap = isMap;
		}

		public final boolean isCollection() {
			return isCollection;
		}

		public final void setCollection(boolean isCollection) {
			this.isCollection = isCollection;
		}

		public final Class<?> getRawType() {
			return rawType;
		}

		public final boolean isArray() {
			return isArray;
		}

		public final void setArray(boolean isArray) {
			this.isArray = isArray;
		}

		public final Genericity setRawType(Class<?> rawType) {
			this.rawType = rawType;
			this.isMap = ClassUtil.isMap(rawType);
			if(! this.isMap) {
				this.isCollection = ClassUtil.isCollection(rawType);
				if(! this.isCollection) {
					this.isArray = Array.class.isAssignableFrom(rawType) || rawType.isArray();
					if(! isArray) {
						this.isBean = ClassUtil.isBean(rawType);
					}
				}
			}
			return this;
		}

		public final TypeVariable<?>[] getGenericityTags() {
			return genericityTags;
		}

		public final Genericity setGenericityTags(TypeVariable<?>[] genericityTags) {
			this.genericityTags = genericityTags;
			return this;
		}

		public final Genericity[] getGenericities() {
			return genericities;
		}

		public final void setGenericities(Genericity[] genericities) {
			this.genericities = genericities;
		}
		
		public final Genericity getTagGenericity(String tag) {
			if(genericityTagsMap != null) {
				return genericityTagsMap.get(tag);
			}else {
				genericityTagsMap = new HashMap<>();
				if(! ArrayUtil.isEmpty(genericityTags)) {
					for(int index = 0; index < genericityTags.length; index ++) {
						String tagName = genericityTags[index].getTypeName();
						Genericity genericity = null;
						if(index < genericities.length) {
							genericity = genericities[index];
						}
						if(genericity != null) {
							genericityTagsMap.put(tagName, genericity);
						}else {
							genericityTagsMap.put(tagName, new Genericity().setRawType(Object.class));
						}
					}
				}
				return genericityTagsMap.get(tag); 
			}
		}

		@Override
		public String toString() {
			return rawType + "";
		}
		
	}
}
