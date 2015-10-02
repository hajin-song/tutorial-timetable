/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;

import java.util.List;
import org.whitley.object.controller.local.StudentSubjectControllerLocal;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.whitley.object.entities.Studentsubject;

/**
 * <h1>StudentSubjectController</h1>
 * Controller of POJO of {@code org.whitley.object.entities.Studentsubject}.
 * Receive requests from {@link org.whitley.object.handler.SubjectHandler},
 * {@link org.whitley.object.handler.StudentHandler} 
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
 * @see org.whitley.object.handler.StudentHandler
 * @see org.whitley.object.handler.TutorialHandler
 * @see org.whitley.object.controller.BasicController
 * @since 10/1/2015
 */
@Stateless
public class StudentSubjectController extends BasicController implements StudentSubjectControllerLocal {

    /**
     * addStudentSubject method.
     * Add new student subject relation to the table.
     * @param ss: relation to add.
     */
    @Override
    public void addStudentSubject(Studentsubject ss){
        em.persist(ss);
    }

    /**
     * findByStudent method.
     * Find all student subject relations of a student.
     * @param studentId: Student to search by.
     * @return List of Studentsubject POJOs.
     */
    @Override
    public List<Studentsubject> findByStudent(int studentId){
        Query q = em.createNamedQuery("getStudentSubjects");
        q.setParameter("studentId", studentId);
        return q.getResultList();
    }

    /**
     * findBySubject method.
     * Find all student subject relations of a subject.
     * @param subjectCode: Subject to search by.
     * @return List of Studentsubject POJOs.
     */
    @Override
    public List<Studentsubject> findBySubject(String subjectCode){
        Query q = em.createNamedQuery("getSubjectStudents");
        q.setParameter("subjectCode", subjectCode);
        return q.getResultList();
    }
    
    /**
     * removeAll method.
     * Remove all relations from the table.
     */
    @Override
    public void removeAll(){
        Query q = em.createNamedQuery("removeAllStudentSubject");
        q.executeUpdate();
        
    }

    /**
     * removeTarget method.
     * Remove a specific relation from the table.
     * @param studentid: Target Student ID
     * @param subjectcode: Target Subject Code
     * @param semester: Target semester
     */
    @Override
    public void removeTarget(int studentid, String subjectcode, int semester){
        
        Query q = em.createNamedQuery("findByPK");
        q.setParameter("studentId", studentid);
        q.setParameter("subjectCode",subjectcode);
        q.setParameter("semester",semester);
        em.remove((Studentsubject)q.getSingleResult());

    }
    
    /**
     * checkDuplicate method.
     * Check if a relation already exist in table.
     * @param ss: Relation to check for.
     * @return True if not duplicated, else false.
     */
    @Override
    public boolean checkDuplicate(Studentsubject ss) {
        Query q = em.createNamedQuery("findByPK");
        q.setParameter("studentId", ss.getStudentsubjectPK().getStudentId());
        q.setParameter("subjectCode",ss.getStudentsubjectPK().getSubjectCode());
        q.setParameter("semester",ss.getStudentsubjectPK().getSemester());
        return q.getResultList().isEmpty();
    }

    /**
     * findBySubjectSemester method.
     * Find relations of a given subject on a specified semester.
     * @param subjectCode: Target Subject code
     * @param semester: Target semester.
     * @return List of Studentsubject POJOs.
     */
    @Override
    public List<Studentsubject> findBySubjectSemester(String subjectCode, int semester) {
        Query q = em.createNamedQuery("getBySubjectSemester");
        q.setParameter("subjectCode", subjectCode);
        q.setParameter("semester",semester);
        return q.getResultList();
        
    }

}
