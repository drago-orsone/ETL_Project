package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;


import java.text.SimpleDateFormat;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Arrays;

/**
 * PlaceHolder
 */
public class SystemLoader extends AbstractSystemLoader implements Loader {

	static SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");

	/*
	 *	Seguendo il design del progetto il tipo di ogni campo e fissato da come è fatto il javabeans!
	 * 	quindi il tipo lo so 
	 * 
	 */
	@Override	 
	protected String buildLine(Record record){
		return String.format( "| %5d | %-15s | %10s | %6.2f | %7s |", 
					record.getId(), 
					record.getName(), 
					sdformat.format(record.getBirthday()), 
					record.getHeight(), 
					record.isMarried());
	}
	
	@Override
	protected String buildTitle(List<String> columnNames){
		final char[] array = new char[59];
		Arrays.fill(array, '-');
		String sep = new String(array);

		return String.format("%s\n| %5s | %-15s | %10s | %6s | %7s |\n%s",
					sep,
					columnNames.get(0), 
					columnNames.get(1), 
					columnNames.get(2),  
					columnNames.get(3), 
					columnNames.get(4),
					sep);
	}




}
