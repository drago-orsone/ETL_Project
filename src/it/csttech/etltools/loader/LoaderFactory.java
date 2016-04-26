package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import java.util.Properties;
import it.csttech.etltools.ETLException;
import java.lang.reflect.Constructor;

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

		String loaderClassName = properties.getProperty(loaderType + ".loader.class".toLowerCase());
		try{
			Constructor<?> ctor;
			if(loaderClassName != null)
				ctor = Class.forName(loaderClassName).getDeclaredConstructor(Properties.class);
			else
				throw new ETLException("Invalid output format " + loaderType + ".");
			return (Loader) ctor.newInstance(properties);
		} catch (ReflectiveOperationException ex) { //chiedere a matteo se catch e rilancio di eccezione è lecito.
			throw new ETLException("Possible reason:\n" +
						"Loader class " + loaderClassName + " does not exist.\n" +
						"The called constructor of " + loaderClassName + " does not exist.\n" +
						"Instantiation of class " + loaderClassName + " not succeded.");
		}
	}
}
