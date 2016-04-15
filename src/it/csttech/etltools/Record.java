package it.csttech.etltools;

import java.util.Date;

/**
 * Javabean record.
 * 
 * <p>
 *  Finezze da ricordare:
 * 		implementare il serializable
 * 		numero versione della serializable
 * 		ricordare che come ogni classe il javabean estende object. bisogna dunque overridare toString, equals e clone (altrimenti usano quello del parent).
 * <\p>
 * 
 * @author drago-orsone, MasterToninus
 * @since mm-dd-yyyy
 * @see <a href="https://en.wikipedia.org/wiki/JavaBeans"> JavaBeans <\a> 
 * @see <a href="http://javarevisited.blogspot.it/2014/05/why-use-serialversionuid-inside-serializable-class-in-java.html" >Serial Version (???) <\a> 
 */
public class Record implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

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
