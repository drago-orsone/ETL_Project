package it.csttech.etltools;

import java.util.*;

public class Records  {
	
	private List<String> columnNames;
	private List<Record> records;

	public Records(){
		columnNames = new ArrayList<String>();
		records = new ArrayList<Record>();		
	}

	
    public List<String> getColumnNames() {
        return this.columnNames;
    }
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<Record> getRecords() {
        return this.records;
    }
    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void addColumnName(String columnName){
	columnNames.add(columnName);
    }

    public void addRecord(Record record){
	records.add(record);
    }
}
