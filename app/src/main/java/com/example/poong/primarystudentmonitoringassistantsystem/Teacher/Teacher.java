package com.example.poong.primarystudentmonitoringassistantsystem.Teacher;

public class Teacher {

    private String teacher_name;
    private String teacher_id;
    private String teacher_email;

    public Teacher(){}

    public Teacher(String teacher_id, String teacher_name, String teacher_email){
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.teacher_email = teacher_email;
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

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }
}
