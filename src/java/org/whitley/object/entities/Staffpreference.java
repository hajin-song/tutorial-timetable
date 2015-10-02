/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>Staffpreference</h1>
 * POJO representation of Staffpreference table in database.
 * Maps Staffpreference table as java Object.
 * 
 * Dependent on {@code org.whitley.object.entities.Staff}
 * 
 * <p>Named Query: findAllStaffPreference, retrieve all staff preference records</p>
 * 
 * <p>Named Query: findByPrefID, retrieves a single preference record, specified by prefence ID</p>
 * 
 * <p>Named Query: findByStaffID, retrieve staff preference records of the given Staff</p>
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.entities.Staff
 * @since 10/1/2015
 */
@Entity
@Table(name = "staffpreference", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findAllStaffPreference", query = "SELECT s FROM Staffpreference s"),
    @NamedQuery(name = "findByPrefID", query = "SELECT s FROM Staffpreference s WHERE s.id = :id"),
    @NamedQuery(name = "findByStaffID", query = "SELECT s FROM Staffpreference s WHERE s.staffId = :staffId")
    })
public class Staffpreference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @Column(name = "stream_day")
    @Enumerated(EnumType.STRING)
    private Day streamDay;
    
    @NotNull
    @Column(name = "stream_time_start")
    @Temporal(TemporalType.TIME)
    private Date streamTimeStart;
    
    @NotNull
    @Column(name = "stream_end_time")
    @Temporal(TemporalType.TIME)
    private Date streamEndTime;
    
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Staff staffId;

    public Staffpreference() {
    }

    public Staffpreference(String streamDay, String streamTimeStart, String streamEndTime, Staff staff) {
        try {
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date startDate, endDate;
            startDate = df.parse(streamTimeStart);
            endDate = df.parse(streamEndTime);
            this.streamDay = Day.valueOf(streamDay);
            this.streamTimeStart = startDate;
            this.streamEndTime = endDate;
            this.staffId = staff;
        } catch (ParseException ex) {
            
        }

        this.staffId = staff;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setStreamTimeStart(Date streamTimeStart) {
        this.streamTimeStart = streamTimeStart;
    }

    public String getStreamEndTime() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String reportDate = df.format(streamEndTime);
        
        return reportDate;
    }
    
    public Date getSSTDate(){
        return streamTimeStart;
    }
    public Date getSETDate(){
        return streamEndTime;
    }

    public void setStreamEndTime(Date streamEndTime) {
        this.streamEndTime = streamEndTime;
    }

    public Staff getStaffId() {
        return staffId;
    }

    public void setStaffId(Staff staffId) {
        this.staffId = staffId;
    }


    @Override
    public String toString() {
        return getStreamDay()+ ", From " + getStreamTimeStart()+" To "+getStreamEndTime();
    }
    
}
