package it.csttech.etltools;

import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * PlaceHolder
 */
public class FwFileExtractor extends LineWiseFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger("Extractor.File.LineWise.Fw");

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
