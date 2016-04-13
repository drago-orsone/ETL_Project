package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.SQLException;

import java.io.File;
import java.util.List;
import java.util.ArrayList;


/**
 * PlaceHolder
 * 	Dubbio, perchè non deve implementare : " implements Extractor" ?
 */
public abstract class AbstractDbExtractor  {
	private String dbName;
	
	private String tableName;

	protected String dbClassName;
	protected String jdbConnectorOptions;
	
	protected List<String> fields;

	private static final Logger log = LogManager.getLogger();


	public AbstractDbExtractor(String dbName, String tableName){
		this.dbName = dbName ;
		this.tableName = tableName ;
	}


  /*
   * PlaceHolder 
   * 
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
				System.exit(0);
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
	 * Check if the table exists in database
	
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
	 * Retrive the list of column names
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
	 * Extract a record from the passed ResultSet
	 * 
	 * */
	protected abstract Record  fillRecord(ResultSet rs);
	 
	/*
	 * Return the list of records corresponing to the passed query
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
	 * Retrive The list of records
	 */
	private List<Record> parseRecord(Connection conn){
		return executeQuery ( conn, "SELECT * FROM " + tableName + ";" );	
	}



}
