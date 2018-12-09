package org.nico.soson.entity;

import java.util.List;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ManyFieldChild {

	private String g;
	
	private String h;
	
	private User<String, List<Info<Info<?>[][][]>>> u;

	public User<String, List<Info<Info<?>[][][]>>> getU() {
		return u;
	}

	public void setU(User<String, List<Info<Info<?>[][][]>>> u) {
		this.u = u;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	@Override
	public String toString() {
		return "ManyFieldChild [g=" + g + ", h=" + h + ", u=" + u + "]";
	}

}
