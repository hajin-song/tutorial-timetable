/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;

import java.util.Iterator;
import java.util.List;
import org.whitley.object.controller.local.SubjectControllerLocal;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.whitley.object.entities.Staff;
import org.whitley.object.entities.Subject;

/**
 * <h1>SubjectController</h1>
 * Controller of POJO of {@code org.whitley.object.entities.Subject}.
 * Receive requests from {@link org.whitley.object.handler.SubjectHandler} 
 * and {@link org.whitley.object.handler.TutorialHandler} then
 * access database to handle request.
 * 
 * Provides interface for CRUD operation.
 * 
 * Extends {@link org.whitley.object.controller.BasicController}
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.handler.SubjectHandler
 * @see org.whitley.object.controller.BasicController
 * @since 10/1/2015
 */
@Stateless
public class SubjectController extends BasicController implements SubjectControllerLocal {

    /**
     * addSubject method.
     * Add new subject record to the database.
     * @param subject: subject to add.
     */
    @Override
    public void addSubject(Subject subject) {
        em.persist(subject);
    }
    
    /**
     * getAllSubject method.
     * Retrieve all records of the Subject table in database.
     * @return List of POJO subjects.
     */
    @Override
    public List<Subject> getAllSubject(){
        Query q = em.createNamedQuery("getAllSubject");
        return (List<Subject>) q.getResultList();
        
    }
    
    /**
     * removeAll method.
     * Remove all records from Subject table.
     */
    @Override
    public void removeAll(){
        Query q = em.createNamedQuery("removeAllSubject");
        q.executeUpdate();
        
    }
    
    /**
     * findSubjectByCode method.
     * Find a single subject record specified by subjectCode.
     * @param subjectCode: Code of the subject to retrieve (String)
     * @return Single Subject POJO.
     */
    @Override
    public Subject findSubjectByCode(String subjectCode){
        Query q = em.createNamedQuery("getSubjectByCode");
        q.setParameter("subjectCode", subjectCode);
        return (Subject)q.getSingleResult();
    }
    
    /**
     * addToSize method.
     * Add student number to specified subject.
     * @param subject: Subject to increase the student numbers.
     */
    @Override
    public void addToSize(Subject subject){
        subject = findSubjectByCode(subject.getSubjectCode());
        subject.setSize(subject.getSize() + 1);
        editSubject(subject);
    }
    /**
     * subtractToSize method.
     * Reduce student number of specified subject.
     * @param subjectCode: Subject Code of Subject to update (String)
     */
    @Override
    public void subtractToSize(String subjectCode){
        Subject subject = findSubjectByCode(subjectCode);
        subject.setSize(subject.getSize() - 1);
        editSubject(subject);
    }
    
    /**
     * filterSubject method.
     * Retrieve all subject records, then filter out depending on subject level.
     * @param subjectLevel: level of subjects to retrieve records of.
     * @return List of Subject POJO.
     */
    @Override
    public List<Subject> filterSubject(int subjectLevel){
        List<Subject> result = getAllSubject();
        Iterator<Subject> ite = result.iterator();
        while (ite.hasNext()){
            Subject temp = ite.next();
            if(temp.getSubjectCode().charAt(4)!=subjectLevel){
                ite.remove();
            }
        }
       
                    
        return result;
    }
    
    /**
     * findSubjectByStaff method.
     * Find subjects taught by specified staff.
     * @param staff: Staff to search by.
     * @return List of Subject POJO.
     */
    @Override
    public List<Subject> findSubjectByStaff(Staff staff){
        Query q = em.createNamedQuery("getSubjectByStaff");
        q.setParameter("staff", staff);
        return (List<Subject>)q.getResultList();
    }
    
    /**
     * checkDuplicate method.
     * Check if subject already exists in the table
     * @param subject: Subject to check for.
     * @return true if not a duplicate, else false.
     */
    @Override
    public boolean checkDuplicate(Subject subject){
        Query q = em.createNamedQuery("getSubjectByCode");
        q.setParameter("subjectCode", subject.getSubjectCode());
        return q.getResultList().isEmpty();
    }

    /**
     * editSubject method.
     * Update subject record
     * @param subject: Subject to update.
     */
    @Override
    public void editSubject(Subject subject) {
        em.merge(subject);
        em.flush();
    }

        
    
}
