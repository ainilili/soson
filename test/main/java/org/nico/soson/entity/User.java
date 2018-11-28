package org.nico.soson.entity;

import java.util.Map;

public class User<K, V> {
	
	private Map<K, V>[] map;

	public Map<K, V>[] getMap() {
		return map;
	}

	public void setMap(Map<K, V>[] map) {
		this.map = map;
	}

	
}
