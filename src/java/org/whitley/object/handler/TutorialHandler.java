/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.whitley.object.entities.Day;
import org.whitley.object.entities.Room;
import org.whitley.tutorial.objects.TuteRoom;
import org.whitley.object.entities.Staff;
import org.whitley.object.entities.Staffpreference;
import org.whitley.object.entities.Student;
import org.whitley.object.entities.Studentsubject;
import org.whitley.object.entities.Subject;
import org.whitley.object.entities.Timestream;
import org.whitley.tutorial.objects.TuteStream;
import org.whitley.tutorial.objects.TuteStudent;
import org.whitley.tutorial.objects.TuteSubject;
import org.whitley.tutorial.objects.TuteTable;
import org.whitley.tutorial.objects.TuteTutor;
import org.whitley.tutorial.processor.CreateSchedule;
import org.whitley.validator.ValidatorObject;

/**
 * <h1>LoginHandler</h1>
 * Handles Tutorial allocation using data in database.
 * Also handles timestream management.
 * @author Ha Jin Song
 * @version 1.2
 * @see org.whitley.object.handler.BasicHandler
 * @since 11/1/2015
 */
public class TutorialHandler extends BasicHandler {
    int minStudent, maxStudent, semester;
    int[] level;
    
    ArrayList<TuteSubject> tutes;
    
    @Override
    public void init(){
        tutes = new ArrayList<>();
    }
    /**
     * getAllStream method.
     * Retrieve all timestreams in the database.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    private void getAllStream(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<Timestream> results = tutorialController.getAllStream();
        //Sort
        //NEEDS FIXING
        Collections.sort(results, (Timestream t1, Timestream t2) -> {
            int dayCmp = t1.getTimestreamPK().getStreamDay().compareTo(t2.getTimestreamPK().getStreamDay());
            if (dayCmp != 0){
                return dayCmp;
            }
            int startCmp = t1.getTimestreamPK().getStreamTimeStart().compareTo(t2.getTimestreamPK().getStreamTimeStart());
            if (startCmp != 0){
                return startCmp;
            }
            return t1.getTimestreamPK().getStreamTimeEnd().compareTo(t2.getTimestreamPK().getStreamTimeEnd());
        });     
        request.setAttribute("result", results);
        request.getRequestDispatcher("/tutorial/view.jsp").forward(request, response);
    }
    
    /**
     * addNewStream method.
     * Adds new timestream record to database.
     * Ensure no duplicates exist
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    private void addNewStream(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        //Extract information from request parameter, prepare for validation
        String day = request.getParameter("new_day");
        inputList.add(new ValidatorObject(day,"day","Day"));
        String start = request.getParameter("new_start");
        inputList.add(new ValidatorObject(start,"time","Start Time"));
        String end = request.getParameter("new_end");
        inputList.add(new ValidatorObject(end,"time","End Time"));
        errors = validate(inputList);
        //Proceed only if validation passed
        if(errors.isEmpty()){
            try {
                DateFormat df = new SimpleDateFormat("HH:mm");
                Date startDate, endDate;
                startDate = df.parse(start);
                endDate = df.parse(end);
                //Validate start and end Date.
                if(startDate.after(endDate)){
                    errors.add("Start time cannot be later than End time");
                }else{
                    //Check for duplication
                    Timestream ts = new Timestream(day,start,end);
                    if(!tutorialController.addStream(ts)){
                    errors.add("Stream already Exist");
                }

                }            
            } catch (ParseException ex) {
                errors.add("UNKNOWN ERROR");
            }
        }
        request.setAttribute("errors", errors);
        getAllStream(request,response);
        
    }
    
    /**
     * getStreamDetail method.
     * Retrieve details of a specific timestream.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    private void getStreamDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String day = request.getParameter("stream_day");
            String start = request.getParameter("stream_start");
            String end = request.getParameter("stream_end");
            Timestream stream = tutorialController.findStream(day,start,end); 
            request.setAttribute("stream", stream);
            request.setAttribute("test",Day.valueOf(day));
            request.getRequestDispatcher("/tutorial/view.jsp").forward(request, response);
    }
    
    /**
     * removeStream method.
     * Remove record of a specific timestream from the dtabase.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    private void removeStream(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String day = request.getParameter("stream_day");
            String start = request.getParameter("stream_start");
            String end = request.getParameter("stream_end");
            tutorialController.removeStream(day,start,end);
            getAllStream(request,response);
    }

    /**
     * getSubjectsByConstraints method.
     * First step of tutorial allocation. Takes user input of
     * minimum and maximum students, tutorial semester and tutorial subject level.
     * Then only retrieve subjects which matches the user given criteria.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    private void getSubjectsByConstraint(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        //Extract constraints, prepare for validation
        String raw_min = request.getParameter("minStudents");
        inputList.add(new ValidatorObject(raw_min,"int","Minimum Student"));
        String raw_max = request.getParameter("maxStudents");
        inputList.add(new ValidatorObject(raw_max,"int","Maximum Student"));
        String raw_semester = request.getParameter("semester");
        inputList.add(new ValidatorObject(raw_semester,"int","Semester"));
        String raw_level[] = request.getParameterValues("level");
        if(raw_level == null){
            errors.add("Please specify level");
            request.setAttribute("errors",errors);
            request.getRequestDispatcher("/tutorial/set_constraints.jsp").forward(request,response);
        }
        for(String s : raw_level){
            inputList.add(new ValidatorObject(s,"int","level"));
        }
        errors = validate(inputList);
        //Proceed only if validation passed
        if(errors.isEmpty()){
            this.minStudent = Integer.parseInt(raw_min);
            this.maxStudent = Integer.parseInt(raw_max);
            this.semester = Integer.parseInt(raw_semester);
            this.level = new int[raw_level.length];
            for(int i = 0 ; i < raw_level.length ; i ++ ) {
                level[i] = raw_level[i].charAt(0);
            }
            this.tutes = new ArrayList<>();
            this.tutes = getTutorials();
            errors.add("Successfully updated constraints");
            errors.add("Please proceed to Edit Tutorial Details from Tutorial Manager");
            request.setAttribute("errors",errors);
            request.getRequestDispatcher("/tutorial/set_constraints.jsp").forward(request, response);
        }else{
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/tutorial/set_constraints.jsp").forward(request,response);
        }
    }
    /**
     * getEditTutorial method.
     * Prepare edit_tutorial page.
     * Purpose of the page is to edit Tutors.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.1
     */
    private void getEditTutorial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            errors = new ArrayList<>();
            ArrayList<TuteTutor> staffs = getTutorList();
            request.setAttribute("staffs",staffs);
            request.setAttribute("tutes", this.tutes);
            request.setAttribute("test", this.maxStudent);
            request.getRequestDispatcher("/tutorial/edit_tutorials.jsp").forward(request, response);
    }

    
    
    
    
    
    private void manualAddTutorial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        String code = request.getParameter("code");
        inputList.add(new ValidatorObject(code,"char","Subject Code"));
        errors = validate(inputList);
        //Extract constraints, prepare for validation
        if(errors.isEmpty()){
            
            try{
                Subject subject = subjectController.findSubjectByCode(code);

                //Get subjects, subject-student relationship and staffs.
                List<Studentsubject> ss = ssController.findBySubjectSemester(code, semester);

                //Prepare conversion to Java object from Database POJO
                Student student;
                TuteSubject temp_tute;
                //Retrieve students doing the subject during given semester
                //Check student size constraint

                //Passed, add to the list
                temp_tute = new TuteSubject(subject.getSubjectCode(),subject.getSubjectName());
                temp_tute.setSemester(semester);
                for(Studentsubject s : ss){
                    student = studentController.findStudent(s.getStudentsubjectPK().getStudentId());
                    temp_tute.addStudent(new TuteStudent(student.getFName(),
                        student.getLName(),
                        student.getMName(),
                        student.getStudentId()));
                        
                }
                if(tutes.contains(temp_tute)){
                    errors.add("Tutorial Already Exists");
                }else{
                    this.tutes.add(temp_tute);
                }
            
            }catch (Exception e){
                errors.add("Subject of given code does not exist in database");
                errors.add(e.toString());
                errors.add(String.valueOf(this.semester));
                errors.add(code);
            }
       }
       request.setAttribute("errors", errors);
       getEditTutorial(request,response);

    }
    
    
    /**
     * 
     * saveChanges method.
     * save Changes made to tutorials
     * Changes refer to change in tutors
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.1
     */
    private void saveChanges(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            ArrayList<TuteTutor> staffs = getTutorList();
            ArrayList<TuteSubject> toDelete = new ArrayList<>();
            ArrayList<TuteSubject> toCollapse = new ArrayList<>();
            errors = new ArrayList<>();
            String get_tutor, get_del, get_collapse, get_name, new_tute_code ="";
            for(TuteSubject subject : tutes){
                get_tutor = subject.getCode()+"_tutor";
                get_del = subject.getCode()+"_delete";
                get_collapse = subject.getCode()+"_collapse";
                get_name = subject.getCode()+"_name";
                String tutorName = request.getParameter(get_tutor);
                String tuteName = request.getParameter(get_name);
                String isDel = request.getParameter(get_del);
                String isCol = request.getParameter(get_collapse);
                if(isDel!=null){
                    toDelete.add(subject);
                }else if(isCol!=null){
                    toCollapse.add(subject);
                    new_tute_code +=subject.getCode()+" ";
                }else{
                    subject.setName(tuteName);
                    for(TuteTutor staff  : staffs){
                        if(staff.getName().equals(tutorName)){
                            subject.setTutor(staff);
                        }
                    }
                }
            }
           
            for(TuteSubject subject : toDelete){
                this.tutes.remove(subject);
            }
            TuteSubject colTutes= new TuteSubject( new_tute_code.trim().replace(" ", "/"),"New Collapsed Tutorial");
            if(toCollapse.size() > 0 ){
                for(TuteSubject subject: toCollapse){
                    this.tutes.remove(subject);
                    for(TuteStudent student : subject.getStudents()){
                        colTutes.addStudent(student);
                    }
                }
                this.tutes.add(colTutes);
            }
            errors.add("Successfully updated tutorials");
            errors.add("Unless further editing is required, please proceed to Timetable Generation from Tutorial Manager");
            request.setAttribute("errors",errors);
            getEditTutorial(request,response);
    }
    
    /**
     * preallocateTutorial method.
     * preallocate tutorials.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    private void preallocateTutorial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //Get Timetable information (stream and rooms)
            List<Timestream> tss = tutorialController.getAllStream();
            List<Room> rooms_db = roomController.findAllRoom();
            //Prepare for conversion
            ArrayList<TuteRoom> rooms = new ArrayList<>();


            //Room conversion
            for(Room room : rooms_db){
                    rooms.add(new TuteRoom(room.getRoomName(),room.getMinCapacity(),room.getMaxCapacity()));
            }
            //Convert Timestreams
            ArrayList<TuteStream> tuteStreams = new ArrayList<>();
            for(Timestream ts : tss){
                try {
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    Date startTime,endTime;
                    Day day = ts.getTimestreamPK().getStreamDay();
                    startTime = df.parse(ts.getTimestreamPK().getStreamTimeStart());
                    endTime = df.parse(ts.getTimestreamPK().getStreamTimeEnd());

            
                    TuteStream temp_tStream = new TuteStream(day,startTime,endTime);
                    tuteStreams.add(temp_tStream);
                } catch (ParseException ex) {
                    Logger.getLogger(TutorialHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            Collections.sort(tuteStreams, (TuteStream t1, TuteStream t2) -> {
                int dayCmp = t1.getStream_day().compareTo(t2.getStream_day());
                if (dayCmp != 0){
                    return dayCmp;
                }
                int startCmp = t1.getStream_start().compareTo(t2.getStream_start());
                if (startCmp != 0){
                    return startCmp;
                }
                return t1.getStream_end().compareTo(t2.getStream_end());
            });
            request.setAttribute("tutes", tutes);
            request.setAttribute("streams", tuteStreams);
            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("/tutorial/pre_allocate.jsp").forward(request, response);

         
     }
    
     
     
    /**
     * generateTutorial method.
     * Third step of tutorial allocation, generation of tutorial timetable.
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    private void generateTutorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            List<Timestream> tss = tutorialController.getAllStream();
            List<Room> rooms_db = roomController.findAllRoom();
            ArrayList<TuteRoom> rooms = new ArrayList<>();
            ArrayList<TuteTable> filled = new ArrayList<>(); 
            //Get Room
            for(Room room : rooms_db){
                    rooms.add(new TuteRoom(room.getRoomName(),room.getMinCapacity(),room.getMaxCapacity()));
            }           
            //Get Stream
            ArrayList<TuteStream> streams = new ArrayList<>();
            for(Timestream ts : tss){
                try {
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    Date startTime,endTime;
                    Day day = ts.getTimestreamPK().getStreamDay();
                    startTime = df.parse(ts.getTimestreamPK().getStreamTimeStart());
                    endTime = df.parse(ts.getTimestreamPK().getStreamTimeEnd());

            
                    TuteStream temp_tStream = new TuteStream(day,startTime,endTime);
                    streams.add(temp_tStream);
                } catch (ParseException ex) {
                    Logger.getLogger(TutorialHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            Collections.sort(streams, (TuteStream t1, TuteStream t2) -> {
                int dayCmp = t1.getStream_day().compareTo(t2.getStream_day());
                if (dayCmp != 0){
                    return dayCmp;
                }
                int startCmp = t1.getStream_start().compareTo(t2.getStream_start());
                if (startCmp != 0){
                    return startCmp;
                }
                return t1.getStream_end().compareTo(t2.getStream_end());
            });  
            
            //Prepare for pre-allocation specified by the user
            for(TuteStream stream : streams){
                for(TuteRoom room : rooms){
                    String filled_subject = request.getParameter(stream.toString()+"_"+room.toString()+"_tute");
                    for(TuteSubject tute : tutes){
                        if(tute.getCode().equals(filled_subject)){
                            filled.add(new TuteTable(tute,stream,room));
                        }
                    }
                            
                }
            }
            //Allocate
            CreateSchedule schedule = new CreateSchedule(tutes,streams,rooms,filled);
            try{
            ArrayList<TuteTable> timetable = schedule.createTable();
            if(timetable == null){
                request.setAttribute("tutes", tutes);
                request.setAttribute("streams", streams);
                request.setAttribute("rooms", rooms);
                request.getRequestDispatcher("/tutorial/tutorial_result.jsp").forward(request, response);
            }
            request.setAttribute("tutes", tutes);
            request.setAttribute("streams", streams);
            request.setAttribute("rooms", rooms);
            request.setAttribute("timetable",timetable);
            request.setAttribute("filled",filled);

            //Download
            response.setContentType("application/vnd.ms-excel");
            writeAsExcel(timetable,response.getOutputStream());
            response.getOutputStream().close();
            }catch (Exception e) {
                request.setAttribute("tutes", tutes);
                request.setAttribute("streams", streams);
                request.setAttribute("rooms", rooms);
                request.getRequestDispatcher("/tutorial/tutorial_result.jsp").forward(request, response);
            }
            //request.getRequestDispatcher("/tutorial/tutorial_result.jsp").forward(request, response);
        
    }
    

     
     
     /**
      * getTutorials method.
      * Retrieves Tutorials that meets given criteria.
      * @return tutorials, ArrayList of TuteSubject
      * @since 1.1
      */
     private ArrayList<TuteSubject> getTutorials(){
            ArrayList<TuteSubject> tutorials = new ArrayList<>();
            List<Subject> subjects = new ArrayList<>();
            for(int i = 0 ; i < level.length ; i ++ ){
                subjects.addAll(subjectController.filterSubject(level[i]));
            }
            //Get subjects, subject-student relationship and staffs.
            List<Studentsubject> ss;
            
            //Prepare conversion to Java object from Database POJO
            Student student;
            TuteStudent temp_stud;
            TuteSubject temp_tute;

            for(Subject subject : subjects){
                //Retrieve students doing the subject during given semester
               ss = ssController.findBySubjectSemester(subject.getSubjectCode(),semester);
               //Check student size constraint
               if(ss.size()>=minStudent && ss.size()<=maxStudent){
                   //Passed, add to the list
                   temp_tute = new TuteSubject(subject.getSubjectCode(),subject.getSubjectName());
                   temp_tute.setSemester(semester);
                   for(Studentsubject s : ss){
                       student = studentController.findStudent(s.getStudentsubjectPK().getStudentId());
                       temp_stud = new TuteStudent(student.getFName(),student.getLName(),student.getMName(),student.getStudentId());
                       temp_tute.addStudent(temp_stud);
                   }
                  tutorials.add(temp_tute);
               }
            }
            return tutorials;
     }
    
     /**
      * getTutorList method.
      * Refactored in order to fix bug caused when tutors were deleted but
      * their record still remained in the tutorial information.
      * @return staffs, ArrayList of TuteTutor
      * @since 1.2
      */
     private ArrayList<TuteTutor> getTutorList(){
            ArrayList<TuteTutor> staffs = new ArrayList<>();
            List<Staff> staffs_db = staffController.findAllStaff();
            //Staff conversion
            for(Staff staff : staffs_db){
                List<Staffpreference> staffPref = staffController.findStaffPreference(staff);
                TuteTutor temp_tutor = new TuteTutor(staff.getName());
                for(Staffpreference pref : staffPref){
                    temp_tutor.addToPref(new TuteStream(pref.getStreamDay(),pref.getSSTDate(),pref.getSETDate()));
                }
                staffs.add(temp_tutor);
            }
            return staffs;
     }
     /**
      * writeAsExcel method.
      * Writes Tutorial timetable into excel file for downloading.
      * @param timetable: Filled out TImetable
      * @param ops: Output stream for download
      * @throws IOException if output stream fails
      * @since 1.0
      */
    private void writeAsExcel(ArrayList<TuteTable> timetable, OutputStream ops) 
            throws IOException{
        ops.flush();
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        makeScheduleList(workbook,timetable,"Tutorials");
        makeTutorialDetailList(workbook,timetable,"Tutorial Groups");

        
        try {
            workbook.write(ops);
            ops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * makeScheduleList method.
     * Write excel spreadsheet containing information on tutorial schedule
     * @param workbook: Target excel workbook
     * @param timetable: Filled out Timetable
     * @param sheetName: Name of the Sheet
     * @since 1.0
     */
    private void makeScheduleList(HSSFWorkbook workbook,ArrayList<TuteTable> timetable, String sheetName){
        int row_counter = 0;
        HSSFSheet stream_Sheet = workbook.createSheet(sheetName);                  
        HSSFRow stream_row;
        HSSFCell stream_cell;
        ArrayList<String> data;
        HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        for(TuteTable stream: timetable){
            data = new ArrayList<>();
            String stream_day = stream.getStream().getStream_day().toString();
            data.add(stream_day);
            String stream_start = stream.getStream().getStream_start();
            data.add(stream_start);
            String stream_end = stream.getStream().getStream_end();
            data.add(stream_end);
            String stream_code = stream.getSubject().getCode();
            data.add(stream_code);
            String stream_name = stream.getSubject().getName();
            data.add(stream_name);
            String stream_room = stream.getRoom().getRoomName();
            data.add(stream_room);
            
            stream_row = stream_Sheet.createRow(row_counter);
            row_counter++;
            for(int i = 0 ; i < data.size()-1 ; i ++){
                stream_cell = stream_row.createCell(i);
                stream_cell.setCellValue(data.get(i));
            }
        }
        for(int i = 0 ; i < 6 ; i ++){
                stream_Sheet.autoSizeColumn(i);
        }

    }
    
    /**
    /**
     * makeTutorialDetailList method.
     * Write excel spreadsheet containing information on tutorial details.
     * @param workbook: Target excel workbook
     * @param timetable: Filled out Timetable
     * @param sheetName: Name of the Sheet
     * @since 1.0
     */
    private void makeTutorialDetailList(HSSFWorkbook workbook,ArrayList<TuteTable> timetable, String sheetName){
        int row_counter = 0;
        HSSFSheet stream_Sheet = workbook.createSheet(sheetName);                  
        HSSFRow stream_row;
        HSSFCell stream_cell;
        ArrayList<String> data;
        HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        for(TuteTable stream: timetable){
            stream_row = stream_Sheet.createRow(row_counter);
            stream_cell = stream_row.createCell(0);
            stream_cell.setCellValue("Subject Code");
            stream_cell = stream_row.createCell(1);
            stream_cell.setCellValue(stream.getSubject().getCode());
            row_counter++;
            stream_row = stream_Sheet.createRow(row_counter);
            stream_cell = stream_row.createCell(0);
            stream_cell.setCellValue("Subject Name");
            stream_cell = stream_row.createCell(1);
            stream_cell.setCellValue(stream.getSubject().getName());
            row_counter++;
            stream_row = stream_Sheet.createRow(row_counter);
            stream_cell = stream_row.createCell(0);
            stream_cell.setCellValue("Subject Tutor");
            stream_cell = stream_row.createCell(1);
            stream_cell.setCellValue(stream.getSubject().getTutor().getName());
            row_counter++;
            stream_row = stream_Sheet.createRow(row_counter);
            stream_cell = stream_row.createCell(0);
            stream_cell.setCellValue("Stream Time");
            stream_cell = stream_row.createCell(1);
            stream_cell.setCellValue(stream.getStream().toFormattedString());
            row_counter++;
            stream_row = stream_Sheet.createRow(row_counter);
            stream_cell = stream_row.createCell(0);
            stream_cell.setCellValue("Tutorial Room");
            stream_cell = stream_row.createCell(1);
            stream_cell.setCellValue(stream.getRoom().getRoomName());
            row_counter++;
            for(TuteStudent student : stream.getSubject().getStudents()){
                stream_row = stream_Sheet.createRow(row_counter);
                stream_cell = stream_row.createCell(1);
                stream_cell.setCellValue(student.toString());
                row_counter++;
            }
            row_counter++;
            
        }
        stream_Sheet.autoSizeColumn(0);
        stream_Sheet.autoSizeColumn(1);
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
        getAllStream(request,response);
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
            getStreamDetail(request,response);
        }else if(action.compareTo("Remove Timestream")==0){
            removeStream(request,response);
        }else if(action.compareTo("Add Stream")==0){
            addNewStream(request,response);
        }else if(action.compareTo("Get Subjects")==0){
            getSubjectsByConstraint(request,response);
        }else if(action.compareTo("Preset Timetable")==0){
            preallocateTutorial(request,response);
        }else if(action.compareTo("Generate Timetable")==0){
            generateTutorial(request,response);
        }else if(action.compareTo("Edit Tutorials")==0){
            getEditTutorial(request,response);
        }else if(action.compareTo("Save Changes")==0){
            saveChanges(request,response);
        }else if(action.compareTo("Add New Tutorial")==0){
            manualAddTutorial(request,response);
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
