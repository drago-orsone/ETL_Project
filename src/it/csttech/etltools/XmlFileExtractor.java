package it.csttech.etltools;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.BufferedReader;
import java.lang.IllegalArgumentException;
import java.io.IOException;


/**
 * PlaceHolder
 */
public class XmlFileExtractor extends AbstractFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger();

	/*
	 * Constructor
	 */
	public XmlFileExtractor(String file){
		super(file);
	}

	/*
	 * Cut the imput line
	 */

	@Override
	protected Records buildRecords(BufferedReader br) throws IOException {

		final String END_FILE = "</ROWSET>";

		Records records = new Records();
		initFields(records, br);

		String inputLine = null;
		while (!(br.readLine().equals(END_FILE)))
			records.addRecord(parseLine(records.getColumnNames(), br));

		return records;
	}

	private Record parseLine (List<String> columnNames, BufferedReader br) throws IOException {
	
	    Record record = new Record();
	    final String END_ROW = "</ROW>";

	    try{
		String string;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
		while (!((string = br.readLine()).equals(END_ROW))){

			if(string.contains("<" + columnNames.get(0) + ">")) {
				record.setId(Integer.parseInt(string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"))));
	
			}else if(string.contains("<" + columnNames.get(1) + ">")) {
				record.setName(string.substring(string.indexOf(">") + 1, string.lastIndexOf("<")));

			}else if(string.contains("<" + columnNames.get(2) + ">")) {
				record.setBirthday(formatter.parse(string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"))));

			}else if(string.contains("<" + columnNames.get(3) + ">")) {
				record.setHeight(Double.parseDouble(string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"))));

			}else if(string.contains("<" + columnNames.get(4) + ">")) {
				record.setMarried(Boolean.parseBoolean(string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"))));
			}else{
				continue;
			}
			
		}
	    }catch(ParseException pe){
		log.error(pe);
	    }finally{
			return record;
	    }
	}

	private void initFields(Records records, BufferedReader br) throws IOException {

	    try{
		final int MAX_NUMBER_OF_FIELDS = 10;
		final String END_RAW = "</ROW>";

		String string;

		br.mark(MAX_NUMBER_OF_FIELDS);

		for(int j = 0; j < 3; j++)
			br.readLine();
		
		while (!((string = br.readLine()).equals(END_RAW))){
			records.addColumnName(string.substring(string.indexOf("<") + 1, string.indexOf(">")));
		}
		
		br.reset();
	    }catch(IllegalArgumentException iae){
		log.error(iae);
	    }catch(IOException ioe){
		log.error(ioe);
	    }
	}
}
