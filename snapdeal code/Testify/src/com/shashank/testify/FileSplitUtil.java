package com.shashank.testify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileSplitUtil {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader inFile=new BufferedReader(new FileReader("/home/local/JASPERINDIA/jain.shashank/p2_final_datafix_mv2.sql"));

		String sCurrentLine;
		int lineNumber = 0;
		int outFileNum=1;
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/local/JASPERINDIA/jain.shashank/p1_p2_dada_fix_mv2/p2_final_datafix_mv2_"+outFileNum+".sql")) ;
		out.write("use merchant_view2;\n");
		while ((sCurrentLine = inFile.readLine()) != null) {
			out.write(sCurrentLine);
			out.write("\n");
			lineNumber++;
			if(lineNumber%20000==0){
				out.close();
				outFileNum++;
				out = new BufferedWriter(new FileWriter("/home/local/JASPERINDIA/jain.shashank/p1_p2_dada_fix_mv2/p2_final_datafix_mv2_"+outFileNum+".sql")) ;
				out.write("use merchant_view2;\n");
			}
		}
	   out.close();
	}
	
	

}
