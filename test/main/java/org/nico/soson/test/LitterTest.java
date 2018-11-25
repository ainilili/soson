package org.nico.soson.test;

import java.util.List;

import org.junit.Test;
import org.nico.noson.Noson;
import org.nico.soson.Soson;

public class LitterTest {
	
	@Test
	public void testMany(){
		int count = 100 * 10000;
		
		StringBuilder builder = new StringBuilder("[");
		for(int index = 0; index < count; index ++){
			builder.append("{\"name" + index + "\":\"nico" + index + "\"}");
			if(index != count - 1){
				builder.append(",");
			}
		}
		builder.append("]");
		System.out.println("长json性能测试：");
		long start = System.currentTimeMillis();
		
//		List<Object> list = Soson.toObject(builder.toString(), List.class);
		List<Object> list = Noson.convert(builder.toString(), List.class);
		
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
		System.out.println(list.get(0));
	}


}
