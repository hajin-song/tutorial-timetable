/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.tutorial.objects;

/**
 * <h1>TuteTable</h1>
 * Object representing a slot in timetable
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.tutorial.objects.TuteSubject
 * @see org.whitley.tutorial.objects.TuteStream
 * @see org.whitley.tutorial.objects.TuteRoom
 * @since 14/2/2015
 */
public class TuteTable {
    private TuteSubject subject;
    private TuteStream stream;
    private TuteRoom room;
    
    public TuteTable (TuteSubject subject, TuteStream stream, TuteRoom room){
        this.subject = subject;
        this.stream = stream;
        this.room = room;
    }

    
    @Override
    public String toString() {
        return "subject: " + subject.getCode() + ", stream=" + stream.toString() + ", room=" + room.toString() + '}';
    }
    

    

    public TuteSubject getSubject() {
        return subject;
    }

    public void setSubject(TuteSubject subject) {
        this.subject = subject;
    }

    public TuteStream getStream() {
        return stream;
    }

    public void setStream(TuteStream stream) {
        this.stream = stream;
    }

    public TuteRoom getRoom() {
        return room;
    }

    public void setRoom(TuteRoom room) {
        this.room = room;
    }
    



    
    
    
}
