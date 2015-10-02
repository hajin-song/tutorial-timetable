/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entitiyPK;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.whitley.object.entities.Day;

/**
 *
 * @author Hajin
 */
@Embeddable
public class TimestreamPK implements Serializable {

    @NotNull
    @Lob
    @Column(name = "stream_day")
    @Enumerated(EnumType.STRING)
    private Day streamDay;
    
    @NotNull
    @Column(name = "stream_time_start")
    @Temporal(TemporalType.TIME)
    private Date streamTimeStart;

    @NotNull
    @Column(name = "stream_time_end")
    @Temporal(TemporalType.TIME)
    private Date streamTimeEnd;


    
    public TimestreamPK() {
    }

    
    public TimestreamPK(String streamDay, String streamTimeStart, String streamTimeEnd) {
        try {
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date startDate, endDate;
            startDate = df.parse(streamTimeStart);
            endDate = df.parse(streamTimeEnd);
            this.streamDay = Day.valueOf(streamDay);
            this.streamTimeStart = startDate;
            this.streamTimeEnd = endDate;
        } catch (ParseException ex) {
            Logger.getLogger(TimestreamPK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Day getStreamDay() {
        
        return streamDay;
    }

    public void setStreamDay(String streamDay) {
        this.streamDay = Day.valueOf(streamDay);
    }

    public String getStreamTimeStart() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String reportDate = df.format(streamTimeStart);
        
        return reportDate;
    }

    public void setStreamTimeStart(String streamTimeStart) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date storeDate;
        try {
            storeDate = df.parse(streamTimeStart);
            this.streamTimeEnd = storeDate;
        } catch (ParseException ex) {
            Logger.getLogger(TimestreamPK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStreamTimeEnd() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String reportDate = df.format(streamTimeEnd);
        
        return reportDate;
    }

    public void setStreamTimeEnd(String streamTimeEnd) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date storeDate;
        try {
            storeDate = df.parse(streamTimeEnd);
            this.streamTimeEnd = storeDate;
        } catch (ParseException ex) {
            Logger.getLogger(TimestreamPK.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (streamDay != null ? streamDay.hashCode() : 0);
        hash += (streamTimeStart != null ? streamTimeStart.hashCode() : 0);
        hash += (streamTimeEnd != null ? streamTimeEnd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimestreamPK)) {
            return false;
        }
        TimestreamPK other = (TimestreamPK) object;
        if ((this.streamDay == null && other.streamDay != null) || (this.streamDay != null && !this.streamDay.equals(other.streamDay))) {
            return false;
        }
        if ((this.streamTimeStart == null && other.streamTimeStart != null) || (this.streamTimeStart != null && !this.streamTimeStart.equals(other.streamTimeStart))) {
            return false;
        }
        if ((this.streamTimeEnd == null && other.streamTimeEnd != null) || (this.streamTimeEnd != null && !this.streamTimeEnd.equals(other.streamTimeEnd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.whitley.object.entities.TimestreamPK[ streamDay=" + streamDay + ", streamTimeStart=" + streamTimeStart + ", streamTimeEnd=" + streamTimeEnd + " ]";
    }
    
}
