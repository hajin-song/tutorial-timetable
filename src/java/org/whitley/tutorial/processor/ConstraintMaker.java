package org.whitley.tutorial.processor;


import java.util.ArrayList;
import org.whitley.tutorial.objects.Constraint;
import org.whitley.tutorial.objects.TuteStudent;
import org.whitley.tutorial.objects.TuteSubject;
/**
 * <h1>ConstraintMaker</h1>
 * Creates contraints for given tutorials.
 * <p>Compares list of {@link org.whitley.tutorial.objects.TuteStudent} and 
 * {@link org.whitley.tutorial.objects.TuteSubject#tutor} in
 * each {@link org.whitley.tutorial.objects.TuteSubject} then create
 * {@link org.whitley.tutorial.objects.Constraint} for each tutorial subjects.
 * </p>
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.tutorial.objects.TuteStudent
 * @see org.whitley.tutorial.objects.TuteSubject
 * @see org.whitley.tutorial.objects.Constraint
 * @since 28/7/2014
 */
public class ConstraintMaker {
	ArrayList<Constraint> constraints;
	ArrayList<TuteSubject> Tutorials;
        
        /**
         * Constructor.
         * @param Tutorials ArrayList of Type {@link org.whitley.tutorial.objects.TuteSubject}, list of tutorials to be processed.
         */
	public ConstraintMaker(ArrayList<TuteSubject> Tutorials){
		constraints = new ArrayList<>();
		this.Tutorials = Tutorials;
	}
	
	/**
	 * creates constraints, constraint created if two tutorial cannot be on same time stream.
	 * @return ArrayList of {@link org.whitley.tutorial.objects.Constraint}, 
         * representing constraints for tutorials in {@code Tutorials}.
	 */
	public ArrayList<Constraint> CreateConstraints(){
		for(TuteSubject tuteone : Tutorials){
			Constraint temp = new Constraint(tuteone.getCode());
			for(TuteSubject tutetwo : Tutorials){
				//Ignore if tutorials being compared are the same
				if(tuteone.equals(tutetwo)){
					continue;
					
				}
				//Add to constraint if it has same students
				if(compareStudent(tuteone,tutetwo)){
					temp.addConstraints(tutetwo.getCode());
				}
				//Add to constraint if tutors are the same for two tutorials
				if(compareTutor(tuteone,tutetwo)){
					temp.addConstraints(tutetwo.getCode());
				}
				
			}
			tuteone.setConstraints(temp);;
			constraints.add(temp);
		}
		return constraints;


	}

	/**
	 * Compare tutorials to see if they have same tutor name or not.
	 * @param one: one tutorial
	 * @param two: two tutorial
	 * @return {@code true} if same / {@code false} otherwise.
	 */
	private boolean compareTutor(TuteSubject one, TuteSubject two){
		return one.getTutor().equals(two.getTutor());
	}
	/**
	 * Compare students to see if they are the same.
	 * @param one: one student
	 * @param two: two student
	 * @return {@code true} if same / {@code false} otherwise
	 */
	private boolean compareStudent(TuteSubject one, TuteSubject two){
		for (TuteStudent stuone : one.getStudents()){
			for(TuteStudent stutwo : two.getStudents()){
				if(stutwo.equals(stuone)){
					return true;
				}
			}	
		}
		return false;
	}
}
