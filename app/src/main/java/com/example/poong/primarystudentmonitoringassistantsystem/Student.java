package com.example.poong.primarystudentmonitoringassistantsystem;

import java.util.Date;

/**
 * Student class
 *
 * This is a class that represent the student and their information.
 * A student should have name, and identification number for recognition by human and computer respectively
 * Not only that, student should have personal information such as birth date and parents
 *
 *
 */


public class Student {

    private String name;
    private String id;
    private Date birthdate;
    private String classroom;
    
    //Parent

    public Student(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

