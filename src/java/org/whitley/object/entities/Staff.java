/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <h1>Staff</h1>
 * Maps Staff table in the database.
 * Defines NamedQueries, getAllStaff, findByID and findByName.
 * 
 * <p>getAllStaff retrieves all record in the table</p>
 * <p>findByID retrieves a staff specified by the given ID</p>
 * <p>findByName retrieves a staff specified by the given name</p>
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.tutorial.objects.TuteStudent
 * @see org.whitley.tutorial.objects.TuteSubject
 * @see org.whitley.tutorial.objects.Constraint
 * @since 28/7/2014
 */
@Entity
@Table(name = "staff", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "getAllStaff", query = "SELECT s FROM Staff s WHERE s.id != 1 ORDER BY s.lName ASC"),
    @NamedQuery(name = "findByID", query = "SELECT s FROM Staff s WHERE s.id = :id"),
    @NamedQuery(name = "findByName", 
            query = "SELECT s FROM Staff s WHERE s.fName = :fName AND s.lName = :lName AND s.mName = :mName")
})
public class Staff implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffId")
    private Collection<Staffpreference> staffpreferenceCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    

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
    
    @OneToMany(mappedBy = "staffId")
    private Collection<Subject> subjectCollection;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "staff", optional=false)
    private Stafflogin stafflogin;

    public Staff() {
    }


    public Staff(String fName, String mName, String lName) {
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @XmlTransient
    public Collection<Subject> getSubjectCollection() {
        return subjectCollection;
    }

    public void setSubjectCollection(Collection<Subject> subjectCollection) {
        this.subjectCollection = subjectCollection;
    }

    public Stafflogin getStafflogin() {
        return stafflogin;
    }

    public void setStafflogin(Stafflogin stafflogin) {
        this.stafflogin = stafflogin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.whitley.object.entities.Staff[ id=" + id + " ]";
    }
    
    public String getName(){
        return this.fName + " " + this.mName + " " +this.lName;
    }

    @XmlTransient
    public Collection<Staffpreference> getStaffpreferenceCollection() {
        return staffpreferenceCollection;
    }

    public void setStaffpreferenceCollection(Collection<Staffpreference> staffpreferenceCollection) {
        this.staffpreferenceCollection = staffpreferenceCollection;
    }

}
