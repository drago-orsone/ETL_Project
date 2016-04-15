package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * PlaceHolder
 */
public class FwFileExtractor extends LineWiseFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger(FwFileExtractor.class.getName());

	/*
	 * Constructor
	 */
	public FwFileExtractor(String file){
		super(file);
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
}
