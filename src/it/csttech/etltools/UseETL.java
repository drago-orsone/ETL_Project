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
		//String csvFileName = "data.csv";
		String fwFileName = "data.fw";

		//Extractor ex = new CsvFileExtractor( csvFileName);
		Extractor ex = new FwFileExtractor(fwFileName);
		
		List<Record> records = ex.extract();

		for( Record record : records)
			System.out.println( " " + record.getId() +
								" " + record.getName() +
								" " + record.getBirthday() +
								" " + record.getHeight() +
								" " + record.isMarried() );

    }
}

