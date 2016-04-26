package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import java.util.Properties;
import it.csttech.etltools.ETLException;
import java.lang.reflect.Constructor;

/**
*
*/
public class ExtractorFactory {

	private Properties properties;

	/**
	 * [ExtractorFactory description]
	 * @param  properties [description]
	 * @return            [description]
	 */
	public ExtractorFactory(Properties properties){
		this.properties = properties;
	}

	/**
	* [getExtractor description]
	* @param  extractorType [description]
	* @return               [description]
	*/
	public Extractor getExtractor(String extractorType) throws ETLException {
		String extractorClassName = properties.getProperty(extractorType + ".extractor.class".toLowerCase());
		if(extractorClassName == null )
			throw new ETLException("Invalid input format " + extractorType + ".");
		try{
			Constructor<?> ctor = Class.forName(extractorClassName).getDeclaredConstructor(Properties.class);
			return (Extractor)ctor.newInstance(properties);
		} catch (ReflectiveOperationException ex) {
			throw new ETLException("Cannot find : " + ex.getMessage() + "class" );
				//i possibili errori sono 3: non trovo la classe, la classe non ha tale costruttore, la classe non può essere istanziata!
		}
	}

}
