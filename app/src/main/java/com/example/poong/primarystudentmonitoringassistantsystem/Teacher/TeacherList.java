package com.example.poong.primarystudentmonitoringassistantsystem.Teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.Constants;
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

public class TeacherList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter teacherAdapter;
    private RecyclerView.LayoutManager teacherLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        recyclerView = findViewById(R.id.teacher_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        teacherLayoutManager = new LinearLayoutManager(TeacherList.this);
        recyclerView.setLayoutManager(teacherLayoutManager);
        teacherAdapter = new TeacherRecyclerViewAdapter(getDataSetMatches(), TeacherList.this);
        recyclerView.setAdapter(teacherAdapter);

       getTeacherList();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void getTeacherList() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_TEACHER_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("teacher");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Teacher teacher = new Teacher(obj.getString("teacherID"), obj.getString("teacherName"));
                                    teacherList.add(teacher);
                                    teacherAdapter.notifyDataSetChanged();
                                }

                                Toast.makeText(
                                        getApplicationContext(),
                                        SharedPrefManager.getInstance(getApplicationContext()).getUserID(),
                                        Toast.LENGTH_LONG
                                ).show();
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
                params.put("parent_id", SharedPrefManager.getInstance(getApplicationContext()).getUserID());
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
    private List<Teacher> getDataSetMatches() {
            return teacherList;
        }
}
