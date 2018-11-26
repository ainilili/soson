package org.nico.soson.utils;

/** 
 * 
 * @author nico
 */

public class QuotationUtil {

	private boolean closeFlag = true;
	
	private char lastValidChar;
	
	public void check(char currentChar) {
		check(lastValidChar, currentChar);
	}
	
	public void check(char preChar, char currentChar){
		if(isClose()) {
			if(lastValidChar == ','
					|| lastValidChar == ':'
					|| lastValidChar == '{'
					|| lastValidChar == '[') {
				if(! CharacterUtil.isWhitespace(currentChar)) {
					if(! CharacterUtil.isQuotation(currentChar) 
							&& ! CharacterUtil.isObjectStartMark(currentChar)) {
						closeFlag = ! closeFlag;
					}else if(CharacterUtil.isQuotation(currentChar)){
						lastValidChar = currentChar;
						closeFlag = ! closeFlag;
					}
				}
			}
			if(! CharacterUtil.isWhitespace(currentChar)) {
				lastValidChar = currentChar;
			}
		}else {
			if(CharacterUtil.isQuotation(lastValidChar)) {
				if(currentChar == lastValidChar
						&& preChar != '\\') {
					closeFlag = ! closeFlag;
				}
			}else {
				if(currentChar == ','
						|| currentChar == ':'
						|| currentChar == '}'
						|| currentChar == ']') {
					closeFlag = ! closeFlag;
					lastValidChar = currentChar;
				}
			}
		}
	}
	
	public boolean isClose(){
		return closeFlag;
	}
}
