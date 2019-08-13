package com.shashank.testify;

import java.util.HashMap;
import java.util.Map;

public class HashCodeAndEqualsTest {

	private String language;

	public HashCodeAndEqualsTest(String language) {
		this.language = language;
	}

	public boolean equals(Object obj) {
		HashCodeAndEqualsTest test = (HashCodeAndEqualsTest) obj;
		if (test.language.equals(language))
			return true;
		else
			return false;
	}

	
	//public int hashCode() { return language.hashCode(); }
	
	public static void main(String[] a) {
		HashCodeAndEqualsTest c1 = new HashCodeAndEqualsTest("Java");
		HashCodeAndEqualsTest c2 = new HashCodeAndEqualsTest("Java");
		HashCodeAndEqualsTest c3 = new HashCodeAndEqualsTest("Python");
		Map<HashCodeAndEqualsTest, String> myMap = new HashMap<HashCodeAndEqualsTest, String>();
		myMap.put(c1, c1.language);
		myMap.put(c2, c2.language);
		myMap.put(c3, c3.language);
		System.out.println("Map Size:" + myMap.size());
		System.out.println(c1.equals(c2));
		System.out.println(c2);
		System.out.println(c3);
	}

}
