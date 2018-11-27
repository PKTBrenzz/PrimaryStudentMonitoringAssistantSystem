package com.example.poong.primarystudentmonitoringassistantsystem;

public class Constants {

    private static final String ROOT_URL = "http://192.168.0.150/school_300/v1/";

    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";
    public static final String URL_STUDENT_LIST = ROOT_URL + "displayStudents.php";
    public static final String URL_STUDENT_DETAIL = ROOT_URL + "getStudentResult.php";
    public static final String URL_ATTENDANCE_LIST = ROOT_URL + "getAttendanceList.php";
    public static final String URL_TEACHER_LIST = ROOT_URL + "getTeacherList.php";
    public static final String URL_CLASSROOM_LIST = ROOT_URL + "getClassroomList.php";
}
