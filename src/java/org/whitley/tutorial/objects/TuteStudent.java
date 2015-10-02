
package org.whitley.tutorial.objects;

import java.util.Objects;


/**
 * <h1>TuteStudent</h1>
 * Object representing Student of tutorial subject.
 * @author Ha Jin Song
 * @Version 1.0
 * @Since 28/7/2014
 */
public class TuteStudent{
	/**
	 * ID = Student ID
	 * fname = First Name
	 * lname = Last Name
	 * subjects = List of subject taken by the student
	 */
	private int ID;
	private String fname;
	private String lname;
        private String mname;
	
	public TuteStudent(String fname, String lname, String mname, int ID){
		this.fname = fname;
		this.lname = lname;
                this.mname = mname;
		this.ID = ID;
	}
	
        @Override
	public String toString(){
		return "The Student name is " + fname + " "+ mname+ " " + lname + " with ID of " + ID;
	}
	public boolean compare(TuteStudent target){
		return this.ID==target.getID();
	}

        public String getName(){
            return fname + " "+ mname+ " " + lname;
        }
        

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TuteStudent other = (TuteStudent) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.fname, other.fname)) {
            return false;
        }
        if (!Objects.equals(this.lname, other.lname)) {
            return false;
        }
        if (!Objects.equals(this.mname, other.mname)) {
            return false;
        }
        return true;
    }


	
	public int getID(){
		return ID;
	}

}
