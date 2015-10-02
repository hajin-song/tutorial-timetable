/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.whitley.validator;

/**
 * <h1>ValidatorObject</h1>
 * ValidatorObject class used to represent and wrap user input for
 * {@link org.whitley.validator.InputValidator} Class
 * @author Ha Jin Song
 * @Version 1.0
 * @see org.whitley.validator.InputValidator
 * @Since 10-02-2015
 */

public class ValidatorObject {
    private final String value, type,fieldName;
    
    /**
     * Constructor
     * @param value value being checked
     * @param type type the value is beign checked for
     * @param fieldName HTML field name used for error message output
     */
    public ValidatorObject(String value, String type,String fieldName){
        this.value = value;
        this.type = type;
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }
    
    
    
}
