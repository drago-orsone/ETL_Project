package it.csttech.etltools;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

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

	//static final Logger log = LogManager.getLogger();

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
		String fwOutFile = "out.fw";
		String xmlOutFile = "out.xml";
		String xmlInFile = "data.xml";		

		Extractor ex1 = new CsvFileExtractor(csvInFile);
		Records records = ex1.extract();
		//showRecords(records.getRecords());
		Loader load1 = new FwFileLoader(fwOutFile);		
		load1.load(records);

		Extractor ex2 = new FwFileExtractor(fwInFile);
		records = ex2.extract();
		//showRecords(records.getRecords());
		Loader load2 = new CsvFileLoader(csvOutFile);
		//load2.load(records);

		Loader load3 = new XmlFileLoader(xmlOutFile);		
		load3.load(records);

		Extractor ex3 = new XmlFileExtractor(xmlInFile);
		records = ex3.extract();
		
		load2.load(records);

    }
}

