/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.whitley.object.controller.local.TutorialControllerLocal;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.whitley.object.entities.Day;
import org.whitley.object.entities.Timestream;

/**
 * <h1>TutorialController</h1>
 * Controller of POJO of {@code org.whitley.object.entities.Timestream}.
 * Receive requests from {@link org.whitley.object.handler.TutorialHandler} then
 * access database to handle request.
 * 
 * Provides interface for CRUD operation.
 * 
 * Extends {@link org.whitley.object.controller.BasicController}
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.handler.TutorialHandler
 * @see org.whitley.object.controller.BasicController
 * @since 10/1/2015
 */
@Stateless
public class TutorialController extends BasicController implements TutorialControllerLocal {


    /**
     * getAllStream method.
     * get all timestream records from database.
     * @return List of Timestream.
     */
    @Override
    public List<Timestream> getAllStream() {
        Query q = em.createNamedQuery("getAllTimestream");
        return (List<Timestream>) q.getResultList();
    }
    
    /**
     * addStream method.
     * Add a new stream recrod to the database.
     * @param stream: new record to add.
     * @return true if successfully committed, else false.
     */
    @Override
    public boolean addStream(Timestream stream){
        String day = stream.getTimestreamPK().getStreamDay().getDay();
        String start = stream.getTimestreamPK().getStreamTimeStart();
        String end = stream.getTimestreamPK().getStreamTimeEnd();
        
        if(findStream(day,start,end)==null){
            em.persist(stream);
            return true;
        }
        return false;
    }

    /**
     * findStream method.
     * Find a specific stream from database, gvien day, start and end.
     * @param day: Day of the timestream (String)
     * @param start: Start time of the timestream (String)
     * @param end: end Time of the timestream (String)
     * @return single Timestream record matching the input, null if not found.
     */
    @Override
    public Timestream findStream(String day, String start, String end){

        try {
            Query q = em.createNamedQuery("findStream");
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date startTIme, endTime;
            startTIme = df.parse(start);
            endTime = df.parse(end);

            q.setParameter("streamDay",Day.valueOf(day));

            q.setParameter("streamTimeStart",startTIme);
            q.setParameter("streamTimeEnd",endTime);
            return (Timestream)q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
 
    /**
     * removeStream method.
     * Remove a record from database, specified by day, start and end.
     * @param day: Day of the timestream (String)
     * @param start: Start time of the timestream (String)
     * @param end: end Time of the timestream (String)
     */
    @Override
    public void removeStream(String day, String start, String end) {
        try {
            Query q = em.createNamedQuery("findStream");
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date startTIme, endTime;
            startTIme = df.parse(start);
            endTime = df.parse(end);
            q.setParameter("streamDay",Day.valueOf(day));
            q.setParameter("streamTimeStart",startTIme);
            q.setParameter("streamTimeEnd",endTime);
            em.remove((Timestream)q.getSingleResult());
        } catch (ParseException ex) {
            Logger.getLogger(TutorialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
