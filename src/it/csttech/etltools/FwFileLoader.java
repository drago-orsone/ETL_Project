package it.csttech.etltools;

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
  	protected String parseRecord(Record record){

	  	final int FIXED_WIDTH = 20;
		final char EMPTY_CHAR = ' ';
		final char FINAL_CHAR = '*';
		String temp;
		StringBuilder builder = new StringBuilder();

		temp = Integer.toString(record.getId());
		builder.append(temp);
		for(int i = 0; i < FIXED_WIDTH - temp.length(); i++)
			builder.append(EMPTY_CHAR);

		temp = record.getName();
		builder.append(temp);
		for(int i = 0; i < FIXED_WIDTH - temp.length(); i++)
			builder.append(EMPTY_CHAR);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		temp = (formatter.format(record.getBirthday())).toString();
		builder.append(temp);
		for(int i = 0; i < FIXED_WIDTH - temp.length(); i++)
			builder.append(EMPTY_CHAR);

		temp = Double.toString(record.getHeight());
		builder.append(temp);
		for(int i = 0; i < FIXED_WIDTH - temp.length(); i++)
			builder.append(EMPTY_CHAR);

		builder.append(FINAL_CHAR);
		return builder.toString();
  
  	}

}

