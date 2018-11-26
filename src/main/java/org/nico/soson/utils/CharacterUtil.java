package org.nico.soson.utils;

public class CharacterUtil {

	public static boolean isQuotation(char c) {
		return c == '\'' || c == '"';
	}
	
	public static boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
	
	public static boolean isObjectStartMark(char c) {
		return c == '{' || c == '[';
	}
}
