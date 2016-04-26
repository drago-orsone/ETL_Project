package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Properties;

/**
* @todo. il metodo parse column name non è a prova di errore. Non considera entry tra virgolette come un solo campo e la presenza di ; in un campo rompe la lettura.
*/
public class CsvFileExtractor extends LineWiseFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger(CsvFileExtractor.class.getName());
	private String fieldSeparator;
	private String stringDelimiter;

	/**
	 * [CsvFileExtractor description]
	 * @param   String Name of the input file.
	 * @param   String Character delimiting the different fields.
	 * @param   StringCharacter delimiting the string fields. as a String
	 */
	public CsvFileExtractor( String fileName, String fieldSeparator, String stringDelimiter){
		super(fileName);
		this.fieldSeparator = fieldSeparator;
		this.stringDelimiter = stringDelimiter;
	}

	public CsvFileExtractor( Properties properties){
		super(properties.getProperty("inputFile") + ".csv");
		this.fieldSeparator = properties.getProperty("FIELD_SEPARATOR");
		this.stringDelimiter = properties.getProperty("STRING_DELIMITER");
	}


	/**
	 * Split the input line.
	 * It takes eventual extra fields. the following parse method will ignore them.
	 *
	 * @param  inputLine [description]
	 * @return           [description]
	 */
	 @Override
	protected List<String> parseColumnNames(String inputLine){
		log.trace("*Parsing* " + inputLine);
		List<String> entryList = Arrays.asList( inputLine.split(
			stringDelimiter + fieldSeparator + stringDelimiter + "|" +
			stringDelimiter + fieldSeparator + "|" +
			fieldSeparator + stringDelimiter + "|" +
			fieldSeparator + "|" +
			stringDelimiter));
		log.trace("*  in   * " + entryList);
		return entryList ;
	}
}
