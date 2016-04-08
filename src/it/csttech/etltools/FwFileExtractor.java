package it.csttech.etltools;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.SimpleDateFormat;
import java.text.ParseException;



/**
 * PlaceHolder
 */
public class FwFileExtractor extends AbstractFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger();

  	//private File file;

	FwFileExtractor(File file){
		super(file);
	}

	/*
	* PlaceHolder 
	* 
	*/

	/*
	@Override
  	public List<Record> extract(){

		BufferedReader br = null;
 		InputStream in = null;

		try {
			in = new FileInputStream(inputFile); //FileNotFoundException
			br = new BufferedReader(new InputStreamReader(in));

			String line;
			List<Record> list = new ArrayList<Record>();
			while ((line = br.readLine()) != null)
				list.add(parseLine(line));

  		}catch(FileNotFoundException e){
			log.error("File not Found Error");
			log.info( "\t Possible reasons may be:\n"
					+ "\t Input file " + inputFile + " not found.\n"
					+ "\t Output file " + outputFile + " exists but is a directory or a regular file.\n"
					+ "\t Output file " + outputFile + " does not exist but cannot be created.");
		}catch(IOException){
			//DO something
		}finally{
			if(br != null && in != null) {
				try{
					br.close(); //br.close throws IOException
					in.close(); //in.close throws IOException
				}catch(IOException e){
					log.error("Input file " + inputFile + " closing not succeded.");
				}
			}
			if(printWriter != null) {
				printWriter.close();
			}
			if (out != null) {
				try{
					out.close();
				}catch(IOException e){
    					log.error("Writing on output file " + outputFile + " failed.");
				}
			}
		}
	  
	}*/

	private Record parseLine(String line){


		try{
			Record record = new Record();
			List<String> list = new ArrayList<String>;		
			int fieldsNumber;

			if ((inputString.length() - 1) % FIXED_WIDTH == 0) {

				fieldsNumber = (inputString.length() - 1) / FIXED_WIDTH;

				for (int i = 0; i < fieldsNumber; i++)
					list.add(inputString.substring(i*FIXED_WIDTH, (i+1)*FIXED_WIDTH).trim());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			record.setID(Integer.parseInt(list[0]));
			record.setName(list[1]);
			record.setBirthday(formatter.parse(list[2]));
			record.setHeight(Double.parseDouble(list[3]));
			record.setMarried(Boolean.parseBoolean(list[4]));

			}else{
				log.warn("Line bad format. Skipped and continue!");
			}
		}catch(ParseException pe){
			log.error("Parsing not succeded.");
		}finally{
			return record;
		}
	}
}
