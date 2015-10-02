/*
 * Author: Ha Jin Song
 * Last Modified: 28/7/2014
 * 
 * Object class for room
 */
package org.whitley.tutorial.objects;

import java.util.Objects;

/**
 * <h1>TuteRoom</h1>
 * Object representing room within the organisation.
 * One room can hold one tutorial per stream.
 * @author Ha Jin Song
 * @Version 1.0
 * @since 28/7/2014
 */
public class TuteRoom{
	/**
	 * tutorial - tutorial in the room
	 * min - minimum people required for the room
	 * max - maximum people that can be in the room
	 * roomName - name of the room
	 */
	private int min;
	private int max;
	private String roomName;
        
	public TuteRoom(String roomName,int min, int max){
		this.min = min;
		this.max = max;
		this.roomName = roomName;
	}
	

        /**
         * fits method, check if given tutorial can be hosted in the room
         * @param tutorial: Type of {@link org.whitley.tutorial.object.TuteSubject}, tutorial to be hosted
         * @return true if the tutorial can be hosted, else false
         */
	public boolean fits(TuteSubject tutorial){
            return min <= tutorial.getStudents().size() && max >= tutorial.getStudents().size();
        }
        
        @Override
	public String toString(){
            return min + "_" + max + "_" +roomName;
	}


        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TuteRoom other = (TuteRoom) obj;
            if (this.min != other.min) {
                return false;
            }
            if (this.max != other.max) {
                return false;
            }
            if (!Objects.equals(this.roomName, other.roomName)) {
                return false;
            }
            return true;
        }


        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }


    


}
