package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.ConstantURLs;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAttendanceCalender extends Fragment {

    private TextView textView, mTotal, mAbsent;
    private Date date;
    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    public PieChart pieChart;
    public ArrayList<PieEntry> PieEntries = new ArrayList<>();

    private int workingDays = 0;

    public StudentAttendanceCalender() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_attendance_calender, container, false);

        String studentID = getArguments().getString("studentID");

        date = new Date();

        textView = view.findViewById(R.id.textCalendar);
        textView.setText(dateFormatMonth.format(date));

        mTotal = view.findViewById(R.id.present);
        mAbsent = view.findViewById(R.id.absent);

        compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        //Set an event for Teachers' Professional Day 2016 which is 21st of October

        setAttendanceCalendar(studentID);

//        Event ev1 = new Event(Color.BLACK, 1543795200000L);
//        compactCalendar.addEvent(ev1);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getActivity().getApplicationContext();

//                if (dateClicked.toString().compareTo("Mon Dec 03 00:00:00 AST 2018") == 0) {
//                    Toast.makeText(context, "Teachers' Professional Day", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(context, "No Events Planned for that day", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                textView.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        pieChart = (PieChart) view.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(61f);

        getAttendancePie(studentID);

        return view;
    }

    private void getAttendancePie(final String studentID) {
        PieEntries.clear();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_ATTENDANCE_COUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("attendance");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    String attendanceStatus = obj.getString("attendanceStatus");
                                    Integer attendanceCount = obj.getInt("Count");

                                    if(attendanceStatus.equals("absent")){
                                        mAbsent.setText(attendanceCount.toString());
                                    }
                                    workingDays = workingDays + attendanceCount;

                                    PieEntries.add(new PieEntry(attendanceCount.floatValue(), attendanceStatus));
                                }
                            }else{
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mTotal.setText(String.valueOf(workingDays));

                        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                        PieDataSet dataSet = new PieDataSet(PieEntries, "Attendance Analysis");
                        dataSet.setSliceSpace(3f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                        PieData data = new PieData(dataSet);
                        data.setValueFormatter(new PercentFormatter());
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.BLACK);

                        pieChart.setData(data);
                        pieChart.invalidate();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("studentID", studentID);
//                params.put("student_id", "S00001");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void setAttendanceCalendar(final String studentID) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_ATTENDANCE_CALENDAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("calendar");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    String attendanceStatus = obj.getString("attendanceStatus");
                                    String attendanceDate = obj.getString("attendanceDate");

                                    try {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        date = simpleDateFormat.parse(attendanceDate);
                                        long milliseconds = date.getTime();

                                        if(attendanceStatus.equals("present")){
                                            Event present = new Event(Color.BLUE, milliseconds, "Present");
                                            compactCalendar.addEvent(present);
                                        }
                                        else{
                                            Event absent = new Event(Color.RED, milliseconds, "Absent");
                                            compactCalendar.addEvent(absent);
                                        }

                                    } catch (java.text.ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }else{
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("studentID", studentID);
//                params.put("student_id", "S00001");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
