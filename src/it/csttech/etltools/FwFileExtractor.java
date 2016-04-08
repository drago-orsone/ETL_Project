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

	public FwFileExtractor(String file){
		super(file);
	}

	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected Record parseLine(String inputString){

		Record record = new Record();

		try{
			final int FIXED_WIDTH = 20;
			List<String> list = new ArrayList<String>();		
			int fieldsNumber;

			if ((inputString.length() - 1) % FIXED_WIDTH == 0) {

				fieldsNumber = (inputString.length() - 1) / FIXED_WIDTH;
				for (int i = 0; i < fieldsNumber; i++)
					list.add(inputString.substring(i*FIXED_WIDTH, (i+1)*FIXED_WIDTH).trim());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			record.setId(Integer.parseInt(list.get(0)));
			record.setName(list.get(1));
			record.setBirthday(formatter.parse(list.get(2)));
			record.setHeight(Double.parseDouble(list.get(3)));
			record.setMarried(Boolean.parseBoolean(list.get(4)));

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
