package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

import java.sql.Connection;


/**
 * Load a container of record in a SQLite database.
 * 
 * @author drago-orsone, MasterToninus
 * @since mm-dd-yyyy
 * @see <a href="http://stackoverflow.com/questions/1609637/is-it-possible-to-insert-multiple-rows-at-a-time-in-an-sqlite-database">how to add multiple rows in single query <\a>
 * 
 */
public class SqliteLoader extends AbstractDbLoader implements Loader {

	private static final Logger log = LogManager.getLogger(SqliteLoader.class.getName());

	/*
	 * Constructor
	 * Load attribute specific to Sqlite 
	 */
	public SqliteLoader(String dbName, String tableName){
		super(dbName,tableName);
		this.dbClassName = "org.sqlite.JDBC";
		this.jdbConnectorOptions = "jdbc:sqlite:";
	}


	/*
	 *	Execute a Create table statement according to the record format hardcoded in the javabean class "Record".
	 *  It depends from the structure of the javabean Record and from the sql dialect.
	 * 
	 * @see it.csttech.etltools.Record
	 */
	@Override	 
	protected void createTable(Connection conn, Records records){
		//Assembly the sqlite code 
		StringBuilder sqlCode = new StringBuilder("CREATE TABLE  IF NOT EXISTS " + tableName + "( " );
		sqlCode.append(fields.get(0) + " INTEGER PRIMARY KEY     NOT NULL, ");
		sqlCode.append(fields.get(1) + " TEXT NOT NULL, ");
		sqlCode.append(fields.get(2) + " DATETIME , ");
		sqlCode.append(fields.get(3) + " REAL , ");
		sqlCode.append(fields.get(4) + " BOOLEAN ) ");				
	
		log.debug("Creating Table : " + tableName + " : | " + fields.get(0) + 
					" | " + fields.get(1) + " | " + fields.get(2) + 
					" | " + fields.get(3) + " | " + fields.get(4) +" |" );
		//Execute the query
		executeStatement(conn, sqlCode.toString());
	}



	/*
	 *	Execute a bulk addRow statement.
	 *  It depends from the structure of the javabean Record and from the sql dialect.
	 * 
	 * @param conn JDBC connection
	 * @param records container of records to be loaded.
	 * @todo. l'interfaccia resultset può essere usata per fare il load elegante. (metodi update)
	 * @see <a href="https://docs.oracle.com/javase/6/docs/api/java/sql/ResultSet.html">JavaDoc<\a>
	 */
	@Override
	protected void addRows(Connection conn, Records records){
		//Assembly the sqlite code 
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sqlCode = new StringBuilder("INSERT INTO " + tableName + " ( " );
		for(String Name : fields)
			sqlCode.append( " " + Name + " ,");
				
		sqlCode.deleteCharAt(sqlCode.length()-1);
		sqlCode.append( " ) VALUES ");		

		for(Record record : records.getRecords()){
		StringBuilder buffer = new StringBuilder(sqlCode.toString());
			buffer.append("( " + record.getId() + " , ");
			buffer.append("'" + record.getName() + "'" + " , ");
			buffer.append("'" + (formatter.format(record.getBirthday())).toString() + "'" + " , ");
			buffer.append("'" + record.getHeight() + "'" + " , ");
			buffer.append("'" + record.isMarried() + "'" + " );");					
			log.debug("Insert Query : ID ="  + record.getId());
					executeStatement(conn, buffer.toString());		

		}

/*				***TEMP*** OLD VERSION
		for(Record record : records.getRecords()){
			sqlCode.append("( " + record.getId() + " , ");
			sqlCode.append("'" + record.getName() + "'" + " , ");
			sqlCode.append("'" + (formatter.format(record.getBirthday())).toString() + "'" + " , ");
			sqlCode.append("'" + record.getHeight() + "'" + " , ");
			sqlCode.append("'" + record.isMarried() + "'" + " ),");					
			log.debug("Insert Query : "  + "...");		
		}
			
		sqlCode.deleteCharAt(sqlCode.length()-1);
		sqlCode.append( " ; ");	
			
		//Execute the query	
		executeStatement(conn, sqlCode.toString());		
*/
	}
	


}