package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.util.List;

/**
 * PlaceHolder
 */

public class FwFileLoader extends LineWiseFileLoader implements Loader {

	public FwFileLoader(String file){
		super(file);
	}

  	/*
   	* PlaceHolder 
   	*  Meglio usare string.format()
   	*/
	/*@Override
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

		return String.format("%-20s%-20s%-20s%-20s%-20s%s", 
				columnNames.get(0), columnNames.get(1), columnNames.get(2), columnNames.get(3), columnNames.get(4), FINAL_CHAR);

	}*/

	@Override
  	protected String buildTitle(List<String> columnNames){
		return "";   
	}

	@Override
  	protected String buildLine(Record record){
		return "";   
	}

}

