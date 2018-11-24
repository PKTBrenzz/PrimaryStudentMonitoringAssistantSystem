package com.example.poong.primarystudentmonitoringassistantsystem;

public class Grade {
    private String gradeId;
    private String studentId;
    private String subject;
    private int mark;

    public Grade(){}

    public Grade(String gradeId, String studentId, String subject, int mark){
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.subject = subject;
        this.mark = mark;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
