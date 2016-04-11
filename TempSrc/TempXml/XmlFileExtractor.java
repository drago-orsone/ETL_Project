package it.csttech.etltools;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.SimpleDateFormat;
import java.text.ParseException;



/**
 * PlaceHolder
 */
public class XmlFileExtractor extends AbstractFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger();

	/*
	 * Constructor
	 */
	public XmlFileExtractor(String file){
		super(file);
	}

	@Override
	public Records extract(){

		Records records = new Records();
		BufferedReader br = null;
		InputStream in = null;

		try {
			log.debug("Opening File Stream");
			in = new FileInputStream(fileName); //FileNotFoundException
			br = new BufferedReader(new InputStreamReader(in));

			records.setColumnNames(parseColumnNames(br.readLine()));

			String inputLine = null;
			while ((inputLine = br.readLine()) != null)
				records.addRecord(parseLine(inputLine));

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


	/*
	 * Cut the imput line
	 */
	@Override
	protected List<String> parseColumnNames(String inputString){
			final int FIXED_WIDTH = 20;
			List<String> list = new ArrayList<String>();		
			int fieldsNumber;

			if ((inputString.length() - 1) % FIXED_WIDTH == 0) {

				fieldsNumber = (inputString.length() - 1) / FIXED_WIDTH;
				for (int i = 0; i < fieldsNumber; i++)
					list.add(inputString.substring(i*FIXED_WIDTH, (i+1)*FIXED_WIDTH).trim());
			}else{
				log.warn("Line bad format. Skipped and continue!");
			}			
			return list;
	}


	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected Record parseLine(String inputString){

		Record record = new Record();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try{
			List<String> list = parseColumnNames(inputString);

			record.setId(Integer.parseInt(list.get(0)));
			record.setName(list.get(1));
			record.setBirthday(formatter.parse(list.get(2)));
			record.setHeight(Double.parseDouble(list.get(3)));
			record.setMarried(Boolean.parseBoolean(list.get(4)));

		}catch(ParseException pe){
			log.error("Parsing not succeded.");
		}finally{
			return record;
		}
	}
}
