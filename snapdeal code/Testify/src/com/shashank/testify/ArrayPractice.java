package com.shashank.testify;

public class ArrayPractice {
	
	public int findOpsForPalindrome(int input[]){
		int steps=0;
		for(int i=0,j=input.length-1;i<j;){
			//when both end are equal, skip both sides
			if(input[i]==input[j]){
				i++;
				j--;
			}
			//when left side is lesser than right, merge on left side
			else if(input[i]<input[j]){
				input[i+1]=input[i]+input[i+1];
				i++;
				steps++;
			}else{//when right side is lesser than left, merge on right side
				input[j-1]=input[j]+input[j-1];
				j--;
				steps++;
			}
		}
		return steps;
	}
	
    
    /*
     * Consider an array with n elements and value of all the elements is zero. 
	We can perform following operations on the array.

    1. Incremental operations:Choose 1 element from the array and increment its value by 1.
    2. Doubling operation: Double the values of all the elements of array.

    We are given desired array target[] containing n elements. 
    Compute and return the smallest possible number of the operations 
    needed to change the array from all zeros to desired array.
     * 
     */
	public int stepsToDesiredArray(int input[]){
		int steps=0;
		//find max element
		/*for(int i=0, max=i;i<input.length;i++){
			if(input[i]>max){
				max=i;
			}
		}*/
		boolean moreWorkRemaining=true;
		while(moreWorkRemaining){
			moreWorkRemaining=false;
			for(int i=0;i<input.length;i++){
				if(input[i]!=0){
					moreWorkRemaining=true;
				}
			}
			if(moreWorkRemaining==false){
				break;
			}
			boolean allEven=true;
			for(int j=0;j<input.length;j++){
				//if found an odd number
				if(input[j]%2==1){
					allEven=false;
					break;
				}
			}
			if(allEven){
				for(int k=0;k<input.length;k++){
					input[k]=input[k]/2;
				}
				steps++;
			}
			for(int l=0;l<input.length;l++){
				if(input[l] > 0 && input[l]%2==1){
					input[l]=input[l]-1;
					steps++;
				}
			}
		}
		
		return steps;
	}
	
	public static void main(String args[]) {
		ArrayPractice ap=new ArrayPractice();
		int input[]={16, 16, 16};
		System.out.println("steps required ,is: "+ap.findOpsForPalindrome(input));
		System.out.println("steps required for making all zeroes: "+ap.stepsToDesiredArray(input));
	}

}
