package it.csttech.etltools;

import java.text.SimpleDateFormat;

/**
 * PlaceHolder
 */
public class CsvFileLoader extends AbstractFileLoader implements Loader {

	/*
	 * Constructor
	 */
	public CsvFileLoader( String fileName){
		super(fileName);
	}


	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected String parseRecord(Record record){
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		builder.append(record.getId() + ";");
		builder.append("\"" + record.getName() + "\""+";");
		builder.append((formatter.format(record.getBirthday())).toString() + ";");
		builder.append(record.getHeight() + ";");
		builder.append(record.isMarried() + ";");		
		return builder.toString();    
	}


}
