package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * PlaceHolder
 * 	Dubbio, perchè non deve implementare : " implements Extractor" ?
 */
public abstract class AbstractFileExtractor {

	private String fileName;
	private static final Logger log = LogManager.getLogger(AbstractFileExtractor.class.getName());

	/*
	 * Constructor.
	 *
	*/
	public AbstractFileExtractor(String fileName){
		this.fileName = fileName ;
	}

	/*
	* Abstract method to be specified in the children classes.
	* placeHolder
	*/
	protected abstract Records buildRecords(BufferedReader br) throws IOException;

  /*
   * PlaceHolder
   *
   */
	public Records extract(){

		Records records = new Records();
		BufferedReader br = null;
		InputStream in = null;

		try {
			log.debug("Opening File Stream");
			in = new FileInputStream(fileName); //FileNotFoundException
			br = new BufferedReader(new InputStreamReader(in));

			records = buildRecords(br);

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
		}
		return records;
	}

}
