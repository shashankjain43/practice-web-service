package com.shashank.testify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListReverse {
	
	public static void main(String args[]) {

        List<String> books = new ArrayList<String>();
        books.add("Beautiful Code");
        books.add("Clean Code");
        books.add("Working Effectively with Legacy Code");

        System.out.println("Original List: " + books);

        // Easy way to reverse a List in Java, use Collections.reverse()
        // method, use this to reverse ArrayList or LinkedList in
        // production
        Collections.reverse(books);

        System.out.println("Reversed List: " + books);

        // Now, let's try to reverse a List using recursion
        List<String> output = reverseListRecursively(books);
        System.out.println("Reversed list reversed again: " + output);
        
        List<String> output1 = reverseListRecursively(output);
        System.out.println("Reversed list reversed again: " + output1);
    }

	private static List<String> reverseListRecursively(List<String> books) {
		
		if(books.size()==1){
			return books;
		}
		List<String> revList= new ArrayList<String>();
		revList.add(books.get(books.size()-1));
		revList.addAll(reverseListRecursively(books.subList(0, books.size()-1)));
		return revList;
	}
	
	private static List<String> reverseListIteratively(List<String> books) {
		
		if(books.size()==1){
			return books;
		}
		List<String> revList= new ArrayList<String>();
		for(int i=0;i<(books.size()-1)/2;i++){
			swap(books.get(i),books.get(books.size()-i));
		}
		return revList;
	}

	private static void swap(String string1, String string2) {
		
		String temp=string1;
		string1=string2;
		string2=temp;
		
	}


}
