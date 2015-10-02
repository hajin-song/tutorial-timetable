/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.whitley.object.entities.Day;


/**
 * <h1>InputValidator</h1>
 * InputValidator Class checks all input given by user.
 * <p>
 * Every method declared within this class have return type of <Strong>boolean</Strong>
 * There are methods for checking integer, string (30 Characters as limit), date and day.
 * </p>
 * <p>
 * Error messages are declared as String constant. 
 * </p>
 * @author Ha Jin Song
 * @Version 1.0
 * @see Date
 * @see org.whitley.object.entities.Day
 * @Since 10-02-2015
 */

public class InputValidator {
    
    /**
     * Check whether or not the given string is integer.
     * @param isInt the input being checked for integer
     * @return {@code true} if input is integer, else {@code false}
     */
    public boolean validateInteger(String isInt){
        try{
            Integer.parseInt(isInt);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * Check whether or not given String's length is less than limit.
     * @param target the String being checked
     * @param limit the maximum possible length of the string
     * @return {@code true} if target's lenght is less than limit, else {@code false}
     */
    public boolean validateCharacter(String target, int limit){
        if(target.length() > limit){
            return false;
        }   
        return true;
    }
    
    /**
     * Check whether or not given String is in format of Date.
     * @param target the String being checked for Date
     * @return {@code true} if target is type of {@code Date}, else {@code false}
     */
    public boolean validateDate(String target){
        DateFormat df = new SimpleDateFormat("HH:mm");
        try {
            df.parse(target);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check whether or not given String corresponds to day of week.
     * @param target the String being checked for Day of week, represented by Enum type {@link org.whitley.object.entities.Day}
     * @return {@code true} if target is a Day of week, else {@code false}
     */
    public boolean validateDay(String target){
        try{
            Day.valueOf(target);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
