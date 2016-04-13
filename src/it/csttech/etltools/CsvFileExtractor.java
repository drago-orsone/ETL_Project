package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @todo. il metodo parse column name non è a prova di errore. Non considera entry tra virgolette come un solo campo e la presenza di ; in un campo rompe la lettura.
 */
public class CsvFileExtractor extends LineWiseFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger("Extractor.File.LineWise.Csv");

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
