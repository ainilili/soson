package org.nico.soson.test;

import java.util.ArrayList;
import java.util.List;

public class LitterTest {

	public static void main(String[] args) {
		
		List<Integer> braceLocator = new ArrayList<>();
		List<Integer> unbraceLocator = new ArrayList<>();
		List<Integer> bracketLocator = new ArrayList<>();
		List<Integer> unbracketLocator = new ArrayList<>();
		
		StringBuilder builder = new StringBuilder();
		int count = 1000 * 10000;
		
		builder.append("{");
		while(count -- > 0) {
			builder.append("\"" + count + "\":" + count);
			if(count > 0) {
				builder.append(",");
			}
		}
		builder.append("}");
		
		long start = System.currentTimeMillis();
		for(char c: builder.toString().toCharArray()) {
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
