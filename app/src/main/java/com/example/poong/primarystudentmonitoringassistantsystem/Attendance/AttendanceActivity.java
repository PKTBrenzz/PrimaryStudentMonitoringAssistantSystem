package com.example.poong.primarystudentmonitoringassistantsystem.Attendance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.Class.ClassRoomAdapter;
import com.example.poong.primarystudentmonitoringassistantsystem.Class.Classroom;
import com.example.poong.primarystudentmonitoringassistantsystem.ConstantURLs;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Button mSumbitButton,mEditButton;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    private ArrayList<Classroom> mClassList = new ArrayList<Classroom>();
    private ClassRoomAdapter classRoomAdapter;

    private ArrayList<Attendance> attendancesList = new ArrayList<Attendance>();
    private List<Attendance> getDataSetMatches() {
        return attendancesList;
    }

    private RecyclerView recyclerView;
    private AttendanceRecyclerViewAdapter attendanceAdapter;
    private RecyclerView.LayoutManager attendanceLayoutManager;

    private Date date;
    private String submission = "";
    private boolean edit_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String currentDate = simpleDateFormat.format(date);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.attendanceRecycleView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        attendanceLayoutManager = new LinearLayoutManager(AttendanceActivity.this);
        recyclerView.setLayoutManager(attendanceLayoutManager);
        attendanceAdapter = new AttendanceRecyclerViewAdapter(getDataSetMatches(), AttendanceActivity.this);
        recyclerView.setAdapter(attendanceAdapter);

        classRoomAdapter = new ClassRoomAdapter(this, mClassList);

        getClassList();

        Spinner spinnerClasses = findViewById(R.id.spinner_classes);
        spinnerClasses.setAdapter(classRoomAdapter);

        spinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Classroom clickedClass = (Classroom) adapterView.getItemAtPosition(i);
                getStudentAttendance("2018-11-24",clickedClass.getClassID());
                String clickedClassName = clickedClass.getClassName();
                Toast.makeText(getApplicationContext(), clickedClassName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
                    editStudentAttendance();

                    mSumbitButton.setVisibility(View.GONE);
                    mEditButton.setVisibility(View.VISIBLE);

                    attendanceAdapter.notifyDataSetChanged();
                    for (int i = 0; i < attendancesList.size(); i++) {
                        attendancesList.get(i).setEnabled(false);
                    }
                    attendanceAdapter.notifyDataSetChanged();

//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
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

                    }else{
                        mEditButton.setText("Edit");

                        editStudentAttendance();

                        attendanceAdapter.notifyDataSetChanged();
                        for (int i = 0; i < attendancesList.size(); i++) {
                            attendancesList.get(i).setEnabled(false);
                        }
                        attendanceAdapter.notifyDataSetChanged();

                        edit_done = true;

//                        Intent intent = getIntent();
//                        finish();
//                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void getClassList() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_CLASSROOM_LIST,
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

    private void getStudentAttendance(final String currentDate, final int classID) {
        attendancesList.clear();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_ATTENDANCE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("attendance");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Attendance attendance = new Attendance(obj.getInt("attendanceID"), obj.getString("attendanceStatus"), obj.getString("attendanceDate"), obj.getString("studentID"), obj.getString("attendanceSubmission"), obj.getString("studentName"), obj.getString("parentID"));
                                    attendancesList.add(attendance);
                                    attendanceAdapter.notifyDataSetChanged();

                                    if(obj.getString("attendanceSubmission").equals("not submitted")){
                                        submission = "not submitted";
                                    }
                                    else if(obj.getString("attendanceSubmission").equals("submitted")){
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
                params.put("classID",String.valueOf(classID));
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
                ConstantURLs.URL_ATTENDANCE_LIST,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.searchtool_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if (s == null || s.trim().isEmpty()) {
            attendanceAdapter = new AttendanceRecyclerViewAdapter(getDataSetMatches(), AttendanceActivity.this);
            recyclerView.setAdapter(attendanceAdapter);
            return true;
        }

        s = s.toLowerCase();
        ArrayList<Attendance> searchedList = new ArrayList<>();
        for(Attendance attendance : attendancesList)
        {
            String name = attendance.getStudentName().toLowerCase();
            if(name.contains(s)){
                searchedList.add(attendance);
            }

        }
        attendanceAdapter.setFilter(searchedList);
        return true;


    }
}
