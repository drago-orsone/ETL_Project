package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

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

		printWriter.println(buildTitle(records.getColumnNames()));

		for(Record record : records.getRecords())
			printWriter.println(buildLine(record));

	}

	protected abstract String buildLine(Record record);

	protected abstract String buildTitle(List<String> columnNames);

}
