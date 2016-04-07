package it.csttech.etltools;

import java.util.*;

/**
 * PlaceHolder
 */
public class CsvFileExtractor implements Extractor {
	
   private String fileName;

	/*
	 * Transform the readed line to a record
	 */
	protected static Record parseLine (String inputLine){
		String[] parsedLine inputLine.split( 
								"\";\"" + "|" + 
								"\";"	+ "|" + 
								";\""	+ "|" + 
								";"		+ "|" + 
								"\"");
		Record output = new Record();
		output.setId( (int) parsedLine[0] );
		output.setName( parsedLine[1] );
		output.setBirthday( (Date) parsedLine[2] );
		output.setHeight( (double) parsedLine[3] );
		output.setMarried( (boolean) parsedLine[4] );		
		return output;	
	}

   
   public CsvFileExtractor( String fileName){
	   this.fileName = fileName ;
   }
	
  /*
   * PlaceHolder 
   * 
   */
	public List<Record> Extract(){
		List<Record> records = new ArrayList<Record>;

		BufferedReader br = null;
		InputStream in = null;
		try {
			log.debug("Opening File Stream");
			in = new FileInputStream(inputFile); //FileNotFoundException
			br = new BufferedReader(new InputStreamReader(in));

		}catch(FileNotFoundException e){
			log.error("File not Found Error");
			log.info( "\t Possible reasons may be:\n" +
					  "\t Input file " + inputFile + " not found.");
		}

		String inputLine = null;			
		try {
			while ( (inputLine = br.readLine()) != null){
				records.add( parseLine( inputLine ) );
			}
		}catch(IOException e){
			log.error("Input file reading failed.");
		}finally{
			if( in != null) {
				try{
					log.debug("Closing File Stream");
					br.close(); //br.close throws IOException
					in.close(); //in.close throws IOException
				}catch(IOException e){
					log.error("Input file " + inputFile + " closing not succeded.");
				}
			}
		}
	}

	  
	  
  }
}
