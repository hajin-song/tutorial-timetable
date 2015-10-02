/*
 * Author: Ha Jin Song
 * Last Modified: 28/7/2014
 * 
 * Object class for tutorial
 */
package org.whitley.tutorial.objects;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <h1>TuteSubject</h1>
 * Object representing tutorial subject
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.tutorial.objects.TuteStudent
 * @see org.whitley.tutorial.objects.TuteTutor
 * @Since 28/7/2014
 */
public class TuteSubject{
	private String name, code;
        private TuteTutor tutor;
	private ArrayList<TuteStudent> students;
	private ArrayList<String> constraints;
	private int numConstraints,numStudents,semester;

	
	
	public TuteSubject(String code,String name){
		this.name = name;
		students = new ArrayList<>();
		this.code = code;
		numStudents=0;
	}

        /**
         * addStudent, add student of type TuteStudent to this subject.
         * Add only if the student does not exist in subject already.
         * @param student: Type of TuteStudent, student to be added
         */
	public void addStudent(TuteStudent student){
		for(TuteStudent in:students){
			if(in.compare(student)){
				return;
			}
		}
		students.add(student);
		numStudents++;
	}
	public void setConstraints(Constraint constraints){
		this.constraints = constraints.getConstraints();
		numConstraints = this.constraints.size();
		
	}
	public boolean compare(TuteSubject tute){
		return this.name.equals(tute.name);
	}

    @Override
    public String toString() {
        return "TuteSubject{" + "tutor=" + tutor + ", name=" + name + ", semester=" + semester + ", code=" + code + ", students=" + students + ", constraints=" + constraints + ", numConstraints=" + numConstraints + ", numStudents=" + numStudents + '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TuteSubject other = (TuteSubject) obj;
        return Objects.equals(this.code, other.code);
    }


        


        public TuteTutor getTutor() {
            return tutor;
        }

        public void setTutor(TuteTutor tutor) {
            this.tutor = tutor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSemester() {
            return semester;
        }

        public void setSemester(int semester) {
            this.semester = semester;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public ArrayList<TuteStudent> getStudents() {
            return students;
        }

        public void setStudents(ArrayList<TuteStudent> students) {
            this.students = students;
        }

        public ArrayList<String> getConstraints() {
            return constraints;
        }

        public void setConstraints(ArrayList<String> constraints) {
            this.constraints = constraints;
        }

        public int getNumConstraints() {
            return numConstraints;
        }

        public void setNumConstraints(int numConstraints) {
            this.numConstraints = numConstraints;
        }

        public int getNumStudents() {
            return numStudents;
        }

        public void setNumStudents(int numStudents) {
            this.numStudents = numStudents;
        }

 


	
}
