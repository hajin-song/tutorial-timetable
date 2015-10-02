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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <h1>Student</h1>
 * POJO representation of Student table in database.
 * Maps Student table as java Object.
 * 
 * <p>Named Query: getAllStudent, retrieve all student records</p>
 * 
 * <p>Named Query: getStudentByID, retrieves a single student record, specified by studentID</p>
 * 
 * <p>Named Query: removeAllStudent, delete all student records from the table</p>
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @since 10/1/2015
 */
@Entity
@Table(name = "student", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "getAllStudent", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "getStudentByID", query = "SELECT s FROM Student s WHERE s.studentId = :studentId"),
        @NamedQuery(name="removeAllStudent",
            query = "DELETE FROM Student")
})
public class Student implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<Studentsubject> studentsubjectCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "student_id", unique = true)
    private Integer studentId;
    
    @Size(max = 5)
    @Column(name = "title")
    private String title;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "f_name")
    private String fName;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "l_name")
    private String lName;
    
    @Size(max = 30)
    @Column(name = "m_name")
    private String mName;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
//    private Collection<Studentsubject> studentsubjectCollection;

    public Student() {
    }

    public Student(Integer studentId, String fName, String lName, String mName, String title) {
        this.studentId = studentId;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.title = title;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    
    public String getName(){
        return this.title+" "+this.fName + " " + this.mName + " " +this.lName;
    }

    @XmlTransient
    public Collection<Studentsubject> getStudentsubjectCollection() {
        return studentsubjectCollection;
    }

    public void setStudentsubjectCollection(Collection<Studentsubject> studentsubjectCollection) {
        this.studentsubjectCollection = studentsubjectCollection;
    }
}
