package org.nico.soson.utils;

public class TimeUtil {

	private long time;
	
	private long start;
	
	private long end;
	
	private String name;
	
	public TimeUtil(String name) {
		this.name = name;
	}
	
	public void start() {
		start = System.nanoTime();
	}
	
	public void end() {
		end = System.nanoTime();
	}
	
	public long diff() {
		return (end - start);
	}
	
	public void increment() {
		time += (end - start)/1000000;
	}
	
	public long time() {
		return time;
	}
	
	public String name() {
		return name;
	}
}
