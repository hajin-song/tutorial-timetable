/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

import org.whitley.object.entitiyPK.TimestreamPK;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>Timestream</h1>
 * POJO representation of Timestream table in database.
 * Maps Timestream table as java Object.
 * 
 * <p>Named Query: getAllTimestream, retrieves all timestream records, in the order of streamDay</p>
 * 
 * <p>Named Query: findStream, retrieves a single timestream given timestream detail</p>
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.entitiyPK.TimestreamPK
 * @since 10/1/2015
 */
@Entity
@Table(name = "timestream", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "getAllTimestream", query = "SELECT t FROM Timestream t ORDER BY t.timestreamPK.streamDay ASC"),
    @NamedQuery(name = "findStream", 
            query = "SELECT t FROM Timestream t WHERE t.timestreamPK.streamTimeStart = :streamTimeStart AND t.timestreamPK.streamTimeEnd = :streamTimeEnd AND t.timestreamPK.streamDay = :streamDay")
    })
public class Timestream implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TimestreamPK timestreamPK;

    public Timestream() {
    }

    public Timestream(TimestreamPK timestreamPK) {
        this.timestreamPK = timestreamPK;
    }

    public Timestream(String streamDay, String streamTimeStart, String streamTimeEnd) {
        this.timestreamPK = new TimestreamPK(streamDay, streamTimeStart, streamTimeEnd);
    }

    public TimestreamPK getTimestreamPK() {
        return timestreamPK;
    }

    public Day getDay(){
        return timestreamPK.getStreamDay();
    }
    public String getStartTime(){
        return timestreamPK.getStreamTimeStart();
    }
    public String getEndTime(){
        return timestreamPK.getStreamTimeEnd();
    }
    
    
    public void setTimestreamPK(TimestreamPK timestreamPK) {
        this.timestreamPK = timestreamPK;
    }

    public void setStartTime(String startTime){
        this.timestreamPK.setStreamTimeStart(startTime);
    }
    public void setEndTime(String endTime){
        this.timestreamPK.setStreamTimeEnd(endTime);
    }
    public void setDay(String day){
        this.timestreamPK.setStreamDay(day);
    }

    /**
     * toFormattedString method.
     * Used to distinguish tutor change in {@code org.whitley.object.handler.Tutorialhandler}.
     * @return formatted String.
     */
    public String toFormattedString() {
        return timestreamPK.getStreamDay()+ ", From " + timestreamPK.getStreamTimeStart()+" To "+timestreamPK.getStreamTimeEnd();
    }
    @Override
    public String toString() {
        return timestreamPK.getStreamDay()+ "_" + timestreamPK.getStreamTimeStart()+"_"+timestreamPK.getStreamTimeEnd();
    }
}
