/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.tutorial.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import org.whitley.object.entities.Day;
/**
 * <h1>TuteStream</h1>
 * Object representing one stream of the timetable
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.entities.Day
 * @since 14/2/2015
 */
public class TuteStream {
    private Day stream_day;
    private Date stream_start;
    private Date stream_end;

    
    public TuteStream(Day stream_day, Date stream_start, Date stream_end){
        this.stream_day = stream_day;
        this.stream_start = stream_start;
        this.stream_end = stream_end;

    }

    @Override
    public String toString() {
        return stream_day.getDay()+ "_" + getStream_start()+"_"+getStream_end();
    }

    /**
     * toFormattedString method.
     * Returns the TuteStream object in formatted string, which is used
     * to render HTML form.
     * @return formatted String representing the stream.
     */
    public String toFormattedString(){
        return stream_day.getDay()+ ", From " + getStream_start()+" To "+getStream_end();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TuteStream other = (TuteStream) obj;
        if (this.stream_day != other.stream_day) {
            return false;
        }
        if (!Objects.equals(this.stream_start, other.stream_start)) {
            return false;
        }
        return Objects.equals(this.stream_end, other.stream_end);
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.stream_day);
        hash = 53 * hash + Objects.hashCode(this.stream_start);
        hash = 53 * hash + Objects.hashCode(this.stream_end);
        return hash;
    }
    
    
    
    
    
    
    
    public Day getStream_day() {
        return stream_day;
    }

    public void setStream_day(Day stream_day) {
        this.stream_day = stream_day;
    }

    public String getStream_start() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String reportDate = df.format(this.stream_start);
        
        return reportDate;

    }

    public void setStream_start(Date stream_start) {
        this.stream_start = stream_start;
    }

    public String getStream_end() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String reportDate = df.format(this.stream_end);
        
        return reportDate;
    }

    public void setStream_end(Date stream_end) {
        this.stream_end = stream_end;
    }

    
}
