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
  	protected String buildTitle(List<String> columnNames){
		return String.format("%s%c%s%c%s%c%s%c%s",
					columnNames.get(0),  FIELD_SEPARATOR,
					columnNames.get(1),  FIELD_SEPARATOR, 
					columnNames.get(2),  FIELD_SEPARATOR,  
					columnNames.get(3),  FIELD_SEPARATOR, 
					columnNames.get(4));
	}

	@Override
  	protected String buildLine(Record record){
		return String.format(Locale.ENGLISH, "%d%c%s%c%td/%tm/%tY%c%f%c%s", 
					record.getId(),  FIELD_SEPARATOR, 
					record.getName(),  FIELD_SEPARATOR, 
					record.getBirthday(), record.getBirthday(), record.getBirthday(),  FIELD_SEPARATOR,
					record.getHeight(),  FIELD_SEPARATOR, 
					record.isMarried());  
	}
}
