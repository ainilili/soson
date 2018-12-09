package org.nico.soson.test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.nico.noson.Noson;
import org.nico.soson.Soson;
import org.nico.soson.entity.Complex;
import org.nico.soson.entity.Info;
import org.nico.soson.entity.ManyField;
import org.nico.soson.entity.User;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

public class LitterTest {
	
	public static String json = null;
	
	public static int count = 100 * 10000;
	
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
	
//	@Test
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
	}
	
//	@Test
	public void testMoshi() throws IOException{
		
		System.out.println("Moshi：");
		Moshi moshi = new Moshi.Builder().build();
		Type type = Types.newParameterizedType(List.class, Map.class);
		JsonAdapter<List<Map<String, Object>>> adapter = moshi.adapter(type);
		long start = System.nanoTime();
		
		List<Map<String, Object>> list = adapter.fromJson(json);
		
		long end = System.nanoTime();
		System.out.println((end - start)/1000000 + "ms");
		System.out.println(list.get(0));
	}
	
//	@Test
	public void testTest(){
		System.out.println("Test：");
		long start = System.currentTimeMillis();
//		List<Object> list = new ArrayList<Object>(10);
		String str = "a:b:c:d";
		for(int index = 0; index < count; index ++) {
//			Map<Object, Object> map = new HashMap<>();
//			map.put(index, index);
//			list.add(map);
//			new ObjectEntity(new HashMap<>(), Map.class);
			String.valueOf(index).substring(0, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}

//	@Test
	public void testComplex(){
		String json = "{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}}";
		User<String, List<Info<Info<?>[][][]>>> map = null;
		map = Soson.toObject(json, new Complex<User<String, List<Info<Info<?>[][][]>>>>(){});
		
		System.out.println(map.getClass());
		System.out.println(map.getA().getClass());
		System.out.println(map.getA().get("b").getClass());
		System.out.println(map.getA().get("b").get(0).getClass());
		System.out.println(map.getA().get("b").get(0).getE().getClass());
		System.out.println(map.getA().get("b").get(0).getE()[0].getClass());
		System.out.println(map.getA().get("b").get(0).getE()[0][0].getClass());
		System.out.println(map.getA().get("b").get(0).getE()[0][0][0].getC());
	}
	
//	@Test
	public void testMap() {
		String json = "{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}}";
		System.out.println(Soson.toObject(json, Map.class));
	}
	
//	@Test
	public void testManyField(){
		String json = "{a:1,b:2,c:3,d:4,f:5,child:{g:nico,h:oo,u:{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}}}}";
		ManyField mf = Soson.toObject(json, ManyField.class);
		System.out.println(mf);
		System.out.println(mf.getClass());
		System.out.println(mf.getChild().getU().getA().getClass());
		System.out.println(mf.getChild().getU().getA().get("b").getClass());
		System.out.println(mf.getChild().getU().getA().get("b").get(0).getClass());
		System.out.println(mf.getChild().getU().getA().get("b").get(0).getE().getClass());
		System.out.println(mf.getChild().getU().getA().get("b").get(0).getE()[0].getClass());
		System.out.println(mf.getChild().getU().getA().get("b").get(0).getE()[0][0].getClass());
		System.out.println(mf.getChild().getU().getA().get("b").get(0).getE()[0][0][0].getC());
	}
	
//	@Test
	public void testComplexList(){
		String json = "[{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}},{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}}]";
		List<User<String, List<Info<Info<?>[][][]>>>> list = Soson.toObject(json, new Complex<List<User<String, List<Info<Info<?>[][][]>>>>>(){});
		for(User<String, List<Info<Info<?>[][][]>>> map: list){
			System.out.println(map.getClass());
			System.out.println(map.getA().getClass());
			System.out.println(map.getA().get("b").getClass());
			System.out.println(map.getA().get("b").get(0).getClass());
			System.out.println(map.getA().get("b").get(0).getE().getClass());
			System.out.println(map.getA().get("b").get(0).getE()[0].getClass());
			System.out.println(map.getA().get("b").get(0).getE()[0][0].getClass());
			System.out.println(map.getA().get("b").get(0).getE()[0][0][0].getC());
		}
	}
	
//	@Test
	public void testArray(){
		String json = "[[[1,2,3]]]";
		int[][][] is = Soson.toObject(json, int[][][].class);
		System.out.println(is);
	}
	
//	@Test
	public void testList(){
		String json = "[1,2,3]";
		List<Integer> is = Soson.toObject(json, new Complex<List<Integer>>(){});
		System.out.println(is);
	}
	
//	@Test
	public void testperformance(){
		Gson gson = new Gson();
		List<User<String, List<Info<Info<?>[][][]>>>> list = null;
		
		String json = "[{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}},{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}},{a:{b:[{c:d,e:[[[{c:q},{c:q}]]]}]}}]";
		
		long start = System.currentTimeMillis();
		for(int index = 0; index < 1000 * 100; index ++){
			list = Soson.toObject(json, new Complex<List<User<String, List<Info<Info<?>[][][]>>>>>(){});
//			list = gson.fromJson(json, new TypeToken<List<User<String, List<Info<Info<?>[][][]>>>>>(){}.getType());
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
		for(User<String, List<Info<Info<?>[][][]>>> map: list){
			System.out.println(map.getClass());
			System.out.println(map.getA().getClass());
			System.out.println(map.getA().get("b").getClass());
			System.out.println(map.getA().get("b").get(0).getClass());
			System.out.println(map.getA().get("b").get(0).getE().getClass());
			System.out.println(map.getA().get("b").get(0).getE()[0].getClass());
			System.out.println(map.getA().get("b").get(0).getE()[0][0].getClass());
			System.out.println(map.getA().get("b").get(0).getE()[0][0][0].getC());
			break;
		}
	}
	
}
