/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entitiyPK;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hajin
 */
@Embeddable
public class StudentsubjectPK implements Serializable {

    @NotNull
    @Column(name = "student_id")
    private int studentId;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "subject_code")
    private String subjectCode;

    @NotNull
    @Column(name = "semester")
    private int semester;

    public StudentsubjectPK() {
    }

    public StudentsubjectPK(int studentId, String subjectCode, int semester) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.semester = semester;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) studentId;
        hash += (subjectCode != null ? subjectCode.hashCode() : 0);
        hash += (int) semester;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentsubjectPK)) {
            return false;
        }
        StudentsubjectPK other = (StudentsubjectPK) object;
        if (this.studentId != other.studentId) {
            return false;
        }
        if ((this.subjectCode == null && other.subjectCode != null) || (this.subjectCode != null && !this.subjectCode.equals(other.subjectCode))) {
            return false;
        }
        if (this.semester != other.semester) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.whitley.object.entities.StudentsubjectPK[ studentId=" + studentId + ", subjectCode=" + subjectCode + ", semester=" + semester + " ]";
    }
    
}
