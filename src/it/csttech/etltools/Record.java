package it.csttech.etltools;

import java.util.Date;

/**
 * Javabean record.
 * 
 * 
 * @author drago-orsone, MasterToninus
 * @since mm-dd-yyyy
 * @see <a href="https://en.wikipedia.org/wiki/JavaBeans"> JavaBeans <\a>
 *  bisogna indicare anche la "versione"
 *  metodo to string va overloadato! se no usa quello di object (ogni obj può essere messo tostring)
 *  metodo equals (object ha il suo che verifica il numero di istanza)
 *  metodo clone (return record)
 */
public class Record implements java.io.Serializable {

	private int id;
	private String name;
	private Date birthday;
	private double height;
	private boolean married;

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return this.birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

   public double getHeight() {
        return this.height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    // Diversa sintassi per gli attributi boolean ( 'is' al posto di 'get' )
    public boolean isMarried() {
        return this.married;
    }
    public void setMarried(boolean married) {
        this.married = married;
    }

}
