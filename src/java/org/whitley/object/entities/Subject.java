/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * <h1>Subject</h1>
 * POJO representation of Subject table in database.
 * Maps Subject table as java Object.

 * 
 * <p>Named Query: getAllSubject, retrieve all subject records, in the order of subjectCode</p>
 * 
 * <p>Named Query: getSubjectByCode, retrieves a single subject record, specified by given subjectCode</p>
 * 
 * <p>Named Query: getSubjectByStaff, retrieve subject records, filtered by tutor</p>
 * 
 * <p>Named Query: removeAllSubject, Deleted all subject records from the table</p>
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @since 10/1/2015
 */
@Entity
@Table(name = "subject", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "getAllSubject", query = "SELECT s FROM Subject s ORDER BY s.subjectCode ASC"),
    @NamedQuery(name = "getSubjectByCode", query = "SELECT s FROM Subject s WHERE s.subjectCode = :subjectCode"),
    @NamedQuery(name="getSubjectByStaff", query = "SELECT s FROM Subject s WHERE s.staffId = :staff"),
    @NamedQuery(name="removeAllSubject", query = "DELETE FROM Subject"),
})
public class Subject implements Serializable {
    @NotNull
    @Column(name = "size")
    private Integer size;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private Collection<Studentsubject> studentsubjectCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "subject_code",unique = true)
    private String subjectCode;
    
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "subject_name")
    private String subjectName;
    
    
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room roomId;
    
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staffId;
    
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private Collection<Studentsubject> studentsubjectCollection;
    */
    public Subject() {
    }



    public Subject(String subjectCode, String subjectName, int size) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.size = size;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public Staff getStaffId() {
        return staffId;
    }

    public void setStaffId(Staff staffId) {
        this.staffId = staffId;
    }
    
    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }




    @XmlTransient
    public Collection<Studentsubject> getStudentsubjectCollection() {
        return studentsubjectCollection;
    }

    public void setStudentsubjectCollection(Collection<Studentsubject> studentsubjectCollection) {
        this.studentsubjectCollection = studentsubjectCollection;
    }
    
}
