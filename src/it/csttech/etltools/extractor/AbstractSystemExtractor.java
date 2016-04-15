package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

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

	static final Logger log = LogManager.getLogger(AbstractSystemExtractor.class.getName());

	public Records extract(){
		Records records = new Records();

		System.out.println("\nDigit ColumnNames separated by ;");


		String str;


		try{
			BufferedReader br = 
						  new BufferedReader(new InputStreamReader(System.in));
			
			if((str=br.readLine())!=null )
				records.setColumnNames(parseColumnNames(str));
			else
				System.out.println("Format not valid.");

			System.out.println("Insert Record value separated by ; (type .q to conclude)");					
			while((str=br.readLine())!=null && !str.equals(".q")){
				records.addRecord(parseRecord(str));
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
	protected abstract Record parseRecord(String inputLine);

	/*
	 * Abstract method to be specified in the children classes.
	 * Transform the firt line readed to a list of strings 
	 */
	protected abstract List<String> parseColumnNames(String inputLine);

}
