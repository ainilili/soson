package org.nico.soson.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.soson.Soson;
import org.nico.soson.utils.QuotationUtil;
import org.xml.sax.Locator;

public class LitterTest {

	public static void main(String[] args) {
		
		StringBuilder builder = new StringBuilder();
		int count = 10 * 100000;
		
		builder.append("{");
		while(count -- > 0) {
			builder.append("\"" + count + "\":{\"name\":\"nico\"}");
			if(count > 0) {
				builder.append(",");
			}
		}
		builder.append("}");
		
		long start = System.currentTimeMillis();
		
		Soson.toObject(builder.toString(), Map.class);
		
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
		
	}             
	
	
}
