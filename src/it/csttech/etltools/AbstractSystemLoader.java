package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 *
 * 
 */
public abstract class AbstractSystemLoader {

	static final Logger log = LogManager.getLogger();

	public void load(Records records){
		System.out.println(buildLine(records.getColumnNames()));
		for( Record record : records.getRecords())
			System.out.println( buildLine(parseRecord(record)) );	
	}

	protected String buildLine(List<String> inputFields){
		StringBuilder builder = new StringBuilder();
		for( String line : inputFields)
			builder.append(line +" ");
		return builder.toString();
	}

	protected abstract List<String> parseRecord(Record inRecord);

}
