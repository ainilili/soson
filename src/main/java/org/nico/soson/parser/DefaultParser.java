package org.nico.soson.parser;

import java.util.List;

import org.nico.soson.feature.SerializeFeature;
import org.nico.soson.parser.handler.ParseHandler;
import org.nico.soson.parser.resolve.ClassResolve;
import org.nico.soson.utils.QuotationUtil;

public class DefaultParser extends AbstractParser{

	@Override
	public Object parse(char[] chars, Class<?> clazz, SerializeFeature... features) {
		
		List<Class<?>> dic = new ClassResolve(clazz).excute();
		
		ParseHandler handler = new ParseHandler(dic);
		QuotationUtil qu = new QuotationUtil();
		for(int index = 0; index < chars.length; index ++){
			char c = chars[index];
			qu.check(c);
			if(qu.isClose()){
				switch(c){ 
				case '[':
					handler.handleBracket(c);
					break;
				case '{':
					handler.handleBrace(c);
					break;
				case ']':
					handler.handleUnBracket(c);
					break;
				case '}':
					handler.handleUnBrace(c);
					break;
				case ':':
					handler.handleColon(c);
					break;
				case ',':
					handler.handleComma(c);
					break;
				}
			}else{
				handler.handleChar(c);
			}
		}
		return handler.getTarget();
	}

}
