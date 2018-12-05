package com.example.poong.primarystudentmonitoringassistantsystem.Students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.ConstantURLs;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentListAtParentView extends AppCompatActivity {
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private MyStudentRecyclerViewAdapter myStudentRecyclerViewAdapter;
    private RecyclerView.LayoutManager studentLayoutManager;

    private ArrayList<Student> studentList = new ArrayList<Student>();
    private List<Student> getDataSetMatches() { return studentList; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_at_parent_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Student List");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.student_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        studentLayoutManager = new LinearLayoutManager(StudentListAtParentView.this);
        recyclerView.setLayoutManager(studentLayoutManager);
        myStudentRecyclerViewAdapter = new MyStudentRecyclerViewAdapter(getDataSetMatches(), StudentListAtParentView.this);
        recyclerView.setAdapter(myStudentRecyclerViewAdapter);

        getStudentList();
    }

    private void getStudentList() {
        studentList.clear();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_STUDENT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("student");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Student student = new Student(obj.getString("studentName"), obj.getString("studentID"),obj.getString("studentGender"));
                                    studentList.add(student);
                                    myStudentRecyclerViewAdapter.notifyDataSetChanged();
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
                //For parent
                params.put("role", "parent");
                params.put("parentID", SharedPrefManager.getInstance(getApplicationContext()).getUserID());
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
