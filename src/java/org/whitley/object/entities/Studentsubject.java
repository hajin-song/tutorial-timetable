/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

import org.whitley.object.entitiyPK.StudentsubjectPK;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>Studentsubject</h1>
 * POJO representation of Studentsubject table in database.
 * Maps Studentsubject table as java Object.
 * 
 * Has compound key, defined in {@code org.whitley.object.entitiyPK.StudentsubjectPK}
 * 
 * <p>Named Query: getAllStudentsubject, retrieves all studentsubject records</p>
 * 
 * <p>Named Query: getStudentSubjects, retrieves all studentsubject records of the given student</p>
 * 
 * <p>Named Query: getSubjectStudents, retrieves all studentsubject records of the given subject</p>
 * 
 * <p>Named Query: StudentInSubject, retrieve single studentsubject record of given subject and student</p>
 * 
 * <p>Named Query: getBySubjectSemester, retrieve all studentsubject records of the given subject and semester</p>
 * 
 * <p>Named Query: removeAllStudentSubject, delete all student subject records from the table</p>
 * 
 * <p>Named Query: findByPK, Find a single studentsubject record, specified by its PK (studentID,subjectCode,Semester)</p>
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.entitiyPK.StudentsubjectPK
 * @since 10/1/2015
 */
@Entity
@Table(name = "studentsubject", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "getAllStudentsubject", query = "SELECT s FROM Studentsubject s"),
    @NamedQuery(name = "getStudentSubjects", query = "SELECT s FROM Studentsubject s WHERE s.studentsubjectPK.studentId = :studentId"),
    @NamedQuery(name = "getSubjectStudents", query = "SELECT s FROM Studentsubject s WHERE s.studentsubjectPK.subjectCode = :subjectCode"),
    @NamedQuery(name="StudentInSubject",
            query="SELECT s FROM Studentsubject s WHERE s.studentsubjectPK.subjectCode=:subjectCode AND s.studentsubjectPK.studentId = :studentId"),
    @NamedQuery(name="getBySubjectSemester",
            query="SELECT s FROM Studentsubject s WHERE s.studentsubjectPK.subjectCode=:subjectCode AND s.studentsubjectPK.semester = :semester"),
    @NamedQuery(name="removeAllStudentSubject",
            query = "DELETE FROM Studentsubject"),
    @NamedQuery(name="findByPK",
            query="SELECT s FROM Studentsubject s WHERE s.studentsubjectPK.subjectCode=:subjectCode AND s.studentsubjectPK.studentId = :studentId AND s.studentsubjectPK.semester =:semester")
        
})
public class Studentsubject implements Serializable {
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;
    @JoinColumn(name = "subject_code", referencedColumnName = "subject_code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Subject subject;
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected StudentsubjectPK studentsubjectPK;
    
    @Size(max = 10)
    @Column(name = "study_pkg_code")
    private String studyPkgCode;
    
    @Size(max = 50)
    @Column(name = "study_pkg_title")
    private String studyPkgTitle;
    
    @Size(max = 100)
    @Column(name = "owning_org_unit")
    private String owningOrgUnit;
    
    @Size(max = 20)
    @Column(name = "degree_type")
    private String degreeType;

    public Studentsubject() {
    }
    public Studentsubject(int studentId, String subjectCode, int semester) {
        this.studentsubjectPK = new StudentsubjectPK(studentId, subjectCode, semester);
        this.studyPkgCode = "";
        this.studyPkgTitle = "";
        this.owningOrgUnit = "";
        this.degreeType = "";
    }
    
    public Studentsubject(int studentId, String subjectCode, int semester,
            String studyPkgCode,String studyPkgTitle,String owningOrgUnit,String degreeType) {
        this.studentsubjectPK = new StudentsubjectPK(studentId, subjectCode, semester);
        if(studyPkgCode.length() < 10 ){
            this.studyPkgCode = studyPkgCode;
        }else{
            this.studyPkgCode = "";
        
        }
        if(studyPkgTitle.length() < 50 ){
            this.studyPkgTitle = studyPkgTitle;
        }else{
            this.studyPkgTitle = "";
        
        }
        this.owningOrgUnit = owningOrgUnit;
        if(degreeType.length() < 20 ){
            this.degreeType = degreeType;
        }else{
            this.degreeType = "";
        
        }
    }
    public StudentsubjectPK getStudentsubjectPK() {
        return studentsubjectPK;
    }

    public void setStudentsubjectPK(StudentsubjectPK studentsubjectPK) {
        this.studentsubjectPK = studentsubjectPK;
    }

    public String getStudyPkgCode() {
        return studyPkgCode;
    }

    public void setStudyPkgCode(String studyPkgCode) {
        this.studyPkgCode = studyPkgCode;
    }

    public String getStudyPkgTitle() {
        return studyPkgTitle;
    }

    public void setStudyPkgTitle(String studyPkgTitle) {
        this.studyPkgTitle = studyPkgTitle;
    }

    public String getOwningOrgUnit() {
        return owningOrgUnit;
    }

    public void setOwningOrgUnit(String owningOrgUnit) {
        this.owningOrgUnit = owningOrgUnit;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
}
