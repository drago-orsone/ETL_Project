package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Properties;

/**
 * PlaceHolder
 */
public class SqliteExtractor extends AbstractDbExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger(SqliteExtractor.class.getName());


	/*
	 * Class Constructor specyfing database name e table name.
	 * @param dbName name of the target database
	 * @param tableName name of the target table in the considered database
	*/
	public SqliteExtractor(String dbName, String tableName){
		super(dbName,tableName);
		this.dbClassName = "org.sqlite.JDBC";
		this.jdbConnectorOptions = "jdbc:sqlite:";
	}

	public SqliteExtractor(Properties properties){
		super(properties.getProperty("inputFile") + ".db", properties.getProperty("inputTable"));
		this.dbClassName = "org.sqlite.JDBC";
		this.jdbConnectorOptions = "jdbc:sqlite:";
	}

	/*
	 * Extract the first record from the passed ResultSet
	 *
	 * @param rs Result set of a query.
	 * @return record
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
			log.error("Cannot extract record. Exception: " + e.getClass().getName() + ": " + e.getMessage() );
			return null;
		} catch ( ParseException e ) {
			log.error("Cannot Recognized data format. Exception: " + e.getClass().getName() + ": " + e.getMessage());
			return null;
		} finally {
			return record;
		}
	}


}
