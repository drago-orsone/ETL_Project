package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Locale;

import java.util.Properties;


/**
 *
 * scan with STRING_DELIMETER = '"';
 */
public class SystemExtractor extends AbstractSystemExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger(SystemExtractor.class.getName());

	private static final String regex = "[^\"\\s]+|\"(\\\\.|[^\\\\\"])*\"";
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public SystemExtractor(Properties properties){

	}

	/**
	 * Cut the input line
	 *
	 * Si prende pure i campi extra.. il metodo parse line li ignorera'!
	 *
	 * @see "http://stackoverflow.com/questions/12360694/java-scanner-delimit-by-spaces-unless-quotation-marks-are-present"
	 *
	 */
	@Override
	protected List<String> parseColumnNames(String inputLine){

		//use Scanner to parse the content of each line
    Scanner scanner = new Scanner(inputLine);

		List<String> list = new ArrayList<String>();
		while(scanner.hasNext())
			list.add(scanner.findInLine(regex).replaceAll("^\"|\"$", ""));
		int i = list.size();
		while(i<5){
			list.add("*?*");
			i++;
		}
		return list;
	}




	/**
	 *	Transform the readed line to a record
	 *
	 */
	@Override
	protected Record parseRecord(String inputLine){

		Record record = new Record();
		//use Scanner to parse the content of each line
    Scanner scanner = new Scanner(inputLine);
		if(!scanner.hasNext()) return null;

		try{
			record.setId(scanner.nextInt());
			record.setName(scanner.findInLine(regex).replaceAll("^\"|\"$", ""));
			record.setBirthday(formatter.parse(scanner.next()));
			record.setHeight(scanner.useLocale(Locale.US).nextDouble());
			record.setMarried(scanner.nextBoolean());
		} catch (ParseException|InputMismatchException ex) {
			log.error("Parsing not succeded. " + ex.getMessage());
			log.debug(ex);
			record = null; // ignore wrong format string
		}finally{
			scanner.close();
		}
		return record;
	}



}
