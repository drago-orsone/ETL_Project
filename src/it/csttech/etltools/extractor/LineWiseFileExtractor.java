package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import java.util.List;
//import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.ParseException;

public abstract class LineWiseFileExtractor extends AbstractFileExtractor {

	private static final Logger log = LogManager.getLogger(LineWiseFileExtractor.class.getName());
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	int i = 2;

	public LineWiseFileExtractor(String file){
		super(file);
	}

	@Override
	protected Records buildRecords(BufferedReader br) throws IOException {

		Records records = new Records();
		records.setColumnNames(parseColumnNames(br.readLine()));

		String inputLine = null;
		while ((inputLine = br.readLine()) != null) {
			records.addRecord(parseLine(inputLine));
			i++;
		}

		return records;
	}

	private Record parseLine (String inputString){

		Record record = new Record();


		try{
			List<String> list = parseColumnNames(inputString);

			record.setId(Integer.parseInt(list.get(0)));
			record.setName(list.get(1));
			record.setBirthday(formatter.parse(list.get(2)));
			record.setHeight(Double.parseDouble(list.get(3)));
			record.setMarried(Boolean.parseBoolean(list.get(4)));

		}catch(ParseException pe){
			log.error("Parsing not succeded at line " + i + ".");
		}finally{
			return record;
		}

	}

	protected abstract List<String> parseColumnNames(String inputString);

}
