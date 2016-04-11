package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.io.*;
import java.util.*;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * PlaceHolder
 * 	Dubbio, perchè non deve implementare : " implements Extractor" ?
 */
public abstract class AbstractDbExtractor  {
	private String dbName;
	
	protected String tableName;
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
	 * Siccome il risultato di una query deve essere un record.
	 * 
	 * */
  	private ResultSet  executeQuery(Connection conn, String sqlCode){		
		Statement stmt = null;
		ResultSet rs = null;
		try {
		  log.debug(" Query statement = " + sqlCode);	
		  stmt = conn.createStatement();
		  rs = stmt.executeQuery( sqlCode );

		  stmt.close();

		} catch ( SQLException e ) {
			log.fatal( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			return rs;
		}
	}

  	private List<Record>  parseRecord(Connection conn){		
		ResultSet rs = executeQuery(conn, "SELECT * FROM " + tableName + ";" );
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Record> recordList = new ArrayList<Record>();

		log.debug("ciclo INJ");
		try {
			Record record = new Record();
			while ( rs.next() ) {
				log.debug("ciclo OUTJ");
				int id = rs.getInt(fields.get(0));
				String  name = rs.getString(fields.get(1));
				Date birthday = formatter.parse(rs.getString(fields.get(2)));
				double height = Double.parseDouble(rs.getString(fields.get(3)));
				boolean flag = Boolean.parseBoolean(rs.getString(fields.get(4)));
         System.out.println( "ID = " + id );
         System.out.println( "NAME = " + name );
         System.out.println( "AGE = " + birthday );
         System.out.println( "height = " + height );
         System.out.println( "flag = " + flag );
				
				/*
				record.setId(rs.getInt(fields[0]));
				record.setName(rs.getString(fields[1]));
				record.setBirthday(formatter.parse(rs.getString(fields[2])));
				record.setHeight(Double.parseDouble(rs.getString(fields[3])));
				record.setMarried(Boolean.parseBoolean(rs.getString(fields[4])));
				recordList.add(record);		*/
			}
			rs.close();
		} catch ( SQLException e ) {
			log.fatal( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			return recordList;
		}
	}

	
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
	
 
  
  
  
}
