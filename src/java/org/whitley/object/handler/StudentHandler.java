/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.whitley.object.entities.Student;
import org.whitley.object.entities.Studentsubject;

/**
 * <h1>StudentHandler</h1>
 * Handles student entity in database.
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.object.handler.BasicHandler
 * @since 11/1/2015
 */
@WebServlet(name = "StudentHandler", urlPatterns = {"/StudentHandler"})
public class StudentHandler extends BasicHandler {

    /**
     * getAllStudents method.
     * Retrieve all student records from database
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getAllStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> results = studController.getAllStudent();
        request.setAttribute("result", results);
        request.getRequestDispatcher("/student/view.jsp").forward(request, response);
        
    }
    
    /**
     * getStudentDetail method.
     * Retrieve detail of a specific student
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getStudentDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        int studentID = Integer.parseInt(request.getParameter("studentid"));
        Student student = studController.findStudent(studentID);
        List<Studentsubject> subjects = ssController.findByStudent(studentID);
        request.setAttribute("student",student);
        request.setAttribute("subjects",subjects);
        request.getRequestDispatcher("/student/detailed_view.jsp").forward(request, response);
    }
    
    /**
     * removeStudentSubject method.
     * Remove specified student-subject relation of the specific student.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void removeStudentSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            int studentID = Integer.parseInt(request.getParameter("studentid"));
            String subjectCode = request.getParameter("subjectcode");
            int semester = Integer.parseInt(request.getParameter("semester"));
            ssController.removeTarget(studentID,subjectCode,semester);
            
            getStudentDetail(request,response);
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
        getAllStudents(request,response);
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
            getStudentDetail(request,response);
        }else if(action.compareTo("Remove Subject")==0){
            removeStudentSubject(request,response);
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
