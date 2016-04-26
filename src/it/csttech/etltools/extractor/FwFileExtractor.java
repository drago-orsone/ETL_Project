package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * PlaceHolder
 */
public class FwFileExtractor extends LineWiseFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger(FwFileExtractor.class.getName());
	private int fixedWidth;
	private String endChar;

	/*
	 * Constructor
	 */
	public FwFileExtractor(String file, int fixedWidth){
		super(file);
		this.fixedWidth = fixedWidth;
	}

	public FwFileExtractor( Properties properties){
		super( properties.getProperty("inputFile") + ".fw" ) ;
		this.fixedWidth = Integer.parseInt(properties.getProperty("FIXED_WIDTH"));
		this.endChar = properties.getProperty("END_CHAR");
	}


	/*
	 * Cut the imput line
	 */
	@Override
	protected List<String> parseColumnNames(String inputString){
			List<String> list = new ArrayList<String>();
			int fieldsNumber;

			if ((inputString.length() - 1) % fixedWidth == 0) {

				fieldsNumber = (inputString.length() - 1) / fixedWidth;
				for (int i = 0; i < fieldsNumber; i++)
					list.add(inputString.substring(i*fixedWidth, (i+1)*fixedWidth).trim());
			}else{
				log.warn("Line bad format. Skipped and continue!");
			}
			return list;
	}
}
