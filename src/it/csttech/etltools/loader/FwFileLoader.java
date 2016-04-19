package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;
import java.util.Locale;
import java.util.List;

/**
 * PlaceHolder
 */

public class FwFileLoader extends LineWiseFileLoader implements Loader {

	public FwFileLoader(String file){
		super(file);
	}

  	/*
   	* PlaceHolder 
   	*  
   	*/

	private static final int FIXED_WIDTH = 20;
	private static final String FINAL_CHAR = "*";

	@Override
  	protected String buildTitle(List<String> columnNames){
		
		return String.format(
			"%-" + FIXED_WIDTH + "s%-" + FIXED_WIDTH + "s%-" + FIXED_WIDTH + "s%-" + FIXED_WIDTH + "s%-" + FIXED_WIDTH + "s%s",
			columnNames.get(0), columnNames.get(1), columnNames.get(2), columnNames.get(3), columnNames.get(4), FINAL_CHAR);   
	}

	@Override
  	protected String buildLine(Record record){
		int dateWidth = FIXED_WIDTH - 6;
		return String.format( Locale.US,
			"%1$-" + FIXED_WIDTH + "d%2$-" + FIXED_WIDTH + "s%3$td/%3$tm/%3$-" + dateWidth + "tY%4$-" + FIXED_WIDTH + ".02f%5$-" + FIXED_WIDTH + "s%6$s", 
			record.getId(), record.getName(), record.getBirthday(), record.getHeight(), record.isMarried(), FINAL_CHAR);   
	}

}
