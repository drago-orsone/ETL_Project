package it.csttech.etltools.extractor;

import it.csttech.etltools.Extractor;
import it.csttech.etltools.Record;
import it.csttech.etltools.Records;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import java.io.File;
import java.util.List;
import java.util.ArrayList;



/**
 * Extract all the record from a SQL database
 *
 *
 */
public abstract class AbstractDbExtractor  {

	private static final Logger log = LogManager.getLogger(AbstractDbExtractor.class.getName());

	private String dbName;

	/** Name of the target table in the database. */
	protected String tableName;
	/** Name of the JDBC connector class name. */
	protected String dbClassName;
	/** JDBC option correspoding to the database format. */
	protected String jdbConnectorOptions;
	/** UGLY list of column names, for the sake of convenience. */
	protected List<String> fields;


	/*
	 * Class Constructor specyfing database name e table name.
	 * @param dbName name of the target database
	 * @param tableName name of the target table in the considered database
	*/
	public AbstractDbExtractor(String dbName, String tableName){
		this.dbName = dbName ;
		this.tableName = tableName ;
	}


  /*
   * Return a container of all the records contained  in the corresponding database and table.
   *
   * @return Container of records.
   */
  public Records extract(){


		Records records = new Records();
		Connection conn = null;

        try {
			log.debug("Driver Loading.");
            Class.forName(dbClassName).newInstance();

			File f = new File(dbName);
			if( f.exists())
				log.debug("Database found");
			else{
				log.error("Database not found. ABORT ");
				System.exit(0);
            }

  			log.debug("Requesting Connection to drive manager : " + dbName);
			conn = DriverManager.getConnection(jdbConnectorOptions + dbName);     //throws SQLException

			log.debug("Checking if " + tableName + " exists in " + dbName);
			if(checkTable(conn) ){
				log.debug("Table found.");
			}else{
				log.error( tableName + " not found. EXTRACTION ABORTED.");
				return null;
			}

            //--------------------------------------------------
			log.debug("Executing Query to db");
			// 1) Settare il column Names
				records.setColumnNames(parseColumnNames(conn));
				fields = records.getColumnNames();
			// 2) recuperare tutte le righe
				records.setRecords(parseRecord(conn));
			//--------------------------------------------------



        } catch (ClassNotFoundException ex) {
			log.fatal("Error loading connector driver. Class " + " dbClassName " + " not found.");
		} catch ( SQLException e ) { //Eccezione generata dalla connessione
			log.fatal(e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
        } catch (Exception e) {
			log.fatal(e.getClass().getName() + ": " + e.getMessage() );
        }finally{
			if(conn != null) {
				try{
					log.debug("Closing dB connection");
					conn.close();
				} catch ( Exception e ) {
					log.fatal( e.getClass().getName() + ": " + e.getMessage() );
					System.exit(0);
				}
			}
			return records;
		}

	}


	/*
	 * Check if the table already exist in the connected database.
	 *
	 * @param conn JDBC connection
	 * @return true if the table exists in the database
	 */
	private boolean checkTable(Connection conn){
		boolean check = false;
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getTables(null, null, tableName, null);
			check = resultSet.next();
		} catch ( SQLException e ) {
			log.fatal( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			return check;
		}
	}

	/*
	 * Retrive the list of column names.
	 * @param conn JDBC connection
	 * @return List of string containing all the column names.
	*/
	private List<String> parseColumnNames(Connection conn){
		log.debug("Retrieving Column Name List");
		List<String> NamesArray = new ArrayList<String>();
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
			while (resultSet.next()) {
				NamesArray.add(resultSet.getString("COLUMN_NAME"));
			}
		} catch ( SQLException e ) {
			log.fatal( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			StringBuilder debugString = new StringBuilder("Column retrivied : ");
			for( String line : NamesArray )
				debugString.append( line + " ");
			log.debug(debugString.toString());

			return NamesArray;
		}

	}

	/*
	 * Extract the first record from the passed ResultSet
	 *
	 * @param rs Result set of a query.
	 * @return record
	 * */
	protected abstract Record  fillRecord(ResultSet rs);

	/*
	 * Return the list of records corresponing to the passed query
	 * @param conn JDB connection.
	 * @param sqlCode string containg an sql query code.
	 * @return List record extracted from the query.
	 */
  	private List<Record>  executeQuery(Connection conn, String sqlCode){
		Statement stmt = null;
		ResultSet rs = null;

		List<Record> recordList = new ArrayList<Record>();

		log.debug(" Query statement = " + sqlCode);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery( sqlCode );

			Record record = new Record();
			while ( rs.next() ) {
				recordList.add(fillRecord(rs));
			}
			rs.close();
			stmt.close();
		} catch ( SQLException e ) {
			log.fatal( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			return recordList;
		}
	}

	/*
	 * Wrapper method executing the Select * query
	 * @param conn JDB connection.
	 *
	 * @return List record extracted from the query.
	 */
	private List<Record> parseRecord(Connection conn){
		return executeQuery ( conn, "SELECT * FROM " + tableName + ";" );
	}



}
