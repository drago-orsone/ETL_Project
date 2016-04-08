package it.csttech.etltools;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * PlaceHolder
 */
public class CsvFileExtractor extends AbstractFileExtractor implements Extractor {

	/*
	 * Transform the readed line to a record
	 */

	public CsvFileExtractor( String fileName){
		super(fileName);
	}


	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected Record parseLine (String line){
     Scanner s = new Scanner(line).useDelimiter(
     							"\";\"" + "|" + 
								"\";"	+ "|" + 
								";\""	+ "|" + 
								";"		+ "|" + 
								"\"");

		Record output = new Record();
		output.setId( s.nextInt() );
		output.setName( s.next() );


		String dateToken = s.next();
		
		SimpleDateFormat inDateFormat = new SimpleDateFormat("dd/MM/YYYY");
        try {
            output.setBirthday( inDateFormat.parse(dateToken) );
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }

		output.setHeight( s.nextDouble() );
		output.setMarried( s.nextBoolean() );	 
		s.close(); 

		return output;	
	}
	  
}

