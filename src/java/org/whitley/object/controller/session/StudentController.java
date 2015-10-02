/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.session;

import java.util.List;
import org.whitley.object.controller.local.StudentControllerLocal;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.whitley.object.entities.Student;

/**
 * <h1>StudentController</h1>
 * Controller of POJO of {@code org.whitley.object.entities.Student}.
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
 * @see org.whitley.object.handler.StudentHandler
 * @see org.whitley.object.handler.TutorialHandler
 * @see org.whitley.object.controller.BasicController
 * @since 10/1/2015
 */
@Stateless
public class StudentController extends BasicController implements StudentControllerLocal {

    /**
     * addStudent method.
     * Add a new student record to table/
     * @param student: Student to add
     */
    @Override
    public void addStudent(Student student){
        em.persist(student);
    }
    
    /**
     * removeAll method.
     * Remove all student records from the table.
     */
    @Override
    public void removeAll(){
        Query q = em.createNamedQuery("removeAllStudent");
        q.executeUpdate();
    }
    
    /**
     * getAllStudent metod.
     * Retrieve all student records from the table.
     * @return List of Student POJOs.
     */
    @Override
    public List<Student> getAllStudent(){
        Query q = em.createNamedQuery("getAllStudent");
        return (List<Student>) q.getResultList();
    }
    
    /**
     * findStudent method.
     * Retrieve a single student record given student id.
     * @param id: Id of the student to search for.
     * @return Student POJO.
     */
    @Override
    public Student findStudent(Integer id){
        Query q = em.createNamedQuery("getStudentByID");
        q.setParameter("studentId", id);
        return (Student) q.getSingleResult();
    }
    
    /**
     * checkDuplicate method.
     * Check if given student already exist in the table.
     * @param student: Student to check for.
     * @return true if not duplicate, else false.
     */
    @Override
    public boolean checkDuplicate(Student student) {
        Query q = em.createNamedQuery("getStudentByID");
        q.setParameter("studentId", student.getStudentId());
        return q.getResultList().isEmpty();
    }
    
    /**
    * checkDuplicate method.
     * Check if given student already exist in the table.
     * uses ID
     * @param id: id of the student to cehck for.
     * @return true if not duplicate, else false.
     */
    @Override
    public boolean checkDuplicate(int id) {
        Query q = em.createNamedQuery("getStudentByID");
        q.setParameter("studentId", id);
        return q.getResultList().isEmpty();
    }
    
}
