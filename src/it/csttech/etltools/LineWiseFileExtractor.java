package it.csttech.etltools;

import java.util.List;
//import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.ParseException;

public abstract class LineWiseFileExtractor extends AbstractFileExtractor {

	private static final Logger log = LogManager.getLogger("Extractor.File.LineWise");

	public LineWiseFileExtractor(String file){
		super(file);
	}
	
	@Override
	protected Records buildRecords(BufferedReader br) throws IOException {
		
		Records records = new Records();
		records.setColumnNames(parseColumnNames(br.readLine()));

		String inputLine = null;
		while ((inputLine = br.readLine()) != null)
			records.addRecord(parseLine(inputLine));

		return records;
	}

	private Record parseLine (String inputString){

		Record record = new Record();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try{
			List<String> list = parseColumnNames(inputString);

			record.setId(Integer.parseInt(list.get(0)));
			record.setName(list.get(1));
			record.setBirthday(formatter.parse(list.get(2)));
			record.setHeight(Double.parseDouble(list.get(3)));
			record.setMarried(Boolean.parseBoolean(list.get(4)));

		}catch(ParseException pe){
			log.error("Parsing not succeded.");
		}finally{
			return record;
		}

	}

	protected abstract List<String> parseColumnNames(String inputString);

}
