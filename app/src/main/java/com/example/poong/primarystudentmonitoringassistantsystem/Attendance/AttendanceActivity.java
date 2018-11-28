package com.example.poong.primarystudentmonitoringassistantsystem.Attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.Class.ClassRoomAdapter;
import com.example.poong.primarystudentmonitoringassistantsystem.Class.Classroom;
import com.example.poong.primarystudentmonitoringassistantsystem.Constants;
import com.example.poong.primarystudentmonitoringassistantsystem.Main2Activity;
import com.example.poong.primarystudentmonitoringassistantsystem.MyStudentRecyclerViewAdapter;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;
import com.example.poong.primarystudentmonitoringassistantsystem.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {

    StringBuffer sb = null;

    private Button mSumbitButton,mEditButton;
    private CheckBox mCheckBox;
    private ProgressDialog progressDialog;

    private ArrayList<Classroom> mClassList;
    private ClassRoomAdapter classRoomAdapter;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter attendanceAdapter;
    private RecyclerView.LayoutManager attendanceLayoutManager;
    private MyStudentRecyclerViewAdapter myStudentRecyclerViewAdapter;

    private Date date;
    private String submission = "";
    private boolean edit_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(date);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        Spinner spinnerClasses = findViewById(R.id.spinner_classes);


//        submission = "submitted";

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        Toast.makeText(
//                getApplicationContext(),
//                currentDate,
//                Toast.LENGTH_LONG
//        ).show();

        recyclerView = findViewById(R.id.attendanceRecycleView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        attendanceLayoutManager = new LinearLayoutManager(AttendanceActivity.this);
        recyclerView.setLayoutManager(attendanceLayoutManager);
        attendanceAdapter = new AttendanceRecyclerViewAdapter(getDataSetMatches(), AttendanceActivity.this);
        recyclerView.setAdapter(attendanceAdapter);

        getStudentAttendance(currentDate);

//        Toast.makeText(AttendanceActivity.this, submission, Toast.LENGTH_SHORT).show();

//        Toast.makeText(
//                getApplicationContext(),
//                submission,
//                Toast.LENGTH_LONG
//        ).show();

//        mSumbitButton = findViewById(R.id.attendanceSubmitButton);
//        mEditButton = findViewById(R.id.attendanceEditButton);
//
//        if(submission.equals("not submitted")){
//            mSumbitButton.setVisibility(View.VISIBLE);
//            mEditButton.setVisibility(View.GONE);
//            mSumbitButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    editStudentAttendance();
//                }
//            });
//        }
//        else{
//            edit_done = true;
//
//            mSumbitButton.setVisibility(View.GONE);
//            mEditButton.setVisibility(View.VISIBLE);
//            mEditButton.setText("Edit");
//            mEditButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(edit_done == true){
//                        mEditButton.setText("Done");
//                        edit_done = false;
//                    }else{
//                        mEditButton.setText("Edit");
//                        edit_done = true;
//                    }
//                }
//            });
//        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    private void setAttendanceButton(String submission) {
        mSumbitButton = findViewById(R.id.attendanceSubmitButton);
        mEditButton = findViewById(R.id.attendanceEditButton);

        mSumbitButton.setVisibility(View.GONE);
        mEditButton.setVisibility(View.GONE);

        if(submission.equals("not submitted")){
            mSumbitButton.setVisibility(View.VISIBLE);

            mSumbitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    JSONObject JSONestimate = new JSONObject();
//                    final JSONArray myarray = new JSONArray();
//                    attendanceAdapter.notifyDataSetChanged();
//                    for (int i = 0; i < attendancesList.size(); i++) {
//
//                        try {
//                            JSONestimate.put("data:" + String.valueOf(i + 1), attendancesList.get(i).getJSONObject());
//                            myarray.put(attendancesList.get(i).getJSONObject());
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    Log.d("TTT",myarray.toString());
//                    Toast.makeText(AttendanceActivity.this, myarray.toString(),Toast.LENGTH_LONG).show();


//                    sb = new StringBuffer();
//                    attendanceAdapter.notifyDataSetChanged();
//                    int i = 0;
//                    do{
//                        Attendance hey = attendancesList.get(i);
//                        sb.append(hey.getAttendance_status());
//                        if(i != attendancesList.size() - 1){
//                            sb.append("\n");
//                        }
//                        i++;
//                    }while(i<attendancesList.size());
//
//                    Toast.makeText(AttendanceActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
                    editStudentAttendance();

                    mSumbitButton.setVisibility(View.GONE);
                    mEditButton.setVisibility(View.VISIBLE);

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
        }
        else{
            edit_done = true;

            attendanceAdapter.notifyDataSetChanged();
            for (int i = 0; i < attendancesList.size(); i++) {
                attendancesList.get(i).setEnabled(false);
            }
            attendanceAdapter.notifyDataSetChanged();

            mSumbitButton.setVisibility(View.GONE);
            mEditButton.setVisibility(View.VISIBLE);
            mEditButton.setText("Edit");
            mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edit_done == true){
                        mEditButton.setText("Done");
                        edit_done = false;

                        attendanceAdapter.notifyDataSetChanged();
                        for (int i = 0; i < attendancesList.size(); i++) {
                            attendancesList.get(i).setEnabled(true);
                        }
                        attendanceAdapter.notifyDataSetChanged();
//                        mCheckBox.setEnabled(true);
                    }else{
                        mEditButton.setText("Edit");

                        editStudentAttendance();

                        edit_done = true;

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void getClassList() {
        mClassList = new ArrayList<Classroom>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_CLASSROOM_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("classroom");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Classroom classroom = new Classroom(obj.getInt("classID"), obj.getString("className"));
                                    mClassList.add(classroom);
                                    classRoomAdapter.notifyDataSetChanged();
                                }
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
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
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUserID());
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getStudentAttendance(final String currentDate) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ATTENDANCE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("attendance");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Attendance attendance = new Attendance(obj.getInt("attendance_id"), obj.getString("attendance_status"), obj.getString("date"), obj.getString("student_id"), obj.getString("submission_status"));
                                    attendancesList.add(attendance);
                                    attendanceAdapter.notifyDataSetChanged();

                                    if(obj.getString("submission_status").equals("not submitted")){
                                        submission = "not submitted";
                                    }
                                    else if(obj.getString("submission_status").equals("submitted")){
                                        submission = "submitted";
                                    }
                                    else{
                                        submission = "edited";
                                    }

                                }
                                Log.d("HEY",submission);
                                setAttendanceButton(submission);
//                                Toast.makeText(
//                                        getApplicationContext(),
//                                        currentDate,
//                                        Toast.LENGTH_LONG
//                                ).show();
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
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
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "show");
                params.put("date", currentDate);
                params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUserID());
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void editStudentAttendance() {
        progressDialog.show();

        attendanceAdapter.notifyDataSetChanged();
        for (int i = 0; i < attendancesList.size(); i++) {
            attendancesList.get(i).setSubmission_status("submitted");
        }
        attendanceAdapter.notifyDataSetChanged();

        JSONObject JSONestimate = new JSONObject();
        final JSONArray myarray = new JSONArray();

        for (int i = 0; i < attendancesList.size(); i++) {
            try {
                JSONestimate.put(String.valueOf(i + 1), attendancesList.get(i).getJSONObject());
                myarray.put(attendancesList.get(i).getJSONObject());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d("TTT",myarray.toString());
        Toast.makeText(AttendanceActivity.this, myarray.toString(),Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ATTENDANCE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(!obj.getBoolean("error")){


                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "update");
                params.put("jsonarray", myarray.toString());
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private ArrayList<Attendance> attendancesList = new ArrayList<Attendance>();
    private List<Attendance> getDataSetMatches() {
        return attendancesList;
    }
}
