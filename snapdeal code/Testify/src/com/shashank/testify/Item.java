package com.shashank.testify;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class Item {
	
	public static void main(String[] args) throws IOException{
		
		CSVReader reader = new CSVReader(
				new FileReader("/home/local/JASPERINDIA/jain.shashank/p2_final_datafix_mv2.csv"), ',', '"', 1);

		//Read all rows at once
		List<String[]> allRows = reader.readAll();
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/local/JASPERINDIA/jain.shashank/p2_final_datafix_mv2.sql")) ;
		 
		//Read CSV line by line and use the string array as you want
		//allRows.remove(0)  ;
	    out.write("use merchant_view2;\n");
	   for(String[] row : allRows){
	     //System.out.println(Arrays.toString(row));
	     String txn_id = row[0];
	     String txn_state = row[1] ;
	     if(txn_state.equalsIgnoreCase("CREATED") ||
	    		 txn_state.equalsIgnoreCase("IN_PROGRESS") ||
	    		 txn_state.equalsIgnoreCase("DEEMED_SUCCESS")){
	    	 txn_state = "PENDING";
	     }
	     if(txn_state.equalsIgnoreCase("ROLLED_BACK")){
	    	 txn_state = "ROLLBACK";
	     }
	     /*if(txn_state.equalsIgnoreCase("SUCCESS")){
	    	 txn_state = "SETTLED";
	     }*/
	     String tsmTimeStamp=row[2] ;
	     SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	     String stringTS=null;
	     try{
	    	 Date ts = new Date(Long.parseLong(tsmTimeStamp));
	         stringTS = sdf.format(ts);
	     }catch(NumberFormatException nfe){
	    	 //tsmTimeStamp=tsmTimeStamp.substring(1, 29);
	    	 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"); 
	    	 Date date=null;
			try {
				date = (Date)formatter.parse(tsmTimeStamp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 SimpleDateFormat sdf1 = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    	 stringTS=sdf1.format(date);
	    	 nfe.printStackTrace();
	     }
	     
	    String i_query = "update txn_state_details set updated_on=now(), tsm_time_stamp = '"+stringTS+"'"+
	    		" where txn_id= '"+txn_id+ "'"+" and txn_state= '"+txn_state+"' ;\n";
	    out.write(i_query);
	   
	   }
	   out.close();
	}
	
	

}
