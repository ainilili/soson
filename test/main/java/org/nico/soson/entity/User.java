package org.nico.soson.entity;

import java.util.Map;

public class User<K, V>{
	
	private Map<K, V> map;

	public final Map<K, V> getMap() {
		return map;
	}

	public final void setMap(Map<K, V> map) {
		this.map = map;
	}

}
