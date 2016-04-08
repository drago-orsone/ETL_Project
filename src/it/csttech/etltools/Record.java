package it.csttech.etltools;

import java.util.Date;

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
    public void setHeight(double heigth) {
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
