package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import java.util.Properties;
import it.csttech.etltools.ETLException;

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
		public Extractor getExtractor(String extractorType) throws ETLException {

			String inputFile = properties.getProperty("inputFile") + "." +  extractorType.toLowerCase();
			String fieldSeparator = properties.getProperty("FIELD_SEPARATOR");
			String stringDelimiter = properties.getProperty("STRING_DELIMITER");
			int fixedWidth = Integer.parseInt(properties.getProperty("FIXED_WIDTH"));
			String endChar = properties.getProperty("END_CHAR");
			String inputTable = properties.getProperty("inputTable");

			switch (extractorType.toLowerCase()) {
				case "csv": return new CsvFileExtractor(inputFile, fieldSeparator, stringDelimiter);
				case "fw": return new FwFileExtractor(inputFile, fixedWidth);
				case "xml": return new XmlFileExtractor(inputFile);
				case "db": return new SqliteExtractor(inputFile, inputTable);
				case "sys": return new SystemExtractor();
				default: throw new ETLException("Invalid input format " + extractorType + ".");
			}
		}
	}
