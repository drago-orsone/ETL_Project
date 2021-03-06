package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.util.Locale;
import java.util.Properties;
import java.util.List;

/**
 *
 */
public class CsvFileLoader extends LineWiseFileLoader implements Loader {

	private String fieldSeparator;
	private String stringDelimiter;
	/*
	* Constructor
	*/
	public CsvFileLoader(String fileName, String fieldSeparator, String stringDelimiter){
		super(fileName);
		this.fieldSeparator = fieldSeparator;
		this.stringDelimiter = stringDelimiter;
	}

	public CsvFileLoader(Properties prop){
		super(prop.getProperty("outputFile") + ".csv");
		this.fieldSeparator = prop.getProperty("FIELD_SEPARATOR");
		this.stringDelimiter = prop.getProperty("STRING_DELIMITER");
	}

	/**
	* PlaceHolder
	*
	*/
	@Override
	protected String buildTitle(List<String> columnNames){

		return String.format("%2$s%1$s%3$s%1$s%4$s%1$s%5$s%1$s%6$s",
		fieldSeparator,
		columnNames.get(0),
		columnNames.get(1),
		columnNames.get(2),
		columnNames.get(3),
		columnNames.get(4));
	}

	@Override
	protected String buildLine(Record record){
		return String.format(Locale.US, "%2$d%1$s%3$s%1$s%4$td/%4$tm/%4$tY%1$s%5$.02f%1$s%6$s",
		fieldSeparator,
		record.getId(),
		prepareForPrint(record.getName()),
		record.getBirthday(),
		record.getHeight(),
		record.isMarried());
	}

	protected String prepareForPrint( String word ){
    if (word.contains(stringDelimiter) || word.contains(fieldSeparator)) {
      word = word.replace(stringDelimiter, stringDelimiter+stringDelimiter);
      word = stringDelimiter + word + stringDelimiter;
    }
    return word;
  }

}
