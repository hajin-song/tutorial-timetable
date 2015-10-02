/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.local;

import java.util.List;
import javax.ejb.Local;
import org.whitley.object.entities.Timestream;

/**
 * <h1>TutorialControlelrLocal</h1>
 * Local Bean of {@link org.whitley.object.controller.session.TutorialController}
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.session.TutorialController
 * @since 10/1/2015
 */
@Local
public interface TutorialControllerLocal {
 
    
    public List<Timestream> getAllStream();
    public boolean addStream(Timestream stream);
    public Timestream findStream(String day, String start, String end);
    public void removeStream(String day, String start, String end);
    

}
