package org.nico.soson.entity;

public class KVEntity {

	private StringBuilder builder;
	
	private boolean strFlag;
	
	public KVEntity() {
		this.builder = new StringBuilder();
	}
	
	public void append(char c) {
		builder.append(c);
	}
	
	public void clear() {
		builder.setLength(0);
		strFlag = false;
	}
	
	public int length() {
		return builder.length();
	}
	
	public boolean isStr() {
		return strFlag;
	}
	
	public String value() {
		return builder.toString();
	}
	
	public void setStrFlag(boolean strFlag) {
		this.strFlag = strFlag;
	}

}
