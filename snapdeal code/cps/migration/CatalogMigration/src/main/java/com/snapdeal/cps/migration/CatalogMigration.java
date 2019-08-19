package com.snapdeal.cps.migration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class CatalogMigration {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Define a binary semaphore file
		File lockFile = new File("./appdata/run.lock");
		try {
			
			// Check if lock already exists
			if(lockFile.exists()){
				System.out.println("A publisher is already running. Exiting ..");
				System.exit(1);
			}
			
			// Create lock and continue
			lockFile.createNewFile();
			
			// Load Properties
			Properties props = new Properties();
			props.load(CatalogMigration.class.getClassLoader().getResourceAsStream("migration.properties"));
			//props.load(new FileInputStream("./appdata/properties/user.properties"));

			// Create Mongo Connection
			MongoClient mongoClient = new MongoClient(props.getProperty("MONGO_HOSTNAME"), 
												Integer.parseInt(props.getProperty("MONGO_PORT"))
											);

			DB mongoDatabase = mongoClient.getDB(props.getProperty("MONGO_DATABASE"));
			
			// Get env value from cmdline args
			String env = args[0];
			int limit = Integer.parseInt(args[1]);
			
			boolean enableDryRun = true;
			if("prod".equals(env)){
				enableDryRun = false;
			}
			
			// Create and Start threads
			ExecutorService exeService = Executors.newFixedThreadPool(10);
			ArrayList<Future<HashMap<String, Integer>>> results = new ArrayList<Future<HashMap<String, Integer>>>();
			
			for(int i=0;i<10;i++){
				Callable<HashMap<String,Integer>> publisher = new PublisherThread(mongoDatabase, i, limit, enableDryRun, props);
				Future<HashMap<String, Integer>> resultMap = exeService.submit(publisher);
				results.add(resultMap);
			}
			
			// Retrieve the results
			for(Future<HashMap<String,Integer>> result: results){
				System.out.println(result.get());
			}
			
			exeService.shutdown();
			
		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			lockFile.delete();
		}
	}

}
