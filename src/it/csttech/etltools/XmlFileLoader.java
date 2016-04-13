package it.csttech.etltools;

import java.util.*;
import java.text.SimpleDateFormat;
import java.io.PrintWriter;

/**
 * PlaceHolder
 */

public class XmlFileLoader extends AbstractFileLoader implements Loader {

	private List<String> fields = new ArrayList<String>();

	public XmlFileLoader(String file){
		super(file);
	}


	@Override
	protected void printRecords(PrintWriter printWriter, Records records){

		printWriter.println(firstRaws(records.getColumnNames()));

		for(Record record : records.getRecords())
			printWriter.println(parseRecord(record));

		printWriter.print(closingRaw());

	}

  	/*
   	* Return first two lines of xml file
   	* and put tags in a private list of this class
   	*/
  	private String firstRaws(List<String> columnNames){

		for(String string : columnNames)
			fields.add(string);

		return "<?xml version=\"1.0\"?>\n<ROWSET>";
	}

  	/*
   	* PlaceHolder 
   	* 
   	*/
  	private String parseRecord(Record record){

		List<String> recordConverted = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		recordConverted.add(Integer.toString(record.getId()));
		recordConverted.add(record.getName());
		recordConverted.add((formatter.format(record.getBirthday())).toString());
		recordConverted.add(Double.toString(record.getHeight()));
		recordConverted.add(Boolean.toString(record.isMarried()));		

		return "<ROW>\n" + parse(recordConverted) + "</ROW>";
  
  	}

	private String closingRaw(){
		return "</ROWSET>";
	}

	private String parse(List<String> raw) {

		StringBuilder builder = new StringBuilder();
		int j = 0;

		for(String string : raw){
			builder.append("<" + fields.get(j) + ">" + string + "</" + fields.get(j) + ">\n");
			j++;
		}

		return builder.toString();
	}


}
