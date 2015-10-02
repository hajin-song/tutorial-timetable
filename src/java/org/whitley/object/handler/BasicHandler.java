/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.whitley.object.controller.local.LoginControllerLocal;
import org.whitley.object.controller.local.RoomControllerLocal;
import org.whitley.object.controller.local.StaffControllerLocal;
import org.whitley.object.controller.local.StudentControllerLocal;
import org.whitley.object.controller.local.StudentSubjectControllerLocal;
import org.whitley.object.controller.local.SubjectControllerLocal;
import org.whitley.object.controller.local.TutorialControllerLocal;
import org.whitley.validator.InputValidator;
import org.whitley.validator.ValidatorObject;

/**
 * <h1>BasicHandler</h1>
 * Abstract object, template for all other Handlers.
 * Contains all EJB, Admin Authentication and Input Validator
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.object.controller.local.LoginControllerLocal
 * @see org.whitley.object.controller.local.TutorialControllerLocal
 * @see org.whitley.object.controller.local.RoomControllerLocal
 * @see org.whitley.object.controller.local.StudentSubjectControllerLocal
 * @see org.whitley.object.controller.local.StudentControllerLocal
 * @see org.whitley.object.controller.local.StaffControllerLocal
 * @see org.whitley.object.controller.local.StudentControllerLocal
 * @see org.whitley.object.controller.local.SubjectControllerLocal
 * @see org.whitley.validator.InputValidator
 * @since 10/1/2015
 */
@WebServlet(name = "BasicHandler", urlPatterns = {"/BasicHandler"})
public abstract class BasicHandler extends HttpServlet {
    @EJB LoginControllerLocal loginController;
    @EJB TutorialControllerLocal tutorialController;
    @EJB RoomControllerLocal roomController;
    @EJB StudentSubjectControllerLocal ssController;
    @EJB StudentControllerLocal studentController;
    @EJB StaffControllerLocal staffController;
    @EJB StudentControllerLocal studController;
    @EJB SubjectControllerLocal subjectController;
    
    protected InputValidator validator = new InputValidator();
    protected ArrayList<String> errors;
    protected final int CHAR_LIMIT = 30;
    protected final String INVALID_NAME = "ERROR: The name value must be less than 30 characters. Field: ";
    protected final String INVALID_DAY = "ERROR: Please use correct day of week. Field: ";
    protected final String INVALID_TIME = "ERROR: Please use correct time format, HH:mm. Field: ";
    protected final String INVALID_NUMBER = "ERROR: Please use numerical values. Field: ";
    protected final String INVALID_INPUT_NOT_GIVEN = "ERROR: Please give input for following Field: ";
    protected final String INVALID_INPUT = "ERROR: Unknown input type detected.";

    
    /**
     * authenticate method.
     * Authenticates user credentials.
     * Called on every action which requires acces to Database via Controllers (EJB)
     * @param request: HTTP request
     * @since 1.0
     */
    protected boolean authenticate(HttpServletRequest request){

        if(request.getSession().getAttribute("id")==null || request.getSession().getAttribute("pwd")==null){
            return false;
        }
        return loginController.authenticate(request.getSession().getAttribute("id").toString()
                , request.getSession().getAttribute("pwd").toString());
    }
    
    /**
     * validate method, single input.
     * Validates given input, the input are packaged in object of type {@link org.whitley.validator.ValidatorObject}
     * @param input: Type of {@code ValidatorObject}, input being validated
     * @return error message if input is invalid, else null.
     * @since 1.0
     */
    protected String validate(ValidatorObject input){
        String error = null;
        String value = input.getValue();
        String type = input.getType();
        if(value==null||value.equals("")){
            error = INVALID_INPUT_NOT_GIVEN + input.getFieldName();
            return error;
        }
        //Validate input depending on specified type
        switch (type){
            case "char":
                if(!validator.validateCharacter(input.getValue(), CHAR_LIMIT)){
                    error = INVALID_NAME + input.getFieldName()+ ", " + input.getValue();
                }
                break;
            case "day":
                if(!validator.validateDay(input.getValue())){
                    error = INVALID_DAY + input.getFieldName()+ ", " + input.getValue();
                }
                break;    
            case "int":
                if(!validator.validateInteger(input.getValue())){
                    error = INVALID_NUMBER + input.getFieldName()+ ", " + input.getValue();
                }
                break;                
            case "time":
                if(!validator.validateDate(input.getValue())){
                    error = INVALID_TIME + input.getFieldName()+ ", " + input.getValue();
                }
                break;
            default:
                error = INVALID_INPUT;
        }
        return error;
    }
     
    /**
     * validate method, multiple input.
     * Validates given input, the inputs are packaged in object of type {@link org.whitley.validator.ValidatorObject}
     * @param inputList: ArrayList of Type of {@code ValidatorObject}, input being validated
     * @return error ArrayList of error messages.
     * @since 1.0
     */
    protected ArrayList<String> validate(ArrayList<ValidatorObject> inputList){
        errors = new ArrayList<>();
        for(ValidatorObject input : inputList){
            String value = input.getValue();
            String type = input.getType();
            if(value==null||value.equals("")){
                errors.add(INVALID_INPUT_NOT_GIVEN + input.getFieldName());
                continue;
            }
            switch (type){
                case "char":
                    if(!validator.validateCharacter(input.getValue(), CHAR_LIMIT)){
                        errors.add(INVALID_NAME + input.getFieldName()+ ", " + input.getValue());
                    }
                    break;
                case "day":
                    if(!validator.validateDay(input.getValue())){
                        errors.add(INVALID_DAY + input.getFieldName()+ ", " + input.getValue());
                    }
                    break;    
                case "int":
                    if(!validator.validateInteger(input.getValue())){
                        errors.add(INVALID_NUMBER + input.getFieldName()+ ", " + input.getValue());
                    }
                    break;                
                case "time":
                    if(!validator.validateDate(input.getValue())){
                        errors.add(INVALID_TIME + input.getFieldName()+ ", " + input.getValue());
                    }
                    break;
                default:
                    errors.add(INVALID_INPUT);
            }
        }
        return errors;
    }
    
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


}
