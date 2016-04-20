package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import java.util.Properties;

public class ExtractorFactory {

	private Properties properties;

	public ExtractorFactory(){}

		public ExtractorFactory(Properties properties){
			this.properties = properties;
		}


		/**
		* [getExtractor description]
		* @param  extractorType [description]
		* @return               [description]
		*/
		public Extractor getExtractor(String extractorType){
			switch (extractorType.toLowerCase()) {
				case "csv": return new CsvFileExtractor(properties.getProperty("inputFile") + "." + extractorType.toLowerCase());
				case "fw": return new FwFileExtractor(properties.getProperty("inputFile") + "." + extractorType.toLowerCase(), Integer.parseInt(properties.getProperty("FIXED_WIDTH")));
				case "xml": return new XmlFileExtractor(properties.getProperty("inputFile") + "." + extractorType.toLowerCase());
				case "db": return new SqliteExtractor(properties.getProperty("inputFile") + "." + extractorType.toLowerCase(), properties.getProperty("inputTable"));
				case "sys": return new SystemExtractor();
				default: return null;
			}
		}
	}
