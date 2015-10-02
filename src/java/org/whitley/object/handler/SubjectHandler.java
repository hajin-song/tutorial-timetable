/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.whitley.object.entities.Student;
import org.whitley.object.entities.Studentsubject;
import org.whitley.object.entities.Subject;
import org.whitley.validator.ValidatorObject;

/**
 * <h1>SubjectHandler</h1>
 * Handles all subject entity in database.
 * @author Ha Jin Song
 * @version 1.2
 * @see org.whitley.object.handler.BasicHandler
 * @since 11/1/2015
 */
@WebServlet(name = "SubjectHandler", urlPatterns = {"/SubjectHandler"})
public class SubjectHandler extends BasicHandler {
    
    /**
     * getAllSubject method.
     * Retrieve all subject records from database.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getAllSubject(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<Subject> results = subjectController.getAllSubject();
        request.setAttribute("result", results);
        request.getRequestDispatcher("/subject/view.jsp").forward(request, response);
    }
    
    /**
     * getSubjectDetail method.
     * Retrieve the detail of a specific subject.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getSubjectDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String subjectCode = request.getParameter("subjectcode");
        Subject subject = subjectController.findSubjectByCode(subjectCode);
        List<Studentsubject> sss = ssController.findBySubject(subjectCode);
        ArrayList <Student> students = new ArrayList<Student>() {};
        for(Studentsubject ss : sss){
            Student student = studController.findStudent(ss.getStudentsubjectPK().getStudentId());
            students.add(student);
        }

        request.setAttribute("subject",subject);
        request.setAttribute("students",students);
        request.setAttribute("sss",sss);
        request.getRequestDispatcher("/subject/detailed_view.jsp").forward(request, response);
    }
    
    /**
     * filterSubject method.
     * Filter subject by subject level and semester.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void filterSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{        
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        List<Studentsubject> ss;
        errors = new ArrayList<>();
        //Extract data from parameter and prepare for validation
        String raw_level[] = request.getParameterValues("level");
        if(raw_level == null){
           request.setAttribute("result", subjectController.getAllSubject());   
           request.getRequestDispatcher("/subject/view.jsp").forward(request, response);
        }
        
        for(String s : raw_level){
            inputList.add(new ValidatorObject(s,"int","level"));
        }
        String raw_semester = request.getParameter("semester");
        inputList.add(new ValidatorObject(raw_semester,"int","semester"));
        List<Subject> results = new ArrayList<>();
        //Validate
        errors = validate(inputList);
        //Proceed only if validation passed
        if(errors.isEmpty()){
           int semester = Integer.parseInt(raw_semester);
           for(String s : raw_level){
               results.addAll(subjectController.filterSubject(s.charAt(0)));
           }
           Iterator<Subject> ite = results.iterator();
           while(ite.hasNext()){
               Subject temp = ite.next();
               ss = ssController.findBySubjectSemester(temp.getSubjectCode(),semester); 
               if(ss.isEmpty()){
                   ite.remove();
               }
           }       
           request.setAttribute("result", results);         
        }else{
           request.setAttribute("errors", errors);
           results = subjectController.getAllSubject();
           request.setAttribute("result", results);            
        }

        request.getRequestDispatcher("/subject/view.jsp").forward(request, response);
        
    }
    
    /**
     * removeSubjectStudent method.
     * remove a student-subject relation of the subject.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void removeSubjectStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            int studentID = Integer.parseInt(request.getParameter("studentid"));
            String subjectCode = request.getParameter("subjectcode");
            int semester = Integer.parseInt(request.getParameter("semester"));
            ssController.removeTarget(studentID,subjectCode,semester);
            subjectController.subtractToSize(subjectCode);
            getSubjectDetail(request,response);
    }
    
    /**
     * addSubjectStudent method.
     * add Subject Student relationship to current subject.
     * Takes user input, student ID and semester.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.2
     */
    private void addSubjectStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        //Extract information from request parameter, prepare for validation
        String studentID = request.getParameter("ID");
        inputList.add(new ValidatorObject(studentID,"int","Student ID"));
        String subjectCode = request.getParameter("subjectcode");
        inputList.add(new ValidatorObject(subjectCode,"char","Subject Code"));
        String semester = request.getParameter("semester");
        inputList.add(new ValidatorObject(semester,"int","Semester"));
        errors = validate(inputList);
        if(errors.isEmpty()){
            int studentNo = Integer.parseInt(studentID);
            if(!studController.checkDuplicate(studentNo)){
                Studentsubject ss = new Studentsubject(Integer.parseInt(studentID),
                        subjectCode,Integer.parseInt(semester));
                if(ssController.checkDuplicate(ss)){
                        ssController.addStudentSubject(ss);
                        errors.add("Success");
                }else{
                    errors.add("Duplicate Relation");
                }
            }else{
                errors.add("Invalid Student ID");
                
            }

        }
        request.setAttribute("errors", errors);
         getSubjectDetail(request,response);
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
            getAllSubject(request,response);
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
            getSubjectDetail(request,response);
        }else if(action.compareTo("Remove Student")==0){
            removeSubjectStudent(request,response);
        }else if(action.compareTo("Filter")==0){
            filterSubject(request,response);
        }else if(action.compareTo("Register Student")==0){
            addSubjectStudent(request,response);
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
