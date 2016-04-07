package it.csttech.etltools;

import java.util.*;

/**
 * PlaceHolder
 */
public class CsvFileExtractor implements Extractor {
	
   private String fileName;

	/*
	 * Transform the readed line to a record
	 */
	protected static List<Record> parseLine (String inputLine){
		String[] parsedLine inputLine.split(	"\";\""	+ "|" + 
								"\";"	+ "|" + 
								";\""	+ "|" + 
								";"	+ "|" + 
								"\"");
		Record output = new Record();
		
		
	}

   
   public CsvFileExtractor( String fileName){
	   this.fileName = fileName ;
   }
	
  /*
   * PlaceHolder 
   * 
   */
  public List<Record> Extract(){
	  
  }
}
