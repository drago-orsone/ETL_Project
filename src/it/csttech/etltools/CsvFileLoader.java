package it.csttech.etltools;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * PlaceHolder
 */
public class CsvFileLoader extends AbstractFileLoader implements Loader {

	private static final char FIELD_SEPARATOR = ';';

	/*
	 * Constructor
	 */
	public CsvFileLoader( String fileName){
		super(fileName);
	}

  	/*
   	* PlaceHolder 
   	* 
   	*/
  	protected String parseColumnNames(List<String> columnNames){
		StringBuilder builder = new StringBuilder();

		for(String string : columnNames ){
			builder.append( string + FIELD_SEPARATOR);		
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();   
	}


	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected String parseRecord(Record record){
		List<String> recordConverted = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		recordConverted.add(Integer.toString(record.getId()));
		recordConverted.add(record.getName());
		recordConverted.add((formatter.format(record.getBirthday())).toString());
		recordConverted.add(Double.toString(record.getHeight()));
		recordConverted.add(Boolean.toString(record.isMarried()));		

		return parseColumnNames(recordConverted);

	}


}
