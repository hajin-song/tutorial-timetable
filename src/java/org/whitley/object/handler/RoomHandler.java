/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.whitley.object.entities.Room;
import org.whitley.validator.ValidatorObject;

/**
 *<h1>RoomHandler</h1>
 * Handles Room entity of the database.
 * Acts as bridge between View and Controller for the Room entity.
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.object.handler.BasicHandler
 * @since 11/1/2015
 */
public class RoomHandler extends BasicHandler {
    
    /**
     * addNewRoom method.
     * adds new room data to the database.
     * Information for new room given by user via HTTP request.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void addNewRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        
        //Extract data from param, prepare for data validation
        String raw_name = request.getParameter("name");
        inputList.add(new ValidatorObject(raw_name,"char","name"));
        String raw_minC = request.getParameter("minCapacity");
        inputList.add(new ValidatorObject(raw_minC,"int","Minimum Capacity"));
        String raw_maxC = request.getParameter("maxCapacity");
        inputList.add(new ValidatorObject(raw_maxC,"int","Maximum Capacity"));
        //Validate
        errors = validate(inputList);
        
        //Proceed if only validation passed
        if(errors.isEmpty()){
            int minC = Integer.parseInt(raw_minC);
            int maxC = Integer.parseInt(raw_maxC);
            //Check if minimum is less than maximum
            if(minC < maxC){
                Room room = new Room(raw_name,minC,maxC);
                //Check for duplicate
                if(!roomController.addRoom(room)){
                    errors.add("Room already Exists");
                }
            }else{
                errors.add("Minimum Capacity cannot be greater than Maximum Capacity");
            }
        }
        request.setAttribute("errors", errors);
        getAllRoom(request,response);
    }
    
    /**
     * getAllRoom method.
     * Get all rooms in the database for the viewing.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getAllRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Room> results = roomController.findAllRoom();
        request.setAttribute("result", results);
        request.getRequestDispatcher("/room/view.jsp").forward(request, response);
        
    }
    
    /**
     * getRoomDetail method.
     * Retrieve detail of the room. Detail retrieved may be changed as version
     * update.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void getRoomDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Room room = roomController.findRoom(Integer.parseInt(request.getParameter("roomid")));
        request.setAttribute("room", room);
        request.getRequestDispatcher("/room/detailed_view.jsp").forward(request, response);
    }
    
    
    /**
     * editRoomDetail method.
     * Edit detail of a specific room.
     * Changes in detail are given by user from HTTP Request parameter.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void editRoomDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ArrayList<ValidatorObject> inputList = new ArrayList<>();
        errors = new ArrayList<>();
        //Extract from HTTP parameter from request and prepare for validation
        String raw_name = request.getParameter("name");
        inputList.add(new ValidatorObject(raw_name,"char","name"));
        String raw_minC = request.getParameter("minCapacity");
        inputList.add(new ValidatorObject(raw_minC,"int","Minimum Capacity"));
        String raw_maxC = request.getParameter("maxCapacity");
        inputList.add(new ValidatorObject(raw_maxC,"int","Maximum Capacity"));
        //Validate
        errors = validate(inputList);
        //Room being updated
        Room room = roomController.findRoom(Integer.parseInt(request.getParameter("roomId")));
        //Proceed only if validation passed
        if(errors.isEmpty()){
            int minC = Integer.parseInt(raw_minC);
            int maxC = Integer.parseInt(raw_maxC);
            //Ensure minimum less than maximum
            if(minC < maxC){
                roomController.editRoomDetail(room, raw_name, minC,  maxC);
                request.setAttribute("room", room);
                request.getRequestDispatcher("/room/detailed_view.jsp").forward(request, response);
            }else{
                errors.add("Minimum Capacity cannot be greater than Maximum Capacity");
            }
        }
        request.setAttribute("errors", errors);
        request.setAttribute("room",room);
        request.getRequestDispatcher("/room/detailed_view.jsp").forward(request, response);  
    }
    
    /**
     * removeRoom method.
     * remove a room from database.
     * @param request: HTTP request
     * @param response: HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException if data extraction/forwarding fails
     * @since 1.0
     */
    private void removeRoom(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
            int roomID = Integer.parseInt(request.getParameter("roomid"));
            roomController.removeRoom(roomID);
            getAllRoom(request,response);       
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
        getAllRoom(request,response);
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
            getRoomDetail(request,response);
        }else if(action.compareTo("Edit Room")==0){
            editRoomDetail(request,response);
        }else if(action.compareTo("Add New Room")==0){
            addNewRoom(request,response);
        }else if(action.compareTo("Remove Room")==0){
            removeRoom(request,response);
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
