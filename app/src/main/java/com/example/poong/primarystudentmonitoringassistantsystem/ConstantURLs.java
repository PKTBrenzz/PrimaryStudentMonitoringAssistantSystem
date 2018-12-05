package com.example.poong.primarystudentmonitoringassistantsystem;

public class ConstantURLs {

    private static final String ROOT_URL = "http://192.168.0.150/school_database/v1/";
//    private static final String ROOT_URL = "http://10.207.201.116/school_database/v1/";
//    private static final String ROOT_URL = "http://192.168.43.243/school_database/v1/";

    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";
    public static final String URL_STUDENT_LIST = ROOT_URL + "displayStudents.php";
    public static final String URL_STUDENT_RADAR_RESULT = ROOT_URL + "getStudentRadarResult.php";
    public static final String URL_ATTENDANCE_LIST = ROOT_URL + "getAttendanceList.php";
    public static final String URL_TEACHER_LIST = ROOT_URL + "getTeacherList.php";
    public static final String URL_CLASSROOM_LIST = ROOT_URL + "getClassroomList.php";
    public static final String URL_ATTENDANCE_COUNT = ROOT_URL + "getAttendanceCount.php";
    public static final String URL_PREDICTION = ROOT_URL + "predictPerformance.php";
    public static final String URL_STUDENT_DETAILS = ROOT_URL + "getStudentDetails.php";
    public static final String URL_STUDENT_LINE_RESULT  = ROOT_URL + "getStudentLineResult.php";
    public static final String URL_ATTENDANCE_CALENDAR  = ROOT_URL + "getAttendanceToCalendar.php";
    public static final String URL_PROFILE_INFO  = ROOT_URL + "getProfileInfo.php";

}
