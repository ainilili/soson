package org.nico.soson.entity;

import java.util.Map;

public class User<K, V>{
	
	private Map<K, V> a;

	public final Map<K, V> getA() {
		return a;
	}

	public final void setA(Map<K, V> a) {
		this.a = a;
	}

	

}
