package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.util.List;
import java.util.ArrayList;

/**
* PlaceHolder
*/
public class CsvFileLoader extends LineWiseFileLoader implements Loader {

	private static final char FIELD_SEPARATOR = ';';
	private static final char STRING_DELIMETER = '"';

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
	@Override
  	protected String parseColumnNames(List<String> columnNames){
		StringBuilder builder = new StringBuilder();

		for(String string : columnNames){
			builder.append( string + FIELD_SEPARATOR);		
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();   
	}
}
