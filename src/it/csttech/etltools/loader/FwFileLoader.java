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

	public FwFileLoader(String file, int fixedWidth, String finalChar){
		super(file);
		this.fixedWidth = fixedWidth;
		this.finalChar = finalChar;
	}

  	/*
   	* PlaceHolder 
   	*  
   	*/

	private int fixedWidth;
	private String finalChar;

	@Override
  	protected String buildTitle(List<String> columnNames){
		
		return String.format(
			"%-" + fixedWidth + "s%-" + fixedWidth + "s%-" + fixedWidth + "s%-" + fixedWidth + "s%-" + fixedWidth + "s%s",
			columnNames.get(0), columnNames.get(1), columnNames.get(2), columnNames.get(3), columnNames.get(4), finalChar);   
	}

	@Override
  	protected String buildLine(Record record){
		int dateWidth = fixedWidth - 6;
		return String.format( Locale.US,
			"%1$-" + fixedWidth + "d%2$-" + fixedWidth + "s%3$td/%3$tm/%3$-" + dateWidth + "tY%4$-" + fixedWidth + ".02f%5$-" + fixedWidth + "s%6$s", 
			record.getId(), record.getName(), record.getBirthday(), record.getHeight(), record.isMarried(), finalChar);   
	}

}
