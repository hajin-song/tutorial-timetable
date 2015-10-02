/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;

import java.util.List;
import org.whitley.object.controller.local.StaffControllerLocal;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.whitley.object.entities.Staff;
import org.whitley.object.entities.Staffpreference;


/**
 * <h1>StaffController</h1>
 * Controller of POJO of {@code org.whitley.object.entities.Staff}.
 * {@link org.whitley.object.handler.StaffHandler} 
 * and {@link org.whitley.object.handler.TutorialHandler} then
 * access database to handle request.
 * 
 * Provides interface for CRUD operation.
 * 
 * Extends {@link org.whitley.object.controller.BasicController}
 * 
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.handler.StaffHandler
 * @see org.whitley.object.handler.TutorialHandler
 * @see org.whitley.object.controller.BasicController
 * @since 10/1/2015
 */
@Stateless
public class StaffController extends BasicController implements StaffControllerLocal {
    
    /**
     * addStaff method.
     * Add new staff to the table.
     * @param staff: Staff to add.
     * @return true if successful, else false.
     */
    @Override
    public boolean addStaff(Staff staff){
        Query q = em.createNamedQuery("findByName");
        q.setParameter("fName", staff.getFName());
        q.setParameter("mName", staff.getMName());
        q.setParameter("lName", staff.getLName());
        try{
            q.getSingleResult();
            return false;
        }catch (Exception e){
            em.persist(staff);
            return true;
        }
    }

    /**
     * findStaffByID method.
     * Find a single staff record, specified by the given staff ID.
     * @param id: Staff ID to search by.
     * @return Staff POJO
     */
    @Override
    public Staff findStaffByID(int id){
        Query q = em.createNamedQuery("findByID");
        q.setParameter("id", id);
        return (Staff)q.getSingleResult();
    }
    
    /**
     * findStaffByName method.
     * Find a staff by their name.
     * @param fName: First Name of Staff
     * @param lName: Last Name of Staff
     * @param mName: Middle Name of Staff
     * @return Staff POJO.
     */
    @Override
    public Staff findStaffByName(String fName, String lName, String mName){
        Query q = em.createNamedQuery("findByName");
        q.setParameter("fName", fName);
        q.setParameter("lName", lName);
        q.setParameter("mName", mName);
        return (Staff)q.getSingleResult();
    }

    /**
     * findAllStaff method.
     * Retrieve all staff records from the table.
     * @return List of Staff POJOs.
     */
    @Override
    public List<Staff> findAllStaff() {
        Query q = em.createNamedQuery("getAllStaff");
        return (List<Staff>)q.getResultList();
    }

    /**
     * removeStaff method.
     * Delete a single staff from the table, specified by the ID.
     * @param staffId: Staff ID of the staff to remove.
     */
    @Override
    public void removeStaff(int staffId) {
        Query q = em.createNamedQuery("findByID");
        q.setParameter("id", staffId);
        Staff staff = (Staff)q.getSingleResult();
        List<Staffpreference> prefs = findStaffPreference(staff);
        for(Staffpreference pref : prefs){
            removeStaffPreference(pref);
        }
        em.remove(staff);
    }
    
    
    /**
     * findStaffPreference method.
     * Find time preferences of the given staff.
     * @param staff: Staff to search by.
     * @return List of Staffpreference POJOs.
     */
    @Override
    public List<Staffpreference> findStaffPreference(Staff staff){
        Query q = em.createNamedQuery("findByStaffID");
        q.setParameter("staffId", staff);
        try{
        return (List<Staffpreference>)q.getResultList();
        }catch (Exception e){
            return null;
        }
    }
    
    /**
     * findPreferencebyID method.
     * Find Staff preference by its ID.
     * @param prefid: ID to search by.
     * @return Staffpreference POJO.
     */
    @Override
    public Staffpreference findPreferencebyID(int prefid){
        Query q = em.createNamedQuery("findByPrefID");
        q.setParameter("id", prefid);
        return (Staffpreference)q.getSingleResult();
    }
    
    /**
     * addStaffPreference method.
     * Add new staff preference record.
     * @param staffpreference: preference to add.
     */
    @Override
    public void addStaffPreference(Staffpreference staffpreference){
        em.persist(staffpreference);
    }
    
    /**
     * removeStaffPreference method.
     * Remove a staff preference from record.
     * @param staffpreference: preference to remove.
     */
    @Override
    public void removeStaffPreference(Staffpreference staffpreference){
        Staffpreference removing = em.merge(staffpreference);
        em.remove(removing);
    }
}
