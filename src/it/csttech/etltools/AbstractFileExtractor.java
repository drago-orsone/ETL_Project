package it.csttech.etltools;

import java.util.*;
import java.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * PlaceHolder
 * 	Dubbio, perchè non deve implementare : " implements Extractor" ?
 */
public abstract class AbstractFileExtractor {
	
	private String fileName;
	private static final Logger log = LogManager.getLogger();

	/*
	 * Abstract method to be specified in the children classes.
	 * Transform the readed line to a record
	 */
	protected abstract Record parseLine(String inputLine);


	/*
	 * Abstract method to be specified in the children classes.
	 * Transform the readed line to a record
	*/   
	public AbstractFileExtractor(String fileName){
		this.fileName = fileName ;
	}
	
  /*
   * PlaceHolder 
   * 
   */
	public List<Record> extract(){

		List<Record> records = new ArrayList<Record>();
		BufferedReader br = null;
		InputStream in = null;

		try {
			log.debug("Opening File Stream");
			in = new FileInputStream(fileName); //FileNotFoundException
			br = new BufferedReader(new InputStreamReader(in));

			String inputLine = null;
			while ((inputLine = br.readLine()) != null)
				records.add(parseLine(inputLine));

		}catch(FileNotFoundException e){
			log.error("File not Found Error");
			log.info("\t Possible reasons may be:\n" +
					  "\t Input file " + fileName + " not found.");
		}catch(IOException e){
			log.error("Input file reading failed.");
		}catch(Exception e){
			log.error(e);
		}finally{
			if( in != null) {
				try{
					log.debug("Closing File Stream");
					br.close(); //br.close throws IOException
					in.close(); //in.close throws IOException
				}catch(IOException e){
					log.error("Input file " + fileName + " closing not succeded.");
				}catch(Exception e){
					log.error(e);
				}
			}
			return records;
		}
	}
	
}
