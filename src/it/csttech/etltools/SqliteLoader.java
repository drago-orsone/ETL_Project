package it.csttech.etltools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * PlaceHolder
 */
public class SqliteLoader extends AbstractDbLoader implements Loader {


	/*
	 * Constructor
	 */
	public SqliteLoader(String dbName, String tableName){
		super(dbName,tableName);
		this.dbClassName = "org.sqlite.JDBC";
		this.jdbConnectorOptions = "jdbc:sqlite:";
	}


	/*
	 *	Seguendo il design del progetto il tipo di ogni campo e fissato da come è fatto il javabeans!
	 * 	quindi il tipo lo so 
	 * 
	 */
	@Override	 
	protected void createTable(Connection conn, Records records){
		//Leggi Records ed estrai nomi colonne e tipo
		
		//assembla un codice sql 
			StringBuilder sqlCode = new StringBuilder("CREATE TABLE  IF NOT EXISTS " + tableName + "( " );
			sqlCode.append(fields.get(0) + " INTEGER PRIMARY KEY     NOT NULL, ");
			sqlCode.append(fields.get(1) + " TEXT NOT NULL, ");
			sqlCode.append(fields.get(2) + " DATETIME , ");
			sqlCode.append(fields.get(3) + " REAL , ");
			sqlCode.append(fields.get(4) + " BOOLEAN ) ");				
			/*
			 *      CREATE TABLE Persons
			 * 		(
			 * 			PersonID int,
			 * 			LastName varchar(255),
			 * 			FirstName varchar(255),
			 * 			Address varchar(255),
			 * 			City varchar(255)
			 * 		);
			 */		
		//caricalo con execute Statemen		
			executeStatement(conn, sqlCode.toString());
	}

	@Override
	protected void addRows(Connection conn, Records records){
		//Leggi Records ed estrai nomi colonne e tipo
		//assembla un codice sql 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			StringBuilder sqlCode = new StringBuilder("INSERT INTO " + tableName + " ( " );
			for(String Name : fields)
				sqlCode.append( " " + Name + " ,");
				
			sqlCode.deleteCharAt(sqlCode.length()-1);
			sqlCode.append( " ) VALUES ");		
				
			for(Record record : records.getRecords()){
				sqlCode.append("( " + record.getId() + " , ");
					sqlCode.append("'" + record.getName() + "'" + " , ");
					sqlCode.append("'" + (formatter.format(record.getBirthday())).toString() + "'" + " , ");
					sqlCode.append("'" + record.getHeight() + "'" + " , ");
					sqlCode.append("'" + record.isMarried() + "'" + " ),");					
			}
			
			sqlCode.deleteCharAt(sqlCode.length()-1);
			sqlCode.append( " ; ");	
			
			/*
			 *      INSERT INTO 'tablename' ('column1', 'column2') VALUES
			 * 		('data1', 'data2'),
			 * 		('data1', 'data2'),
			 * 		('data1', 'data2'),
			 * 		('data1', 'data2');
			 */		
		//caricalo con execute Statemen		
			executeStatement(conn, sqlCode.toString());		
	}
	


}
