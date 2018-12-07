package org.nico.soson.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.nico.soson.Soson;

public class AllTest {

	@Test
	public void test1() {
		
		String json = "{a:b,l:[a,b,c,{l:c}],w:{n:w}}";
		System.out.println(Soson.toObject(json, Map.class));
	}
}
