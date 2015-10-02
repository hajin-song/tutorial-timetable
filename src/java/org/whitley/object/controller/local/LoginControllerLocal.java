/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.local;

import javax.ejb.Local;

/**
 * <h1>LoginControllerLocal</h1>
 * Local Bean of {@link org.whitley.object.controller.session.LoginController}
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.session.LoginController
 * @since 10/1/2015
 */
@Local
public interface LoginControllerLocal {
     public boolean authenticate(String usrname, String pwd);
}
