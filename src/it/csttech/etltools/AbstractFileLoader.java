package it.csttech.etltools;

import java.util.*;
import java.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * PlaceHolder
 * 	Dubbio, perchè non deve implementare : " implements Extractor" ?
 */
public abstract class AbstractFileLoader {
	
	private String outputFile;
	private static final Logger log = LogManager.getLogger();

	/*
	 * Abstract method to be specified in the children classes.
	 * Transform a Record into a String line
	 */
	protected abstract String parseRecord(Record record);


	/*
	 * Constructor
	*/   
	public AbstractFileLoader(String outputFile){
		this.outputFile = outputFile ;
	}
	
  /*
   * PlaceHolder 
   * 
   */
	public void load(List<Record> records){

		OutputStream out = null;
		PrintWriter printWriter = null;

		try {
			log.debug("Opening Output File Stream");
			out = new FileOutputStream(outputFile); //FileNotFoundException & SecurityException
			printWriter = new PrintWriter(out);

			for(Record record : records)
				printWriter.println(parseRecord(record));

		}catch(FileNotFoundException e){
			log.error("File not Found Error");
			log.info("\t Possible reasons may be:\n"
				+ "\t Output file " + outputFile + " exists but is a directory or a regular file.\n"
				+ "\t Output file " + outputFile + " does not exist but cannot be created.");
		}catch(SecurityException e){
			log.error("Denied write access to the output file " + outputFile + ".");
		}catch(Exception ioe){
			log.error(ioe);
		}finally{
			log.debug("Closing Output File Stream");
			if(printWriter != null) {
				printWriter.close();
			}
			if (out != null) {
				try{
					out.close();
				}catch(IOException e){
    					log.error("Writing on output file " + outputFile + " failed.");
				}catch(Exception e){
					log.error(e);
				}
			}
		}
	}
}
