package it.csttech.etltools.loader;

import java.util.*;
import it.csttech.etltools.Records;
import it.csttech.etltools.Record;
/**
 *
 * @author alberto.dragoni
 */
public class TableFrame extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

    /**
     * Creates new form TableFrame
     */
    public TableFrame(Records records) {
	this.records = records;
	initComponents();
	fillTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     *
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        recordTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	Object[] obj = new Object[5];
	for(int i = 0; i < 5; i++)
		obj[i] = records.getColumnNames().get(i);

	dtm = new javax.swing.table.DefaultTableModel(obj, 0);
        recordTable.setModel(dtm);

        jScrollPane1.setViewportView(recordTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void fillTable() {
	List<Record> listRec = records.getRecords();
	Object[] obj = new Object[5];
    	for (Record record : listRec) {
    		obj[0] = record.getId();
    		obj[1] = record.getName();
    		obj[2] = String.format("%1$td/%1$tm/%1$tY",record.getBirthday());
    		obj[3] = record.getHeight();
    		obj[4] = record.isMarried();
    		dtm.addRow(obj);
    	}
    }

    // Variables declaration
    private Records records;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable recordTable;
    private javax.swing.table.DefaultTableModel dtm;
    // End of variables declaration
}
