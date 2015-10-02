/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>Stafflogin</h1>
 * POJO representation of Stafflogin table in database.
 * Maps Stafflogin table as java Object.
 * 
 * Security Measurement
 * 
 * <p>Named Query: authenticate, Verify login detail given by user</p>
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @since 10/1/2015
 */
@Entity
@Table(name = "stafflogin", catalog = "whitleydb", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "authenticate", 
            query = "SELECT s FROM Stafflogin s WHERE s.username = :username AND s.password=:password")
    })
public class Stafflogin implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @Column(name = "id",unique = true)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "username",unique = true)
    private String username;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "password")
    private String password;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)   
    private Staff staff;

    public Stafflogin() {
    }

    public Stafflogin(Integer id) {
        this.id = id;
    }

    public Stafflogin(String username, String password) {
        this.username = username;
        this.password = password;
    }









    
}
