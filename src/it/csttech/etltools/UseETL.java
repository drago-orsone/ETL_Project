package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*; // Use List

/**
 * A cli interface to launch a simple ETL Suite
 *
 * <p>
 * 	
 * </p>
 *
 * @author 
 * @todo. Boh
 *  
 */
public class UseETL {

	static final Logger log = LogManager.getLogger();


    
/**
 * Main 
 * 
 * @param  args argomenti passati
 */
    public static void main(String[] args) {
		String csvFileName = "data.csv";
		String fwFileName = "data.fw";

		Extractor ex1 = new CsvFileExtractor(csvFileName);
		Extractor ex2 = new FwFileExtractor(fwFileName);
		
		List<Record> records = ex1.extract();

		for( Record record : records)
			System.out.println( " " + record.getId() +
								" " + record.getName() +
								" " + record.getBirthday() +
								" " + record.getHeight() +
								" " + record.isMarried() );

		records = ex2.extract();

		for( Record record : records)
			System.out.println( " " + record.getId() +
								" " + record.getName() +
								" " + record.getBirthday() +
								" " + record.getHeight() +
								" " + record.isMarried() );


    }
}

