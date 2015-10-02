/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.local;

import java.util.List;
import javax.ejb.Local;
import org.whitley.object.entities.Staff;
import org.whitley.object.entities.Subject;

/**
 * <h1>SubjectControllerLocal</h1>
 * Local Bean of {@link org.whitley.object.controller.session.SubjectController}
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.session.SubjectController
 * @since 10/1/2015
 */
@Local
public interface SubjectControllerLocal {
    public void addSubject(Subject subject);
    public List<Subject> getAllSubject();
    public List<Subject> filterSubject(int subjectLevel);
    public Subject findSubjectByCode(String subjectCode);
    public boolean checkDuplicate(Subject subject);
    public List<Subject> findSubjectByStaff(Staff staff);
    public void editSubject(Subject subject);
    public void removeAll();
    public void addToSize(Subject subject);
    public void subtractToSize(String subject);
}
