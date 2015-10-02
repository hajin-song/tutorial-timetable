package org.whitley.tutorial.objects;

import java.util.ArrayList;
/**
 * <h1>Constraint</h1>
 * Object representing constraints of an object of type {@link org.whitley.tutorial.objects.TuteSubject}.
 * @author Ha Jin Song
 * @Version 1.0
 * @since 28/7/2014
 */
public class Constraint{
	/**
	 * constraints: constraints of the target
	 * targets: name of the target, target can have aliases (different code name)
	 */
	private ArrayList<String> constraints;
        private String target;
	
	public Constraint(String target){
		constraints = new ArrayList<String>();

		this.target = target;
	}
	
        /**
         * addConstraints method, add constraint to tutorial
         * @param code: Clashing subject code.
         */
	public void addConstraints(String code){
			//Do not add to constraint if it is itself
                        if(code.equals(target)){
                            return;
                        }
			
			//Avoid duplicates
			for (String codein : constraints){
				if(codein.equals(code)){
					return;
				}
			}
			constraints.add(code);
		
	}
	
        @Override
	public String toString(){
		return target + " " + constraints.toString();
	}
	public int getSize(){
		return constraints.size();
	}
	public String getCode(){
		return target;
	}
	public ArrayList<String> getConstraints(){
		return constraints;
	}
}
