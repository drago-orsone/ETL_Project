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

/**
 *
 *
 */
public abstract class AbstractSystemExtractor {

	static final Logger log = LogManager.getLogger(AbstractSystemExtractor.class.getName());

	/**
	 * [extract description]
	 * @return [description]
	 */
	public Records extract(){
		Records records = new Records();

		System.out.println("\nDigit 5 Column Names separated by whitespaces");

		String str;


		try{
			BufferedReader br =
						  new BufferedReader(new InputStreamReader(System.in));
			if((str=br.readLine())!=null ) //Attenzione: non ho controllo nel caso che venga passato un numero sbagliato di argomenti
				records.setColumnNames(parseColumnNames(str));
			System.out.println("Insert Record value separated by whitespaces. e.g.:\n 1 \"nonna papera\" 12/12/1212 1.12 false\n (type .q to conclude)");
			while((str=br.readLine())!=null && !str.equals(".q")){
				Record dummy = parseRecord(str);
				if(dummy != null)
					records.addRecord(parseRecord(str));
			}
		}catch(IOException io){
			io.printStackTrace();
		}
		return records;
	}

	/**
	 * Abstract method to be specified in the children classes.
	 * Read a line and Transform it to a record
	 * @param  inputLine String to be parsed
	 * @return           record object containing the parsed input.
	 */
	protected abstract Record parseRecord(String inputLine);

	/**
	 *Abstract method to be specified in the children classes.
	 * Transform the firt line readed into a list of strings
	 * @param  inputLine String to be parsed
	 * @return           List of the parsed title as strings.
	 */
	protected abstract List<String> parseColumnNames(String inputLine);

}
