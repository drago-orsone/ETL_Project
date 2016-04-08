package it.csttech.etltools;

import java.util.*;


/**
 * PlaceHolder
 * 	Dubbio, perchè non deve implementare : " implements Extractor" ?
 */
public abstract class AbstractDbExtractor  {
	private String dbName;
	private String tableName;
	private String dbClassName = "org.sqlite.JDBC";

  /*
   * PlaceHolder 
   * 
   */
  public List<Record> extract(){
	List<Record> records = new ArrayList<Record>();

        try {
			log.debug("Driver Loading.");
            Class.forName("org.sqlite.JDBC").newInstance();
            
			File f = new File(dbName);
			if( f.exists())
				log.debug("Database found");
			else
				log.warn("Database not found. Creating " + dbName);
            
			Connection conn = null;
  			log.debug("Requesting Connection to drive manager : " + dbName);
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);     //throws SQLException     
   
			log.debug("Checking if " + tableName + " exists in " + outputFile);
			if( !checkTable(conn, tableName) ){
				log.warn( tableName + " not found. creating table.");	
				log.info( " The first row of " + inputFile + "is used to choose column names");
			// --------------------------------------------
					//metodo che eventualmente crea la tabella
				defaultTableInit( br , conn , tableName);
			// --------------------------------------------
			}
            
			log.debug("Executing Query to db");
			//-------------------------
				//ciccia: 
			//-------------------------

            
        } catch (ClassNotFoundException ex) {
			log.fatal("Error loading connector driver. Class " + " dbClassName " + " not found.");
        } catch ( SQLException e ) { //Eccezione generata dalla connessione
			log.fatal(e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
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
	return records;
  }
  
  	private static void executeStatement(Connection conn, String sqlCode){		
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

	
	private static boolean checkTable(Connection conn, String tableName){		
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

	private static List<String> retrieveColumnNames(Connection conn, String tableName){		
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
	 * Metodo di parsing:
	 * 		legge la riga in lettura e ne ricava una list di campi
	 * 
	 * <p>
	 * 	Attenzione, lo split è ancora prono ad errori.
	 * 	Se un campo contiene al suo interno uno dei delimitatori questo non viene ignorato
	 * </p>
	 * 
	 * <p>
	 * 	Se volessi realizzare un metodo che faccia la conversione da fixedwidth dovrei solo modificare questo parser.
	 * </p>
	 * 
	 * @todo. Capire come si fa a passare stringhe tipo 
	 * 				" ... ; ..."
	 * 	 			" ... \"; ..."
	 * 				ecc..
	 * 
	 * 
	 * 
	 */
	protected static String[] parseLine (String inputLine){
		return inputLine.split(	"\";\""	+ "|" + 
								"\";"	+ "|" + 
								";\""	+ "|" + 
								";"	+ "|" + 
								"\"");
	}

	/*
	 * Creatore tabella di default. --> 
	 * 		tutti i campi saranno di tipo stringa!, 
	 * 		saranno ammessi i null
	 * se voglio una cosa diversa devo inizializzare diversamente il  mio database
	 * 
	 */	
	static private void defaultTableInit (BufferedReader br, Connection conn, String tableName){
		String[] inputFields = null;
		try {
			inputFields = parseLine(br.readLine());
			StringBuilder sqlCode = new StringBuilder( "CREATE TABLE  IF NOT EXISTS " + tableName + " (" );
			for(String dummy : inputFields)
				sqlCode.append( " \"" + dummy + "\" String ,");
			sqlCode.deleteCharAt(sqlCode.length()-1);
			sqlCode.append(")");
			executeStatement(conn, sqlCode.toString());
		}catch(IOException e){
			log.fatal(" Table initialization failed. Input file reading failed.");
			System.exit(0);				
		}
	}	
  
  
  
  
  
  
  
  
}
