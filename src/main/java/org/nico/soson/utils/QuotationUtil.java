package org.nico.soson.utils;

/** 
 * 
 * @author nico
 */

public class QuotationUtil {

	private boolean singleFlag = true;
	
	private boolean doubleFlag = true;
	
	public void check(char preChar, char currentChar){
		if(preChar != '\\'){
			if(currentChar == '\''){
				singleFlag = ! singleFlag; 
			}else if(currentChar == '\"'){
				doubleFlag = ! doubleFlag; 
			}
		}
	}
	
	public boolean isClose(){
		return singleFlag && doubleFlag;
	}
}
