package it.csttech.etltools;

import java.util.List;

/**
 * PlaceHolder
 */

public class FwFileLoader extends BaseCsvFwLoader implements Loader {

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
}

