package it.csttech.etltools;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * PlaceHolder
 */
public class SqliteExtractor extends AbstractDbExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger("Extractor.Db.Sqlite");


	/*
	 * Constructor
	 */
	public SqliteExtractor(String dbName, String tableName){
		super(dbName,tableName);
		this.dbClassName = "org.sqlite.JDBC";
		this.jdbConnectorOptions = "jdbc:sqlite:";
	}

	/*
	 * 
	 * 
	 * */
	@Override
	protected Record  fillRecord(ResultSet rs) {		

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Record record = new Record();
	
		try {
			record.setId(rs.getInt(fields.get(0)));
			record.setName(rs.getString(fields.get(1)));
			record.setBirthday(formatter.parse(rs.getString(fields.get(2))));
			record.setHeight(Double.parseDouble(rs.getString(fields.get(3))));
			record.setMarried(Boolean.parseBoolean(rs.getString(fields.get(4))));
		} catch ( SQLException e ) {
			log.fatal( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} catch ( ParseException e ) {
			log.error( e.getClass().getName() + ": " + e.getMessage());
		} finally {
			return record;
		}
	}


}
