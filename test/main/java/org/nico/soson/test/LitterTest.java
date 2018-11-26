package org.nico.soson.test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.nico.noson.Noson;
import org.nico.soson.Soson;
import org.nico.soson.entity.ObjectEntity;
import org.nico.soson.parser.manager.ObjectManager;
import org.nico.soson.utils.ObjectUtil;
import org.nico.soson.utils.QuotationUtil;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

public class LitterTest {
	
	public static String json = null;
	
	public static int count = 300 * 10000;
	
	static {
		StringBuilder builder = new StringBuilder("[");
		for(int index = 0; index < count; index ++){
			builder.append("{\"name" + index + "\":\"nico" + index + "\"}");
			if(index != count - 1){
				builder.append(",");
			}
		}
		builder.append("]");
		json = builder.toString();
	}
	
//	@Test
	public void testFastJson(){
		System.out.println("FastJson：");
		long start = System.currentTimeMillis();
		
		List<Map<String, Object>> list = JSON.parseObject(json, List.class);
		
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
		System.out.println(list.get(0));
	}
	
	@Test
	public void testGson(){
		Gson gson = new Gson();
		System.out.println("Gson：");
		long start = System.currentTimeMillis();
		
		List<Map<String, Object>> list = gson.fromJson(json, List.class);
		
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}
	
//	@Test
	public void testNoson(){
		Gson gson = new Gson();
		System.out.println("Noson：");
		long start = System.currentTimeMillis();
		
		List<Map<String, Object>> list = Noson.convert(json, List.class);
		
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}
	
//	@Test
	public void testSoson(){
		
		System.out.println("Soson：");
		long start = System.nanoTime();
		
		List<Map<String, Object>> list = Soson.toObject(json, List.class);
		
		long end = System.nanoTime();
		System.out.println((end - start)/1000000 + "ms");
		System.out.println(list.get(0));
	}
	
//	@Test
	public void testTest(){
		System.out.println("Test：");
		long start = System.currentTimeMillis();
		for(int index = 0; index < count; index ++) {
			ObjectEntity entity = ObjectManager.getCollection();
			ObjectUtil.add(entity, index);
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}
	
//	@Test
	public void testQuotationUtil(){
		
		String json = "{a:1321,b:23123,'c':3,\"d\":'456f'}";
		
		QuotationUtil qu = new QuotationUtil();
		char[] chars = json.toCharArray();
		for(int index = 0; index < chars.length; index ++){
			char c = chars[index];
			if(index > 0) {
				qu.check(chars[index - 1], c);
			}else {
				qu.check(c);
			}
			System.out.println(c + "-" + qu.isClose());
		}
		
		System.out.println(Soson.toObject(json, Map.class));
	}

}
