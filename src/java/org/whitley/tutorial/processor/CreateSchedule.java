
package org.whitley.tutorial.processor;

import java.util.ArrayList;
import java.util.Collections;

import org.whitley.tutorial.objects.Constraint;
import org.whitley.tutorial.objects.TuteRoom;
import org.whitley.tutorial.objects.TuteStream;
import org.whitley.tutorial.objects.TuteSubject;
import org.whitley.tutorial.objects.TuteTable;

/**
 * <h1>CreateSchedule</h1>
 * CreateSchedule class which acts as interface between Caller from GUI side and Back End processor.
 * <p>
 * Calls {@link org.whitley.tutorial.processor.ConstraintMaker} and
 * {@link org.whitley.tutorial.processor.AllocateTime} to create Tutorial
 * and return Timetable in form of ArrayList of {@link org.whitley.tutorial.objects.TuteTable}.
 * </p>
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.tutorial.objects.Constraint
 * @see org.whitley.tutorial.objects.TuteRoom
 * @see org.whitley.tutorial.objects.TuteStream
 * @see org.whitley.tutorial.objects.TuteSubject
 * @see org.whitley.tutorial.objects.TuteTable
 * @see org.whitley.tutorial.processor.AllocateTime
 * @see org.whitley.tutorial.processor.ConstraintMaker
 * @since 28/7/2014
 */
public class CreateSchedule {

	private ArrayList<TuteSubject> tutorials;
	private ArrayList<Constraint> constraints;
        private ArrayList<TuteStream> streams;
        private ArrayList<TuteRoom> rooms;
        private ArrayList<TuteTable> filled;

        /**
         * Constructor.
         * @param tutorials ArrayList of {@link org.whitley.tutorial.objects.TuteSubject}.
         * @param streams ArrayList of {@link org.whitley.tutorial.objects.TuteStream}.
         * @param rooms ArrayList of {@link org.whitley.tutorial.objects.TuteRoom}.
         * @param filled ArrayList of {@link org.whitley.tutorial.objects.TuteTable}.
         */
	public CreateSchedule(ArrayList<TuteSubject> tutorials,
                ArrayList<TuteStream> streams,
                ArrayList<TuteRoom> rooms,
                ArrayList<TuteTable> filled){
            this.tutorials = tutorials;
            this.streams = streams;
            this.rooms = rooms;
            this.filled= filled;
	}
	
        /**
         * createTable, calls AllocateTime object to create timetable using
         * ArrayList of {@link org.whitley.tutorial.objects.Constraint}
         * @return Result of 
         * {@link org.whitley.tutorial.processor.AllocateTime#allocateTutorial(org.whitley.tutorial.objects.TuteSubject, org.whitley.tutorial.objects.Constraint)
         * which is ArrayList of {@link org.whitley.tutorial.objects.TuteTable}, auto-generated tutorial Timetable.
         */
	public ArrayList<TuteTable> createTable(){
		ConstraintMaker makeConst = new ConstraintMaker(tutorials);
                ArrayList<TuteTable> result = new ArrayList<>();
		constraints = makeConst.CreateConstraints();
		//Allocate timetable
		AllocateTime Alloc = new AllocateTime(tutorials,constraints,streams,rooms,filled);
                result = Alloc.ac_init();
                if(result==null){
                    return null;
                }
                Collections.sort(result, (TuteTable t1, TuteTable t2) -> {
                    int dayCmp = t1.getStream().getStream_day().compareTo(t2.getStream().getStream_day());
                    if (dayCmp != 0){
                        return dayCmp;
                    }
                    int startCmp = t1.getStream().getStream_start().compareTo(t2.getStream().getStream_start());
                    if (startCmp != 0){
                        return startCmp;
                    }
                    return t1.getStream().getStream_end().compareTo(t2.getStream().getStream_end());
                });  
                return result;
	}

}
