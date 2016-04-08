package it.csttech.etltools;

import java.util.*;


/**
 * PlaceHolder
 */
public class XmlFileExtractor extends AbstractFileExtractor implements Extractor {


	public XmlFileExtractor( String fileName){
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


