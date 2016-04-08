package it.csttech.etltools;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PlaceHolder
 */
public class CsvFileExtractor extends AbstractFileExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger();

	/*
	 * Constructor
	 */
	public CsvFileExtractor( String fileName){
		super(fileName);
	}


	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected Record parseLine (String line){

		Record record = new Record();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try{
			Scanner s = new Scanner(line).useDelimiter(
									"\";\"" + "|" + 
									"\";"	+ "|" + 
									";\""	+ "|" + 
									";"		+ "|" + 
									"\"");

			record.setId( Integer.parseInt(s.next()) );
			record.setName( s.next() );
			record.setBirthday( formatter.parse( s.next() ) );
			record.setHeight( Double.parseDouble( s.next() ) );
			record.setMarried( Boolean.parseBoolean( s.next() ) );

/*
 * 			Qui c'è un problema con il double scanner si aspetta la virgola
 * 
			record.setId( s.nextInt() );
			record.setName( s.next() );
			record.setBirthday( formatter.parse( s.next() ) );
			record.setHeight( s.nextDouble() );
			record.setMarried( s.nextBoolean() );
*/
			if(s.hasNext())
				log.warn("Line bad format. Ignored extra fields.");
			s.close(); 
				
		//}catch(ParseException pe){
		//	log.error("Parsing not succeded.");
		}finally{
			return record;
		}
	}
	  
}

