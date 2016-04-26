package it.csttech.etltools.loader;

import it.csttech.etltools.Loader;
import it.csttech.etltools.Records;
import java.util.Properties;
/**
 *
 * @author alberto.dragoni
 */
public class GuiLoader implements Loader {

    /**
     * Creates new form GuiLoader
     */
    public GuiLoader(Properties prop) {
		//DO NOTHING
    }


    /**
     * @param records
     */
    @Override
    public void load(Records records) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiLoader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiLoader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiLoader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiLoader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        final Records dummyRec = records;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableFrame(dummyRec).setVisible(true);
            }
        });
    }

}
