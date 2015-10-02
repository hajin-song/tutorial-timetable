/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.tutorial.objects;

import java.util.ArrayList;

/**
 * <h1>TuteTutor</h1>
 * Object representing Tutor of a tutorial
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.tutorial.objects.TuteStream
 * @Since 2/3/2015
 */
public class TuteTutor {
    private String name;
    private ArrayList<TuteStream> pref;
    
    public TuteTutor(String name){
        this.name = name;
        this.pref = new ArrayList<>();
    }

    public void addToPref(TuteStream preference){
        pref.add(preference);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TuteStream> getPref() {
        return pref;
    }

    public void setPref(ArrayList<TuteStream> pref) {
        this.pref = pref;
    }
    
    
    
}
