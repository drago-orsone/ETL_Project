package it.csttech.etltools;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PlaceHolder
 */
public class CsvFileExtractor extends AbstractFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger();
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




	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected Record parseLine (String inputString){

		Record record = new Record();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try{
			List<String> list = parseColumnNames(inputString);

			record.setId(Integer.parseInt(list.get(0)));
			record.setName(list.get(1));
			record.setBirthday(formatter.parse(list.get(2)));
			record.setHeight(Double.parseDouble(list.get(3)));
			record.setMarried(Boolean.parseBoolean(list.get(4)));

		}catch(ParseException pe){
			log.error("Parsing not succeded.");
		}finally{
			return record;
		}

	}
	  
}

