package it.csttech.etltools;

import java.util.*;

/**
 * PlaceHolder
 */
public class CsvFileExtractor extends BaseCsvFwExtractor implements Extractor {

	private static final char FIELD_SEPARATOR = ';';
	private static final char STRING_DELIMETER = '"';	


	/*
	 * Constructor
	 */
	public CsvFileExtractor( String fileName){
		super(fileName);
	}


	/*
	 * Cut the input line
	 * 
	 * Si prende pure i campi extra.. il metodo parse line li ignorerà!
	 * 
	 */
	@Override
	protected List<String> parseColumnNames(String inputLine){
			return Arrays.asList(
						inputLine.split(	STRING_DELIMETER + FIELD_SEPARATOR + STRING_DELIMETER + "|" + 
											STRING_DELIMETER + FIELD_SEPARATOR + "|" + 
											FIELD_SEPARATOR + STRING_DELIMETER + "|" + 
											FIELD_SEPARATOR + "|" + 
											STRING_DELIMETER));
	}
}
