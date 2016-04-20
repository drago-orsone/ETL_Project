package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import java.util.Properties;

public class LoaderFactory {

	private Properties properties;

	public LoaderFactory(){}

		public LoaderFactory(Properties properties){
			this.properties = properties;
		}


		//use getLoader method to get object of type shape
		public Loader getLoader(String loaderType){
			switch (loaderType.toLowerCase()) {
				case "csv": return new CsvFileLoader(properties.getProperty("outputFile") + "." +  loaderType.toLowerCase());
				case "fw": return new FwFileLoader(properties.getProperty("outputFile") + "." + loaderType.toLowerCase());
				case "xml": return new XmlFileLoader(properties.getProperty("outputFile") + "." + loaderType.toLowerCase());
				case "db": return new SqliteLoader(properties.getProperty("outputFile") + "." + loaderType.toLowerCase(), properties.getProperty("outputTable"));
				case "sys": return new SystemLoader();
				case "gui": return new GuiLoader();
				default: return null;
			}
		}
	}
