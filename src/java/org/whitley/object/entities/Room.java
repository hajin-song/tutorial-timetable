/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <h1>Room</h1>
 * Maps Room table in the database.
 * Defines Namedqueries, getAllRoom, findRoomById and findByRoomName.
 * 
 * <p>getAllRoom, retrieves all room records in the table.</p>
 * <p>findRoomById, retrieves a specific room specified by the given id.</p>
 * <p>findByRoomName, retrieves a specific room specified by the given name.</p>
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.tutorial.objects.TuteStudent
 * @see org.whitley.tutorial.objects.TuteSubject
 * @see org.whitley.tutorial.objects.Constraint
 * @since 28/7/2014
 */
@Entity
@Table(name = "room", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findAllRoom", query = "SELECT r FROM Room r"),
    @NamedQuery(name = "findRoomById", query = "SELECT r FROM Room r WHERE r.id = :id"),
    @NamedQuery(name = "findByRoomName", query = "SELECT r FROM Room r WHERE r.roomName = :roomName")
    })
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "room_name", unique = true)
    private String roomName;
    
    @NotNull
    @Column(name = "min_capacity")
    private int minCapacity;
    
    @NotNull
    @Column(name = "max_capacity")
    private int maxCapacity;
    
    @OneToMany(mappedBy = "roomId")
    private Collection<Subject> subjectCollection;

    public Room() {
    }


    public Room(String roomName, int minCapacity, int maxCapacity) {
        this.roomName = roomName;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(int minCapacity) {
        this.minCapacity = minCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @XmlTransient
    public Collection<Subject> getSubjectCollection() {
        return subjectCollection;
    }

    public void setSubjectCollection(Collection<Subject> subjectCollection) {
        this.subjectCollection = subjectCollection;
    }

}
