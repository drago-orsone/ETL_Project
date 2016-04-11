package it.csttech.etltools;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * PlaceHolder
 */

public class XmlFileLoader extends AbstractFileLoader implements Loader {

	public XmlFileLoader(String file){
		super(file);
	}

  	/*
   	* Return first two lines of xml file
   	* and put tags in a private list of this class
   	*/
	@Override
  	protected String parseColumnNames(List<String> columnNames){

		for(String string : columnNames)
			fields.add(string);

		return "<?xml version=\"1.0\"?>\n<ROWSET>";
	}

  	/*
   	* PlaceHolder 
   	* 
   	*/
   	@Override
  	protected String parseRecord(Record record){

		List<String> recordConverted = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		recordConverted.add(Integer.toString(record.getId()));
		recordConverted.add(record.getName());
		recordConverted.add((formatter.format(record.getBirthday())).toString());
		recordConverted.add(Double.toString(record.getHeight()));
		recordConverted.add(Boolean.toString(record.isMarried()));		

		return "<RAW>\n" + parse(recordConverted) + "</RAW>";
  
  	}

	@Override
	protected String closingRaw(){
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

	private List<String> fields = new ArrayList();

}
