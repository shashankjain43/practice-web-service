package com.snapdeal.vanila.utils;

public class SymbolTable {

	private static final char[] symbols;

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
			tmp.append(ch);
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);
		symbols = tmp.toString().toCharArray();
	}
	
	public static char getSymbol(int index) throws Exception {
		if(index < 0 || index > 35)
			throw new Exception("invalid index for retrieving symbol");
		
		return symbols[index];
	}

}
