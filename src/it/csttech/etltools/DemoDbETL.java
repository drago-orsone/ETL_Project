package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*; // Use List
import java.io.File;

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
		String dbFile="test.db";
		String dbTable="TEST";
		//Per test mio ricreo sempre il file db di test!        
		File f = new File(dbFile);
		if( f.exists()){
			System.out.println( "Ricreo il file Database " + dbFile );			
			f.delete();
		}


		System.out.println("Estrazione dati da " + csvInFile);		
		Extractor ex1 = new CsvFileExtractor(csvInFile);
		Records records = ex1.extract();
		System.out.println("Dati Estratti :");
		showRecords(records.getRecords());
		
		System.out.println("Caricamento dati Estratti in tabella" + dbTable + " in " + dbFile);				
		Loader load1 = new SqliteLoader("test.db","TEST");		
		load1.load(records);

		System.out.println("Estrazione dati da tabella" + dbTable + " in " + dbFile);	
		Extractor ex2 = new SqliteExtractor("test.db","TEST");
		records = ex2.extract();
		showRecords(records.getRecords());
    }
}

