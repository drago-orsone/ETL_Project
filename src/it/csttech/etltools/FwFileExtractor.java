package it.csttech.etltools;

import java.util.*;


/**
 * PlaceHolder
 */
public class FwFileExtractor extends AbstractFileExtractor implements Extractor {

	public FwFileExtractor( String fileName){
		super(fileName);
	}


	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected Record parseLine (String line){
		Record output = new Record();

		return output;		  
	}
}

