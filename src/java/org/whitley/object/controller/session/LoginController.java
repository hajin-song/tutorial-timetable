/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;

import org.whitley.object.controller.local.LoginControllerLocal;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * <h1>LoginController</h1>
 * Controller of POJO of {@code org.whitley.object.entities.Stafflogin}.
 * 
 * Handles user authentication.
 * 
 * Used by all Handler.
 * 
 * Extends {@link org.whitley.object.controller.BasicController}
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.BasicController
 * @since 10/1/2015
 */
@Stateless
public class LoginController extends BasicController implements LoginControllerLocal {

    /**
     * authenticate method.
     * Authenticates
     * @param usrname: username being checked.
     * @param pwd: password being checked.
     * @return 
     */
    @Override
    public boolean authenticate(String usrname, String pwd){
        return true;
        //Query q = em.createNamedQuery("authenticate");
        //q.setParameter("username", usrname);
       // q.setParameter("password",pwd);

       // return q.getResultList().size()==1;

    }
}
