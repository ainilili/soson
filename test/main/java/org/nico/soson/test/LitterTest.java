package org.nico.soson.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.nico.soson.Soson;

public class LitterTest {

	@Test
	public void testMap(){
		StringBuilder builder = new StringBuilder("{\"name\":\"nico\",\"name1\":\"nico\",\"name2\":{\"a\":1,\"b\":[1,2,3]}}");
		long start = System.currentTimeMillis();
		Map<String, Object> map = Soson.toObject(builder.toString(), Map.class);
		long end = System.currentTimeMillis();
		System.out.println("Map测试：");
		System.out.println((end - start) + "ms");
		System.out.println(map);
	}
	
	@Test
	public void testList(){
		StringBuilder builder = new StringBuilder("[{1:1,2:2}]");
		long start = System.currentTimeMillis();
		List<Object> list = Soson.toObject(builder.toString(), List.class);
		long end = System.currentTimeMillis();
		System.out.println("List测试：");
		System.out.println((end - start) + "ms");
		System.out.println(list);
	}


}
