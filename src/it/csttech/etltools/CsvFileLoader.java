package it.csttech.etltools;

/**
 * PlaceHolder
 */
public class CsvFileLoader extends AbstractFileLoader implements Loader {

	/*
	 * Constructor
	 */
	public CsvFileLoader( String fileName){
		super(fileName);
	}

	/*
	 * Transform the readed line to a record
	 */
	@Override
	protected String parseRecord(Record record){
		StringBuilder builder = new StringBuilder();
		builder.append(record.getId() + ";");
		builder.append("\"" + record.getName() + "\""+";");
		builder.append(record.getBirthday() + ";");
		builder.append(record.getHeight() + ";");
		builder.append(record.isMarried() + ";");		
		return builder.toString();    
	}

}
