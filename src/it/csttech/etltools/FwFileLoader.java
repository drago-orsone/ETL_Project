package it.csttech.etltools;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * PlaceHolder
 */

public class FwFileLoader extends AbstractFileLoader implements Loader {

	public FwFileLoader(String file){
		super(file);
	}

  	/*
   	* PlaceHolder 
   	* 
   	*/
	@Override
  	protected String parseColumnNames(List<String> columnNames){

	  	final int FIXED_WIDTH = 20;
		final char EMPTY_CHAR = ' ';
		final char FINAL_CHAR = '*';

		StringBuilder builder = new StringBuilder();

		for(String string : columnNames ){
			builder.append(string);
			for(int i = 0; i < FIXED_WIDTH - string.length(); i++)
				builder.append(EMPTY_CHAR);
		}
		builder.append(FINAL_CHAR);

		return builder.toString();

	}

  	/*
   	* PlaceHolder 
   	* 
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

