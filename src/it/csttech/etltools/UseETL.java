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
		String csvOutFile = "out.csv";
		String fwInFile = "data.fw";
		String fwOutFile = "data.fw";		

		Extractor ex1 = new CsvFileExtractor(csvInFile);
		Extractor ex2 = new FwFileExtractor(fwFileName);
		
		List<Record> records = ex1.extract();
		//showRecords(records);

		records = ex2.extract();
		//showRecords(records);

		Loader load1 = new CsvFileLoader(csvOutFile);
		load1.load(records);

		Loader load2 = new FwFileExtractor(fwOutFile);		
		load2.load(records);

    }
}

