package com.example.poong.primarystudentmonitoringassistantsystem.Class;

public class Classroom {

    private int classID;
    private String className;

    public Classroom(){}

    public Classroom(int classID, String className){
        this.classID = classID;
        this.className = className;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
