package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import java.util.Properties;
import it.csttech.etltools.ETLException;

/**
 *
 */
public class ExtractorFactory {

	private Properties properties;

	public ExtractorFactory(Properties properties){
		this.properties = properties;
	}

	/**
	* [getExtractor description]
	* @param  extractorType [description]
	* @return               [description]
	*/
	public Extractor getExtractor(String extractorType) throws ETLException {

		switch (extractorType.toLowerCase()) {
			case "csv": return new CsvFileExtractor(properties);
			case "fw": return new FwFileExtractor(properties);
			case "xml": return new XmlFileExtractor(properties);
			case "db": return new SqliteExtractor(properties);
			case "sys": return new SystemExtractor(properties);
			default: throw new ETLException("Invalid input format " + extractorType + ".");
		}
	}
}
