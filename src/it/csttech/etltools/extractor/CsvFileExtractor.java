package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
* @todo. il metodo parse column name non è a prova di errore. Non considera entry tra virgolette come un solo campo e la presenza di ; in un campo rompe la lettura.
*/
public class CsvFileExtractor extends LineWiseFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger(CsvFileExtractor.class.getName());
	private final String fieldSeparator;
	private final String stringDelimiter;
	private final Pattern patternCSV;

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
		this.patternCSV = createCSVPattern();
	}

	public CsvFileExtractor( Properties properties){
		super(properties.getProperty("inputFile") + ".csv");
		this.fieldSeparator = properties.getProperty("FIELD_SEPARATOR");
		this.stringDelimiter = properties.getProperty("STRING_DELIMITER");
		this.patternCSV = createCSVPattern();
	}

	private Pattern createCSVPattern(){
		String simple       = "[^;\"]+";
		String complicated  = "\"([^\"]*(\"\")*)*\"";
		String complete     = complicated + "|" + simple;
		Pattern pattern = Pattern.compile(complete);
		return pattern;
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

		Matcher matcher = patternCSV.matcher(inputLine);

		List<String> entryList = new ArrayList<String>();

		while (matcher.find())
      entryList.add(simplifyWord(matcher.group()));

		log.trace("*  in   * " + entryList);

		return entryList ;
	}

	protected String simplifyWord ( String word ){

		if( word == null) return null;
			if(word.startsWith(stringDelimiter)){
				word = word.substring(stringDelimiter.length(), word.length() - stringDelimiter.length());
				word = word.replace(stringDelimiter+stringDelimiter, stringDelimiter);//working with office
			}
		return word;

	}

}
