/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.controller.local;

import java.util.List;
import javax.ejb.Local;
import org.whitley.object.entities.Staff;
import org.whitley.object.entities.Staffpreference;

/**
 * <h1>StaffControllerLocal</h1>
 * Local Bean of {@link org.whitley.object.controller.session.StaffController}
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.session.StaffController
 * @since 10/1/2015
 */
@Local
public interface StaffControllerLocal {
    public boolean addStaff(Staff staff);
    public void removeStaff(int staffId);
    public Staff findStaffByName(String fName, String lName, String mName);
    public Staff findStaffByID(int id);
    public List<Staff> findAllStaff();
    public List<Staffpreference> findStaffPreference(Staff staff);
    public Staffpreference findPreferencebyID(int prefid);
    public void addStaffPreference(Staffpreference staffpreference);
    public void removeStaffPreference(Staffpreference staffpreference);
}
