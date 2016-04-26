package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import java.util.Properties;
import it.csttech.etltools.ETLException;

public class LoaderFactory {

	private Properties properties;

	public LoaderFactory(Properties properties){
		this.properties = properties;
	}

	/**
	* [getLoader description]
	* @param  loaderType [description]
	* @return            [description]
	*/
	public Loader getLoader(String loaderType) throws ETLException {

		switch (loaderType.toLowerCase()) {
			case "csv": return new CsvFileLoader(properties);
			case "fw": return new FwFileLoader(properties);
			case "xml": return new XmlFileLoader(properties);
			case "db": return new SqliteLoader(properties);
			case "sys": return new SystemLoader(properties);
			case "gui": return new GuiLoader(properties);
			default: throw new ETLException("Invalid output format " + loaderType + ".");
		}

		

	}
}
