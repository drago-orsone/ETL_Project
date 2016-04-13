package it.csttech.etltools;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public abstract class LineWiseFileLoader extends AbstractFileLoader {

	public LineWiseFileLoader(String file){
		super(file);
	}

	@Override
	protected void printRecords(PrintWriter printWriter, Records records){

		printWriter.println(parseColumnNames(records.getColumnNames()));

		for(Record record : records.getRecords())
			printWriter.println(parseRecord(record));

	}

	private String parseRecord(Record record){

		List<String> recordConverted = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		recordConverted.add(Integer.toString(record.getId()));
		recordConverted.add(record.getName());
		recordConverted.add((formatter.format(record.getBirthday())).toString());
		recordConverted.add(Double.toString(record.getHeight()));
		recordConverted.add(Boolean.toString(record.isMarried()));		

		return parseColumnNames(recordConverted);
  	}

	protected abstract String parseColumnNames(List<String> columnNames);

}
