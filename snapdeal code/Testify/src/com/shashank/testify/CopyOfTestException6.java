package com.shashank.testify;


public class CopyOfTestException6 {

	public static void main(String[] args) {
        	System.out.println("in main block: "+ foo());
    }
 
    public static int foo() {
    	
    	try{
    		System.out.println("in try block");
    		CopyOfTestException6.foo1();
    		return 1;
    	}catch(Exception e){
    		System.out.println("in catch block");
    		return 2;
    	}finally{
    		System.out.println("in finally block");
    		//return 3;
    		//System.exit(0);
    	}
    	//return 4;
         
    }

	private static void foo1() throws Exception {
		throw new Exception();
	}
}
