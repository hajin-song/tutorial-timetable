/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <h1>BasicController</h1>
 * Template for all controllers.
 * Defines EntityManager.
 * Extends {@link org.whitley.object.controller.BasicController}
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @since 10/1/2015
 */

public class BasicController {
    @PersistenceContext(name="WhitleyApplicationPU")
    
    //Is this good idea?
    protected EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
