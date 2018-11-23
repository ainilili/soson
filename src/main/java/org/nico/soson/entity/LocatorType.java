package org.nico.soson.entity;

public enum LocatorType {

	BRACE("{"),
	
	UNBRACE("}"),
	
	BRACKET("["),
	
	UNBRACKET("]"),
	
	;
	
	private String value;

	private LocatorType(String value) {
		this.value = value;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}
}
