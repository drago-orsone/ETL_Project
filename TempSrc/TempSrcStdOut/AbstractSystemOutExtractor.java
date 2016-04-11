package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * 
 */
public abstract class AbstractSystemExtractor {

	static final Logger log = LogManager.getLogger();

	public Records extract(){
		Records records = new Records();

		String str;


		try{
			BufferedReader br = 
						  new BufferedReader(new InputStreamReader(System.in));


			System.out.println("Insert Column Names");					
			while((str=br.readLine())!=null){
				System.out.println(str);
				records.addRecord(parseLine(str));
			}
				
		}catch(IOException io){
			io.printStackTrace();
		}	

		return records;

	}

	/*
	 * Abstract method to be specified in the children classes.
	 * Transform the readed line to a record
	 */
	protected abstract Record parseLine(String inputLine);


}
