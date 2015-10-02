/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.local;

import java.util.List;
import javax.ejb.Local;
import org.whitley.object.entities.Studentsubject;

/**
 * <h1>StudentSubjectControllerLocal</h1>
 * Local Bean of {@link org.whitley.object.controller.session.StudentSubjectController}
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.session.StudentSubjectController
 * @since 10/1/2015
 */
@Local
public interface StudentSubjectControllerLocal {
    public void addStudentSubject(Studentsubject ss);
    public List<Studentsubject> findByStudent(int id);
    public List<Studentsubject> findBySubject(String subjectCode);
    public List<Studentsubject> findBySubjectSemester(String subjectCode, int semester);
    public boolean checkDuplicate(Studentsubject ss);
    public void removeTarget(int studentid, String subjectcode, int semester);
    public void removeAll();

}
