package org.nico.soson.entity;

public class Locator {

	private LocatorType type;
	
	private int index;

	public Locator(LocatorType type, int index) {
		this.type = type;
		this.index = index;
	}

	public final LocatorType getType() {
		return type;
	}

	public final void setType(LocatorType type) {
		this.type = type;
	}

	public final int getIndex() {
		return index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}
	
}
