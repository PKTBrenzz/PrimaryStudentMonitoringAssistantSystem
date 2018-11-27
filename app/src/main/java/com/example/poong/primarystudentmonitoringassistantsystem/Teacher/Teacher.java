package com.example.poong.primarystudentmonitoringassistantsystem.Teacher;

public class Teacher {

    private String teacher_name;
    private String teacher_id;

    public Teacher(){}

    public Teacher(String teacher_id, String teacher_name){
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
}
