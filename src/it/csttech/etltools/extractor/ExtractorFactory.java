package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;

public class ExtractorFactory {

	private String fileName;
	private String tableName;

	public ExtractorFactory(){}

	public ExtractorFactory(String fileName){
		this.fileName = fileName;
	}

	public ExtractorFactory(String fileName, String tableName){
		this.fileName = fileName;
		this.tableName = tableName;	
	}
	
	
   //use getExtractor method to get object of type shape 
	public Extractor getExtractor(String extractorType){
		switch (extractorType.toLowerCase()) {
			case "csv": return new CsvFileExtractor(fileName);
            case "fw":	return new FwFileExtractor(fileName);
            case "xml": return new XmlFileExtractor(fileName);
            case "db": 	return new SqliteExtractor(fileName, tableName);
            case "sys": return new SystemExtractor();
            default:    return null;
        }           
   }
}
