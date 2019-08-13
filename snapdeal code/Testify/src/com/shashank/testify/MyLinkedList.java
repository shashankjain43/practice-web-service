package com.shashank.testify;


public class MyLinkedList {
	
	private Node head;
	private int size;
	
	public MyLinkedList() {
		this.head=null;
		size=0;
	}
	
	public void addAtFront(int data){
		Node newNode= new Node(data);
		//if list was empty at beginning
		if(head==null){
			head=newNode;
			size++;
			return;
		}
		newNode.setNext(head);
		head=newNode;
		size++;
	}
	
	public void addAtLast(int data){
		Node newNode= new Node(data);
		//if list was empty at beginning
		if(head==null){
			head=newNode;
			size++;
			return;
		}
		Node temp=head;
		while(temp.getNext()!=null){
			temp=temp.getNext();
		}
		temp.setNext(newNode);
		size++;
	}
	
	public Node removeFromFront(){
		if(size==0){
			System.out.println("Can't remove as List is empty");
			return null;
		}
		Node temp=head;
		head=head.getNext();
		temp.setNext(null);
		size--;
		return temp;
	}
	
	public Node removeFromLast(){
		if(size==0){
			System.out.println("Can't remove as List is empty");
			return null;
		}
		Node prev=head;
		Node temp=head;
		while(temp.getNext()!=null){
			prev=temp;
			temp=temp.getNext();
		}
		prev.setNext(null);
		size--;
		return temp;
	}
	
	public void printList(){
		if(size==0){
			System.out.println("Nothing to print as List is empty");
			return;
		}
		Node temp=head;
		System.out.print("List is: [ ");
		do{
			System.out.print(" "+temp.getData());
			temp=temp.getNext();
		}while(temp!=null);
		System.out.print(" ]");
	}
	
	public void reverseIterative(Node head){
		Node temp=head;
		Node next=null;
		while(head!=null){
			next=head.getNext();
			head.setNext(temp);
			temp=head;
			head=next;
		}
		head=temp;
	}
	
	public static void main(String[] args){
		MyLinkedList list=new MyLinkedList();
		//list.addAtFront(2);
		//list.addAtFront(3);
		/*list.addAtFront(4);
		list.addAtFront(5);
		list.addAtFront(6);
		list.addAtFront(7);
		list.addAtFront(8);
		list.addAtFront(9);*/
		list.printList();
//		list.reverseIterative(list.head);
		/*Node node=list.findNthNodeFromEnd(8,list.head);
		if(node!=null){
			System.out.println("3rd node from end is: "+node.getData());
		}*/
		//Node front=list.removeFromLast();
		Node fNode=list.findFactNode(list.head, 3);
		System.out.println("fact node is: "+fNode.getData());
		list.printList();
	}

	public Node findNthNodeFromEnd(int nodeNoFromLast,Node head) {
		if(head==null){
			return null;
		}
		Node prev=head;
		Node current=head;
		int i=1;
		for(i=1;i<nodeNoFromLast&&current.getNext()!=null;i++){
			current=current.getNext();
		}
		if(i<nodeNoFromLast){
			System.out.println("Not possible as list is shorter than input");
			return null;
		}
		while(current.getNext()!=null){
			current=current.getNext();
			prev=prev.getNext();
		}
		// TODO Auto-generated method stub
		return prev;
	}
	
	public Node findFactNode(Node head,int k){
		Node fNode=null;
		if(head==null){
			return null;
		}
		for(int i=0;head!=null;i++,head=head.getNext()){
			if((i%k)==0){
				fNode=head;
			}
		}
		return fNode;
	}

}
