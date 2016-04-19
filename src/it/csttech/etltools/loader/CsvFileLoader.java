package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.util.Locale;

import java.util.List;

/**
* PlaceHolder
*/
public class CsvFileLoader extends LineWiseFileLoader implements Loader {

	private static final String FIELD_SEPARATOR = ";";

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
  	protected String buildTitle(List<String> columnNames){
		return String.format("%s%s%s%s%s%s%s%s%s",
					columnNames.get(0),  FIELD_SEPARATOR,
					columnNames.get(1),  FIELD_SEPARATOR, 
					columnNames.get(2),  FIELD_SEPARATOR,  
					columnNames.get(3),  FIELD_SEPARATOR, 
					columnNames.get(4));
	}

	@Override
  	protected String buildLine(Record record){
		return String.format(Locale.US, "%2$d%1$s%3$s%1$s%4$td/%4$tm/%4$tY%1$s%5$.02f%1$s%6$s", 
					FIELD_SEPARATOR,
					record.getId(),   
					record.getName(), 
					record.getBirthday(),
					record.getHeight(), 
					record.isMarried());  
	}
}
