package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 *
 *
 */
public abstract class AbstractSystemLoader {

	static final Logger log = LogManager.getLogger(AbstractSystemLoader.class.getName());

	public void load(Records records){
		System.out.println(buildTitle(records.getColumnNames()));
		for( Record record : records.getRecords())
			System.out.println( buildLine(record) );
		System.out.println(separatorLine());
	}

	protected abstract String buildLine(Record record);
	protected abstract String buildTitle(List<String> columnNames);
	protected abstract String separatorLine();
	
}
