/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>LoginHandler</h1>
 * Handles login of the user.
 * Checks given credential with the admin credential in the database.
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.object.handler.BasicHandler
 * @since 11/1/2015
 */
@WebServlet(name = "LoginHandler", urlPatterns = {"/LoginHandler"})
public class LoginHandler extends BasicHandler {

    /**
     * Process login request of the user
     * @param request servelt request
     * @param response servlet response
     * @throws ServletException if request dispatch fails
     * @throws IOException if data forwarding fails
     * @since 1.0
     */
    protected void authenticate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("uid");
        String pwd = request.getParameter("pwd");
        if(loginController.authenticate(id, pwd)) {
            request.getSession().setAttribute("id", id);
            request.getSession().setAttribute("pwd", pwd);
            request.getRequestDispatcher("/main.jsp").forward(request, response);
            
        }else{
            response.sendRedirect("index.jsp");
        }
    }

        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            authenticate(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            authenticate(request, response);
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
