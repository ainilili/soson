package org.nico.soson.entity;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ManyField {

	private String a;
	
	private String b;
	
	private String c;
	
	private String d;
	
	private String f;
	
	private ManyFieldChild child;

	public ManyFieldChild getChild() {
		return child;
	}

	public void setChild(ManyFieldChild child) {
		this.child = child;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	@Override
	public String toString() {
		return "ManyField [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", f=" + f + ", child=" + child + "]";
	}
	
}
