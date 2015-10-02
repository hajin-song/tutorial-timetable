/*

 */
package org.whitley.tutorial.processor;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import org.whitley.tutorial.objects.Constraint;
import org.whitley.tutorial.objects.TuteRoom;
import org.whitley.tutorial.objects.TuteStream;
import org.whitley.tutorial.objects.TuteSubject;
import org.whitley.tutorial.objects.TuteTable;

/**
 * <h1>AllocateTime</h1>
 * AllocateTime class which handles generation of Tutotorial Timetable.
 * This class is called by {@link org.whitley.tutorial.processor.CreateSchedule}
 * 
 * <p>Uses Priority Queue with number of {@link org.whitley.tutorial.objects.Constraint}
 * each {@link org.whitley.tutorial.objects.TuteSubject} has as the key</p>
 * 
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.tutorial.objects.Constraint
 * @see org.whitley.tutorial.objects.TuteRoom
 * @see org.whitley.tutorial.objects.TuteStream
 * @see org.whitley.tutorial.objects.TuteSubject
 * @see org.whitley.tutorial.objects.TuteTable
 * @see org.whitley.tutorial.processor.CreateSchedule
 * @since 28/7/2014
 */
public class AllocateTime {
	private ArrayList<TuteSubject> tutorials;
	private ArrayList<Constraint> constraints;
        private ArrayList<TuteStream> streams;
	private ArrayList<TuteTable> tuteFilled;
        private ArrayList<TuteRoom> rooms;
        
        /**
         * Initialise priority queue within the class.
         */
        private class pqsort implements Comparator<TuteSubject>{

		@Override
		public int compare(TuteSubject o1, TuteSubject o2) {
			// TODO Auto-generated method stub
			int size1 = o1.getNumConstraints() - o1.getTutor().getPref().size();
			int size2 = o2.getNumConstraints() - o2.getTutor().getPref().size();
			return size1 < size2 ? 1 : size1 == size2 ? 0 : -1;
		}
	}

	/**
         * Constructor.
         * 
         * @param tutorials ArrayList of {@link org.whitley.tutorial.objects.TuteSubject}, subjects that requires allocation.
         * @param constraints ArrayList of {@link org.whitl ey.tutorial.objects.Constraints}, constraints of each subject.
         * @param streams ArrayList of {@link org.whitley.tutorial.objects.TuteStream}, Available timestreams.
         * @param rooms ArrayList of {@link org.whitley.tutorial.objects.TuteRoom}, Available rooms.
         * @param tuteFilled ArrayList of {@link org.whitley.tutorial.objects.TuteTable}, pre-allocated tutorials.
         */
	public AllocateTime(ArrayList<TuteSubject> tutorials, 
			ArrayList<Constraint> constraints, 
                        ArrayList<TuteStream> streams,
                        ArrayList<TuteRoom> rooms,
                        ArrayList<TuteTable> tuteFilled){
		this.tutorials = tutorials;
		this.constraints = constraints;
                this.streams = streams;
                this.rooms = rooms;
                this.tuteFilled = tuteFilled;
		
	}
	
        /**
         * Todo: Test extremes
         * Return list of constraints on failure
         * Calculate Order degree
         * @return 
         */
        public ArrayList<TuteTable> ac_init(){
            ArrayList<TuteTable> tuteTable = new ArrayList<>();
            ArrayList<TuteTable> tutePossible = new ArrayList<>();
            TuteSubject cur;
            Constraint curconst;
            //Create Search Space
            for(TuteSubject tute : tutorials){
                ArrayList<TuteStream> prefs = tute.getTutor().getPref();
                for(TuteStream pref : prefs){
                    for(TuteRoom room : rooms){
                        if(room.fits(tute)){
                            tutePossible.add(new TuteTable(tute,pref,room));
                        }
                    }
                }
            }
            
            //Process pre-allocations
            for(TuteTable filled : tuteFilled){
                cur = filled.getSubject();
                curconst = findConstraint(cur);
                TuteStream target_stream = filled.getStream();
                TuteRoom target_room = filled.getRoom();
                //Reduce impossible search space
                tutePossible = removeByTutorial(tutePossible,cur);
                tutePossible = removeByRoom(tutePossible,target_stream,target_room);
                for(TuteSubject i : tutorials){
                    //iterate through constraints of the given tutorial
                    if(!(i.getCode().equals(cur.getCode()))){
                        for (String j : curconst.getConstraints()){
                            if(j.equals(i.getCode())){
                                    tutePossible = removeBySubjectStream(tutePossible,i,target_stream);
                            }
                        }
                    }

                }
                tuteTable.add(filled);
                tutorials.remove(cur);
            }
            //Finish if all tutorials were allocated manually
            if(tutorials.isEmpty()){
                return tuteTable;
            }
            

            
            //Create priority queue
            pqsort pqs = new pqsort();
            PriorityQueue<TuteSubject> q = new PriorityQueue<>(tutorials.size(), pqs);
            for (TuteSubject tute : tutorials){
                    q.offer(tute);
            }
            return ac_loop(tuteTable,tutePossible,q);
        }
        
        private ArrayList<TuteTable> ac_loop(ArrayList<TuteTable> tuteTable, ArrayList<TuteTable> tutePossible, PriorityQueue<TuteSubject> q){
            ArrayList<TuteSubject> list_of_nopes = new ArrayList<>();
            TuteSubject cur;
            TuteTable slotToAdd;
            TuteStream target_stream;
            TuteRoom target_room;
            Constraint cons;
            ArrayList<TuteTable> tuteTableTemp = tuteTable, tutePossibleTemp = tutePossible;
            
            while(!q.isEmpty()){
                cur = q.poll();
                

                slotToAdd  = findPossibleAllocation(tutePossible,cur);
                if(slotToAdd != null){
                    tuteTableTemp.add(findPossibleAllocation(tutePossible,cur));
                    tutePossibleTemp = removeByTutorial(tutePossibleTemp,cur);
                    target_stream = slotToAdd.getStream();
                    target_room = slotToAdd.getRoom();
                    cons = findConstraint(cur);
                    for(TuteSubject i : tutorials){
                        //skip the current tutorial as it does not need to be checked
                        if(i.getCode().equals(cur.getCode())){
                                continue;
                        }
                        //iterate through constraints of the given tutorial
                        for (String j : cons.getConstraints()){
                            if(j.equals(i.getCode())){
                                    tutePossibleTemp=removeBySubjectStream(tutePossibleTemp,i,target_stream);
                            }

                        }
                        //if not, just block the room to avoid overlapping of room
                        tutePossibleTemp = removeByRoom(tutePossibleTemp,target_stream,target_room);
                    }     
                    if(q.isEmpty() && list_of_nopes.isEmpty()){
                        return tuteTable;
                    }else if(q.isEmpty() && !list_of_nopes.isEmpty()){
                        return null;
                    }else{
                        list_of_nopes.stream().forEach((i) -> {
                            q.offer(i);
                        });
                        ArrayList<TuteTable> temp = ac_loop(tuteTableTemp,tutePossibleTemp,q);
                        if(temp !=null){
                            return temp;
                        }else{
                            list_of_nopes.add(cur);
                        }
                    }
                }else{
                    list_of_nopes.add(cur);
                }
            }
            return null;
        }
	/**
         * findPossibleAllocation, find a possible allocation space for a tutorial.
         * @param tutorial Type of {@link org.whitley.tutorial.objects.TuteSubject}, tutorial to search for.
         * @return Type of {@link org.whitley.tutorial.objects.TuteTable}, a possible space for the tutorial, if no possible space is given, return {@code null};
         */
        private TuteTable findPossibleAllocation(ArrayList<TuteTable> tutePossible, TuteSubject tutorial){
            for(TuteTable possible: tutePossible){
                if(possible.getSubject().equals(tutorial)){
                    return possible;
                }
            }
            return null;
        }
        
        /**
         * removeByRoom, remove the given {@code room} from the given {@code stream}.
         * @param stream Type of {@link org.whitley.tutorial.objects.TuteStream}, target stream
         * @param room Type of  {@link org.whitley.tutorial.objects.TuteRoom}, target room
         */
        private ArrayList<TuteTable> removeByRoom(ArrayList<TuteTable> tutePossible,TuteStream stream,TuteRoom room){
            for (Iterator<TuteTable> iter = tutePossible.iterator(); iter.hasNext();) {
                  TuteTable possible = iter.next();
                  if (possible.getRoom().equals(room) && possible.getStream().equals(stream)) {
                    iter.remove();
                  }
            }
            return tutePossible;
        }
        
        /**
         * removeBySubjectStream, remove the given {@code stream} for the given {@code tutorial}.
         * @param tutorial Type of {@link org.whitley.tutorial.objects.TuteSubject}, tutorial to search for.
         * @param stream Type of {@link org.whitley.tutorial.objects.TuteStream}, target stream
         */
        private ArrayList<TuteTable> removeBySubjectStream(ArrayList<TuteTable> tutePossible,TuteSubject tutorial,TuteStream stream){
            for (Iterator<TuteTable> iter = tutePossible.iterator(); iter.hasNext();) {
                  TuteTable possible = iter.next();
                  if (possible.getStream().equals(stream) && possible.getSubject().equals(tutorial)) {
                    iter.remove();
                  }
            }
            return tutePossible;
        }
        /**
         * removeByTutorial, remove all possible allocation from search space given a tutorial.
         * @param tutorial Type of {@link org.whitley.tutorial.objects.TuteSubject}, tutorial to search for.
         */
        private ArrayList<TuteTable> removeByTutorial(ArrayList<TuteTable> tutePossible,TuteSubject tutorial){
            for (Iterator<TuteTable> iter = tutePossible.iterator(); iter.hasNext();) {
                  TuteTable possible = iter.next();
                  if (possible.getSubject().equals(tutorial)) {
                    iter.remove();
                  }
            }        
            return tutePossible;
        }
        
	/**
	 * Returns constraint of the given tutorial
	 * @param tute: tutorial in which constraint is requested.
	 * @return {@code constraint} of type {@link org.whitl ey.tutorial.objects.Constraints}, 
         * containing contraints of given tutorial, {@code tute}, returns {@code null} if constraint is not found.
	 */
	private Constraint findConstraint(TuteSubject tute){
		Constraint constraint = null;
		for(Constraint x : constraints){
			if(x.getCode().equals(tute.getCode())){
				constraint = x;
			}
		}
		return constraint;
	}
}
