package org.nico.soson.parser.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nico.soson.entity.ObjectEntity;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ObjectManager {

	public static ObjectEntity getMap() {
		return new ObjectEntity(new LinkedHashMap<String, Object>(), Map.class);
	}
	
	public static ObjectEntity getCollection() {
		return new ObjectEntity(new ArrayList<Object>(), List.class);
	}
}
