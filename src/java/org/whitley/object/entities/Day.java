/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.object.entities;

/**
 * <h1>Day</h1>
 * Enumerated type, Day, representing day of a week.
 * All days must start with capitalise letter in their String form.
 * @author Ha Jin Song
 * @version 1.0
 * @see org.whitley.tutorial.objects.TuteStudent
 * @see org.whitley.tutorial.objects.TuteSubject
 * @see org.whitley.tutorial.objects.Constraint
 * @since 11/1/2015
 */
public enum Day{
   
    Monday("Monday"),
    Tuesday("Tuesday"),
    Wednesday("Wednesday"),
    Thursday("Thursday"),
    Friday("Friday"),
    Saturday("Saturday"),
    Sunday("Sunday");

    private String day;
     
    Day(String day){
        this.day = day;
    }
    
    public String getDay(){
        return day;
    }
}
