package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
* @todo. il metodo parse column name non è a prova di errore. Non considera entry tra virgolette come un solo campo e la presenza di ; in un campo rompe la lettura.
*/
public class CsvFileExtractor extends LineWiseFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger(CsvFileExtractor.class.getName());
	private String fieldSeparator;
	private String stringDelimiter;

	/**
	 * [CsvFileExtractor description]
	 * @param   [fileName]
	 * @param   [fieldSeparator]
	 * @param   [stringDelimiter]
	 * @return  [description]
	 */
	public CsvFileExtractor( String fileName, String fieldSeparator, String stringDelimiter){
		super(fileName);
	this.fieldSeparator = fieldSeparator;
	this.stringDelimiter = stringDelimiter;
	}


	/*
	* Cut the input line
	*
	* Si prende pure i campi extra.. il metodo parse line li ignorerà!
	*
	*/
	@Override
	protected List<String> parseColumnNames(String inputLine){
		log.trace("*Parsing* " + inputLine);
		List<String> entryList = Arrays.asList(
			inputLine.split(	stringDelimiter + fieldSeparator + stringDelimiter + "|" +
				stringDelimiter + fieldSeparator + "|" +
				fieldSeparator + stringDelimiter + "|" +
				fieldSeparator + "|" +
				stringDelimiter));
		log.trace("*  in   * " + entryList);
		return entryList ;
	}
}
