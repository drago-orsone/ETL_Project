package it.csttech.etltools;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


/**
 * PlaceHolder
 */
public class SqliteExtractor extends AbstractDbExtractor implements Extractor {

	private static final Logger log = LogManager.getLogger();


	/*
	 * Constructor
	 */
	public SqliteExtractor(String dbName, String tableName){
		super(dbName,tableName);
		this.dbClassName = "org.sqlite.JDBC";
		this.jdbConnectorOptions = "jdbc:sqlite:";
	}

}
