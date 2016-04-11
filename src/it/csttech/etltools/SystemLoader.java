package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.*;

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
	protected List<String> parseRecord(Record record){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		List<String> fieldList = new ArrayList<String>();
		fieldList.add( Integer.toString(record.getId()) );
		fieldList.add( record.getName() );		
		fieldList.add( (formatter.format(record.getBirthday())).toString() );
		fieldList.add( Double.toString(record.getHeight()) );
		fieldList.add( Boolean.toString(record.isMarried()) );			
		
		return fieldList;	
	}
	




}
