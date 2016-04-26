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
								String outputFile = properties.getProperty("outputFile") + "." +  loaderType.toLowerCase();
								String fieldSeparator = properties.getProperty("FIELD_SEPARATOR");
								int fixedWidth = Integer.parseInt(properties.getProperty("FIXED_WIDTH"));
								String endChar = properties.getProperty("END_CHAR");
								String table = properties.getProperty("outputTable");

								switch (loaderType.toLowerCase()) {
								case "csv": return new CsvFileLoader(outputFile, fieldSeparator);
								case "fw": return new FwFileLoader(outputFile, fixedWidth, endChar);
								case "xml": return new XmlFileLoader(outputFile);
								case "db": return new SqliteLoader(outputFile, table);
								case "sys": return new SystemLoader();
								case "gui": return new GuiLoader();
								//default: return null;
								default: throw new ETLException("Invalid output format " + loaderType + ".");
								}
}
}
