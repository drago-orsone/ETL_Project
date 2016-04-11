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
public class DemoDbETL {

	static final Logger log = LogManager.getLogger();

	static void showRecords(List<Record> records){
		for( Record record : records)
			System.out.println( " " + record.getId() +
								" " + record.getName() +
								" " + record.getBirthday() +
								" " + record.getHeight() +
								" " + record.isMarried() );		
	}
    
/**
 * Main 
 * 
 * @param  args argomenti passati
 */
    public static void main(String[] args) {

		String csvInFile= "data.csv";

		Extractor ex1 = new CsvFileExtractor(csvInFile);
		Records records = ex1.extract();
		showRecords(records.getRecords());
		Loader load1 = new SqliteLoader("test.db","TEST");		
		load1.load(records);

		Extractor ex2 = new SqliteExtractor("test.db","TEST");
		records = ex2.extract();
		showRecords(records.getRecords());
    }
}

