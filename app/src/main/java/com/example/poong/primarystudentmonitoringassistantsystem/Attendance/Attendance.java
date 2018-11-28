package com.example.poong.primarystudentmonitoringassistantsystem.Attendance;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class Attendance {
    private int attendance_id;
    private String attendance_status;
    private String date;
    private String student_id;
    private String submission_status;
    private String studentName;

    private boolean isEnabled = true;

    public Attendance(){}

    public Attendance(int attendance_id, String attendance_status, String date, String student_id, String submission_status, String studentName){
        this.attendance_id = attendance_id;
        this.attendance_status = attendance_status;
        this.date = date;
        this.student_id = student_id;
        this.submission_status = submission_status;
        this.studentName = studentName;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("attendance_id", attendance_id);
            obj.put("attendance_status", attendance_status);
            obj.put("date",date);
            obj.put("student_id", student_id);
            obj.put("submission_status", submission_status);
            obj.put("name", studentName);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public int getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(int attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSubmission_status() {
        return submission_status;
    }

    public void setSubmission_status(String submission_status) {
        this.submission_status = submission_status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
