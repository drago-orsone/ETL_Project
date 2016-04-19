package it.csttech.etltools;

import java.util.*;

/**
* Container of records
*
* @author drago-orsone, MasterToninus
*
*/
public class Records  {

	private List<String> columnNames;
	private List<Record> records;

	public Records(){
		columnNames = new ArrayList<String>();
		records = new ArrayList<Record>();
	}

	/**
	* Return the list of the names of the entries of the collected records.
	* @return Column names.
	*/
	public List<String> getColumnNames() {
		return this.columnNames;
	}

	/**
	* Set the list of column names of the record.
	* @param columnNames List of column names.
	*/
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * [getRecords description]
	 * @return [description]
	 */
	public List<Record> getRecords() {
		return this.records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	/**
	 * [addColumnName description]
	 * @param columnName [description]
	 */
	public void addColumnName(String columnName){
		columnNames.add(columnName);
	}
	/**
	 * [addRecord description]
	 * @param record [description]
	 */
	public void addRecord(Record record){
		records.add(record);
	}
}
