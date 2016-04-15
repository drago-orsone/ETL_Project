package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;

import java.util.Locale;

import java.util.List;
import java.util.Arrays;

/**
 * PlaceHolder
 */
public class SystemLoader extends AbstractSystemLoader implements Loader {

	/*
	 *	Seguendo il design del progetto il tipo di ogni campo e fissato da come è fatto il javabeans!
	 * 	quindi il tipo lo so 
	 * 
	 */
	@Override	 
	protected String buildLine(Record record){
		return String.format(Locale.US, "| %1$5d | %2$-25s | %3$td/%3$tm/%3$tY | %4$6.2f | %5$7s |", 
					record.getId(), 
					record.getName(), 
					record.getBirthday(),
					record.getHeight(), 
					record.isMarried());
	}
	
	@Override
	protected String buildTitle(List<String> columnNames){
		final char[] array = new char[69];
		Arrays.fill(array, '-');
		array[0]='+';
		array[68]='+';
		String sep = new String(array);

		return String.format("%s%n| %5s | %-25s | %10s | %6s | %7s |%n%s",
					sep,
					columnNames.get(0), 
					columnNames.get(1), 
					columnNames.get(2),  
					columnNames.get(3), 
					columnNames.get(4),
					sep);
	}




}
