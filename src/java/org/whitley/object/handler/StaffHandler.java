/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.whitley.object.entities.Staff;
import org.whitley.object.entities.Staffpreference;
import org.whitley.object.entities.Timestream;
import org.whitley.validator.ValidatorObject;

/**
 * <h1>StaffHandler</h1>
 * Handles staff entity in the database.
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.object.handler.BasicHandler
 * @since 11/1/2015
 */
@WebServlet(name = "StaffHandler", urlPatterns = {"/StaffHandler"})
public class StaffHandler extends BasicHandler {

    /**
     * getAllStaff method.
     * Retrieve all staff records from database
     * @param request: HTTP reuqest
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getAllStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Staff> results = staffController.findAllStaff();
        request.setAttribute("result", results);
        request.getRequestDispatcher("/staff/view.jsp").forward(request, response);
        
    }
    
    /**
     * addNewStaff method.
     * Add new staff record to database.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void addNewStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        //Extract data and prepare for validation
        String fname = request.getParameter("fname");
        inputList.add(new ValidatorObject(fname,"char","First Name"));
        String mname = request.getParameter("mname");
        //Allow blank middle name
        if(!("".equals(mname))){
            inputList.add(new ValidatorObject(mname,"char","Middle Name"));
        }
        String lname = request.getParameter("lname");
        inputList.add(new ValidatorObject(lname,"char","Last Name"));
        //Validate
        errors = validate(inputList);
        //Proceed if validated
        if(errors.isEmpty()){
            Staff staff = new Staff(fname,mname,lname);
            //Avoid duplicate record
            if(!staffController.addStaff(staff)){
                 errors.add("Tutor already Exists");
            }
        }
            request.setAttribute("errors", errors);
            getAllStaff(request,response);
    }
    
    /**
     * removeStaff method.
     * Remove a staff from database record.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/ data forwarding fails
     * @since 1.0
     */
    private void removeStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            ArrayList<ValidatorObject> inputList = new ArrayList<>();
            errors = new ArrayList<>();
            String raw_staffId = request.getParameter("staffid");
            inputList.add(new ValidatorObject(raw_staffId,"int","staff ID"));
            errors = validate(inputList);
            if(errors.isEmpty()){
                
                
                int staffId = Integer.parseInt(raw_staffId);
                Staff temp =staffController.findStaffByID(staffId);
                List<Staffpreference> preference = staffController.findStaffPreference(temp);
                for(Staffpreference pref : preference){
                    staffController.removeStaffPreference(pref);
                }
                staffController.removeStaff(staffId);
            }

            request.setAttribute("errors",errors);
            getAllStaff(request,response);
    }  
    
    /**
     * getStaffDetail method.
     * Retrieve detail of a specific staff.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getStaffDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //Extract staff id
        int staffId = Integer.parseInt(request.getParameter("staffid"));
        //Get staff
        Staff staff = staffController.findStaffByID(staffId);
        //Get staff preference
        List<Staffpreference> preference = staffController.findStaffPreference(staff);
        //Get all timestreams
        List<Timestream> streams = tutorialController.getAllStream();
        Iterator<Timestream> streamIte = streams.iterator();
        //Remove from timestream if it already exist in staff preference
        while(streamIte.hasNext()){
            Timestream temp = streamIte.next();
            for(Staffpreference pref : preference){
                if(temp.getDay().equals(pref.getStreamDay())&&
                        temp.getStartTime().equals(pref.getStreamTimeStart())&&
                        temp.getEndTime().equals(pref.getStreamEndTime())){
                    try{streamIte.remove();}catch(Exception e){continue;};
                }
            }
        }
        Collections.sort(streams, (Timestream t1, Timestream t2) -> {
            int dayCmp = t1.getDay().compareTo(t2.getDay());
            if (dayCmp != 0){
                return dayCmp;
            }
            int startCmp = t1.getStartTime().compareTo(t2.getStartTime());
            if (startCmp != 0){
                return startCmp;
            }
            return t1.getEndTime().compareTo(t2.getEndTime());
        });  
        Collections.sort(preference, (Staffpreference t1, Staffpreference t2) -> {
            int dayCmp = t1.getStreamDay().compareTo(t2.getStreamDay());
            if (dayCmp != 0){
                return dayCmp;
            }
            int startCmp = t1.getSSTDate().compareTo(t2.getSSTDate());
            if (startCmp != 0){
                return startCmp;
            }
            return t1.getSETDate().compareTo(t2.getSETDate());
        });  
        request.setAttribute("staff",staff);
        request.setAttribute("streams",streams);
        request.setAttribute("preference",preference);
        request.getRequestDispatcher("/staff/detailed_view.jsp").forward(request, response);
    }
    
    /**
     * removePreference method.
     * Remove a staff preferene from their preference list.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void removePreference(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //Get staff preference
        Staffpreference pref = staffController.findPreferencebyID(Integer.parseInt(request.getParameter("prefID")));
        staffController.removeStaffPreference(pref);
        getStaffDetail(request,response);
    }
    
    /**
     * editStaffDetail method.
     * Edit detail of a staff.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void editStaffDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String[] prefToAdd = request.getParameterValues("add");
        //Add preferences
        try{
        for(String s : prefToAdd){
            String[] temp = s.split("_");
            String day = temp[0];
            String start = temp[1];
            String end = temp[2];
            Staff tempStaff = staffController.findStaffByID(Integer.parseInt(request.getParameter("staffid")));
            Staffpreference newpref = new Staffpreference(day,start,end,tempStaff);
            staffController.addStaffPreference(newpref);
        }
        }catch (Exception e){
        }
        getStaffDetail(request,response);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!authenticate(request)){
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        getAllStaff(request,response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!authenticate(request)){
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        String action = request.getParameter("action");
        if(action.compareTo("View Detail")==0){
            getStaffDetail(request,response);
        }else if(action.compareTo("Add New Tutor")==0){
            addNewStaff(request,response);
        }else if(action.compareTo("Remove Tutor")==0){
            removeStaff(request,response);
        }else if(action.compareTo("Edit Tutor")==0){
            editStaffDetail(request,response);
        }else if(action.compareTo("Remove Availability")==0){
            removePreference(request,response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
