/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import org.whitley.object.entities.Student;
import org.whitley.object.entities.Studentsubject;
import org.whitley.object.entities.Subject;
import org.whitley.validator.ValidatorObject;

/**
 * <h1>ImportHandler</h1>
 * Handles import of Excel data source.
 * The data source given by user is handled by this class.
 * All data source must be in format the University of Melbourne provide.
 * Any alteration to the data source will not allow the handler to process
 * the data source.
 * Extends BasicHandler.
 * @author Ha Jin Song
 * @version 1.2
 * @see org.whitley.object.handler.BasicHandler
 * @since 11/1/2015
 */
@WebServlet(name = "ImportHandler", urlPatterns = {"/ImportHandler"})
public class ImportHandler extends BasicHandler {
    
    /**
     * manualStudentAdd method.
     * Add Student Record manually.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.2
     */
    private void manualStudentAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        //Extract information from request parameter, prepare for validation
        String studentID = request.getParameter("ID");
        inputList.add(new ValidatorObject(studentID,"int","Student ID"));
        String studentFName = request.getParameter("fname");
        inputList.add(new ValidatorObject(studentFName,"char","Student First Name"));
        String studentMName = request.getParameter("mname");
        inputList.add(new ValidatorObject(studentMName,"char","Student Middle Name"));
        String studentLName = request.getParameter("lname");
        inputList.add(new ValidatorObject(studentLName,"char","Student Last Name"));
        String studentTitle = request.getParameter("title");
        inputList.add(new ValidatorObject(studentTitle,"char","Title"));
        errors = validate(inputList);
        if(errors.isEmpty()){
            Student student = new Student(Integer.parseInt(studentID), 
                    studentFName,studentMName,studentLName,studentTitle);
            if(studController.checkDuplicate(student)){
                    studController.addStudent(student);
                    errors.add("Success");
            }else{
                errors.add("Duplicate Student ID");
            }
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/import/addStudent.jsp").forward(request, response);
    }
    
    /**
    /**
     * manualSubjectAdd method.
     * Add Subject Record manually.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.2
     */
    private void manualSubjectAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        //Extract information from request parameter, prepare for validation
        String subjectCode = request.getParameter("code");
        inputList.add(new ValidatorObject(subjectCode,"char","Subject ID"));
        String subjectName = request.getParameter("name");
        inputList.add(new ValidatorObject(subjectName,"char","Subject Name"));
        errors = validate(inputList);
        if(errors.isEmpty()){
            Subject subject = new Subject(subjectCode, subjectName,0);
            if(subjectController.checkDuplicate(subject)){
                    subjectController.addSubject(subject);
                    errors.add("Success");
            }else{
                errors.add("Duplicate Subject");
            }
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/import/addSubject.jsp").forward(request, response);
    }
    
    
    /**
     * processImport method handling import of data source.
     * Process excel data source and record the data in database.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding to next page fails
     * @throws IOException if excel file parsing fails
     * @since 1.0
     */
    private  void processImport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        response.setContentType("text/html;charset=UTF-8");
        //Start processing
        try (PrintWriter out = response.getWriter()) {
            //Get file from user, upload to server for processing
            boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
            if (!isMultipartContent) {
                out.println("No file to be processed<br/>");
                return;
            }
            
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

           
            try {
                //Upload
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                //Read file
                FileItem fileItem =  (FileItem)iter.next();
                //Counting variables
                int no_rows=0 , no_students=0, no_subjects=0, no_ss=0;
                //Open Excel workbook
                Workbook wb = WorkbookFactory.create(fileItem.getInputStream());
                Sheet studSheet = wb.getSheetAt(0);
                //Read each line
                for(Row row : studSheet){
                    //Skip first line as title
                    if(row.getRowNum()==0){
                        continue;
                    }
                    no_rows++;
                    //Extract student,subject and their relation
                    Student student = extractStudent(row);
                    Subject subject = extractSubject(row);
                    Studentsubject ss = extractStudentsubject(row);
                    //Check for duplicates, if not duplicate, add to database
                    if(studController.checkDuplicate(student)){
                        studController.addStudent(student);
                        no_students++;
                    }
                    if(subjectController.checkDuplicate(subject)){
                        subjectController.addSubject(subject);
                        no_subjects++;
                    }else{
                        subjectController.addToSize(subject);
                    }
                    if(ssController.checkDuplicate(ss)){
                        ssController.addStudentSubject(ss);
                        no_ss++;
                    }
                }
                //finish parsing, forward to result page
                request.setAttribute("rows", no_rows);
                request.setAttribute("students", no_students);
                request.setAttribute("subjects", no_subjects);
                request.setAttribute("ss", no_ss);
                request.getRequestDispatcher("/import/result.jsp").forward(request, response);
            } catch (FileUploadException e) {
                out.println("File upload Error");
            } catch (InvalidFormatException e1){
                out.println("Incorrect Data format given");
            }               
        }
    }

    /**
     * extractStudent method, extracts student data from a row
     * @param row: excel data file row
     * @return Type of {@link org.whitley.object.entities.Student}, 
     * representing student recorded in the {@code row}
     * @since 1.0
     */
    private Student extractStudent(Row row){
        int student_id;
        String student_l_name, student_f_name, student_title;
        try{student_id = Integer.parseInt(row.getCell(0).getStringCellValue());}
        catch(Exception e){
            student_id = (int) row.getCell(0).getNumericCellValue();
        }
        student_l_name = row.getCell(1).toString();
        student_f_name = row.getCell(2).toString();
        try{
            student_title = row.getCell(4).toString();
        }catch(Exception e){
            student_title = "";
        }
        String student_m_name;
        try{
            student_m_name = row.getCell(3).toString();
        }catch (Exception e){
            student_m_name = "";
        }
       Student student =  new Student(student_id,student_f_name,student_l_name,student_m_name,student_title);
       return student;

    }
    
    /**
     * extractSubject method, extracts subject data from a row
     * @param row: excel data file row
     * @return Type of {@link org.whitley.object.entities.Subject}, 
     * representing subject recorded in the {@code row}
     * @since 1.0
     */
    private Subject extractSubject(Row row){
        String subject_code = row.getCell(10).toString();
        String subject_name = row.getCell(11).toString();
        Subject subject = new Subject(subject_code,subject_name,1);
        return subject;
    }
    
    /**
     * extractStudentsubject method, extracts subject student relation data from a row
     * @param row: excel data file row
     * @return Type of {@link org.whitley.object.entities.Studentsubject}, 
     * representing subject-subject relation recorded in the {@code row}
     * @since 1.0
     */
    private Studentsubject extractStudentsubject(Row row){
        int student_id;
        try{student_id = Integer.parseInt(row.getCell(0).getStringCellValue());}
        catch(Exception e){
            student_id = (int) row.getCell(0).getNumericCellValue();
        }
        String subject_code = row.getCell(10).toString();
        String studyPkgCode = row.getCell(5).toString();
        String studyPkgTitle = row.getCell(6).toString();
        String owningOrgUnit = row.getCell(7).toString();
        String degreeType = row.getCell(8).toString();
        String semester_temp = row.getCell(14).toString();
        int semester;
            if(semester_temp.compareTo("Semester 2")==0){
                semester = 2;
            }else if(semester_temp.compareTo("Semester 1")==0){
                semester = 1;
            }else{
                semester = 0;
            }
        Studentsubject ss = new Studentsubject(student_id, subject_code,semester,
                studyPkgCode,studyPkgTitle,owningOrgUnit,degreeType);
        return ss;
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

        try{
            if(action.compareTo("Add New Student")==0){
                manualStudentAdd(request,response);
            }else if(action.compareTo("Add New Subject")==0){
                manualSubjectAdd(request,response);
            }
        }catch (Exception e){
            ssController.removeAll();
            studentController.removeAll();
            subjectController.removeAll();
            processImport(request,response);  
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
