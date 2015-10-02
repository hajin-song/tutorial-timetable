/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.local;

import java.util.List;
import javax.ejb.Local;
import org.whitley.object.entities.Student;

/**
 * <h1>StudentControllerLocal</h1>
 * Local Bean of {@link org.whitley.object.controller.session.StudentController}
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.session.StudentController
 * @since 10/1/2015
 */
@Local
public interface StudentControllerLocal {
    public void addStudent(Student student);
    public List<Student> getAllStudent();
    public Student findStudent(Integer id);
    public boolean checkDuplicate(Student student);
    public boolean checkDuplicate(int id);
    public void removeAll();
}
