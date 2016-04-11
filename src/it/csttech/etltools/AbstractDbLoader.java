package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.io.*;
import java.util.*;


/**
 *
 * http://stackoverflow.com/questions/1609637/is-it-possible-to-insert-multiple-rows-at-a-time-in-an-sqlite-database
 * 
 */
public abstract class AbstractDbLoader {
	private String dbName;
	
	protected String tableName;
	protected String dbClassName;
	protected String jdbConnectorOptions;
	protected List<String> fields;

	private static final Logger log = LogManager.getLogger();

	/*
	 * Constructor
	*/   
	public AbstractDbLoader(String dbName, String tableName){
		this.dbName = dbName ;
		this.tableName = tableName ;
	}


	/*
	 * Modicare un po' di cose riguardo la connessione! è specifica di sqlite!
	 */
	public void load(Records records){

		fields = records.getColumnNames();
		Connection conn = null;

        try {
			log.debug("Driver Loading.");
            Class.forName(dbClassName).newInstance();
            
			File f = new File(dbName);
			if( f.exists())
				log.debug("Database found");
			else
				log.warn("Database not found. Creating " + dbName);
            
  			log.debug("Requesting Connection to drive manager : " + dbName);
			conn = DriverManager.getConnection(jdbConnectorOptions + dbName);     //throws SQLException     
   
			log.debug("Checking if " + tableName + " exists in " + dbName);
			if(checkTable(conn) ){
				log.debug("Table found. Checking if " + tableName + " is correctly formatted.");
				if(!checkColumnNames(conn)){
					log.error("Column Names are not as expected. ABORT!");
					System.exit(0); //dovrei uscire dal metodo.. non è così fatal se non riesco a fare un load! il programma potrebbe continuare
				}
			}else{
				log.warn( tableName + " not found. creating table.");	
				createTable(conn, records);
			}

            //--------------------------------------------------
			log.debug("Executing Query to db");
			// Caricare la tabella 
			addRows(conn, records);
			
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
		}
	}


	// Ok per ogni database sql
	protected static void executeStatement(Connection conn, String sqlCode){		
		Statement stmt = null;
		try {
		  log.debug(" Query statement = " + sqlCode);	
		  stmt = conn.createStatement();
		  stmt.executeUpdate(sqlCode);
		  stmt.close();

		} catch ( SQLException e ) {
		  log.fatal( e.getClass().getName() + ": " + e.getMessage() );
		  System.exit(0);
		}
	}

	/*
	 *	Seguendo il design del progetto il tipo di ogni campo e fissato da come è fatto il javabeans!
	 * 	quindi il tipo lo so 
	 * 
	 */
	protected abstract void createTable(Connection conn, Records records);

	protected abstract void addRows(Connection conn, Records records);
	
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

	private boolean checkColumnNames(Connection conn){
		boolean check = false;		
		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
			int i = 0;
			while (resultSet.next()) {
				log.debug(resultSet.getString("COLUMN_NAME") + " == " + fields.get(i) );
				if( Objects.equals(resultSet.getString("COLUMN_NAME"),fields.get(i))){
					check = true;
					i++;
				}
				else{
					check = false;
					break;
				}
			}
		} catch ( SQLException e ) {
			log.fatal( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			return check;
		}		
	}


}
