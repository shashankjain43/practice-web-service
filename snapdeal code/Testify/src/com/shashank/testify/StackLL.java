package com.shashank.testify;

public class StackLL {
	
	private Node top;
	private int size;
	
	public StackLL(){
		top=null;
		size=0;
	}
	
	public void push(int data){
		Node newItem = new Node(data);
		if(top==null){
			top=newItem;
		}else{
			newItem.setNext(top);
			top=newItem;
		}
		size++;
		System.out.println("Node pushed with data: "+data);
	}
	
	public Node pop(){
		Node temp=null;
		if(top==null){
			System.out.println("Can't pop as Stack is empty");
		}else{
			temp=top;
			top=top.getNext();
			temp.setNext(null);
			size--;
			System.out.println("Node popped with data: "+temp.getData());
		}
		return temp;
	}
	
	public void printStackLL(){
		if(size==0){
			System.out.println("Nothing to print as Stack is empty");
			return;
		}
		Node temp=top;
		System.out.print("Stack is: [ ");
		do{
			System.out.print(" "+temp.getData());
			temp=temp.getNext();
		}while(temp!=null);
		System.out.print(" ]");
	}
	
	public static void main(String[] args){
		StackLL stack=new StackLL();
		stack.printStackLL();
		stack.push(10);
		//stack.push(20);
		//stack.push(30);
		stack.printStackLL();
		stack.pop();
		stack.printStackLL();
	}
	

}
