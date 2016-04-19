package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;

public class LoaderFactory {

	private String fileName;
	private String tableName;

	public LoaderFactory(){}

		public LoaderFactory(String fileName){
			this.fileName = fileName;
		}

		public LoaderFactory(String fileName, String tableName){
			this.fileName = fileName;
			this.tableName = tableName;
		}


		//use getLoader method to get object of type shape
		public Loader getLoader(String loaderType){
			switch (loaderType.toLowerCase()) {
				case "csv": return new CsvFileLoader(fileName);
				case "fw":	return new FwFileLoader(fileName);
				case "xml": return new XmlFileLoader(fileName);
				case "db": 	return new SqliteLoader(fileName, tableName);
				case "sys": return new SystemLoader();
				default:    return null;
			}
		}
	}
