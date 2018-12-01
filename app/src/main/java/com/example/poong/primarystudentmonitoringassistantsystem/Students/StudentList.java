package com.example.poong.primarystudentmonitoringassistantsystem.Students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
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

public class StudentList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private MyStudentRecyclerViewAdapter myStudentRecyclerViewAdapter;
    private RecyclerView.LayoutManager studentLayoutManager;

    private ArrayList<Classroom> mClassList = new ArrayList<Classroom>();
    private ClassRoomAdapter classRoomAdapter;

    private ArrayList<Student> studentList = new ArrayList<Student>();
    private List<Student> getDataSetMatches() { return studentList; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.student_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        studentLayoutManager = new LinearLayoutManager(StudentList.this);
        recyclerView.setLayoutManager(studentLayoutManager);
        myStudentRecyclerViewAdapter = new MyStudentRecyclerViewAdapter(getDataSetMatches(), StudentList.this);
        recyclerView.setAdapter(myStudentRecyclerViewAdapter);

        classRoomAdapter = new ClassRoomAdapter(this, mClassList);

        getClassList();

        setSpinnerClasses();

//        Spinner spinnerClasses = findViewById(R.id.spinner_classes);
//        spinnerClasses.setAdapter(classRoomAdapter);
//
//        spinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Classroom clickedClass = (Classroom) adapterView.getItemAtPosition(i);
//                getStudentList(clickedClass.getClassID());
//                String clickedClassName = clickedClass.getClassName();
//                Toast.makeText(getApplicationContext(), clickedClassName, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
    }

    private void setSpinnerClasses() {
        Spinner spinnerClasses = findViewById(R.id.spinner_classes);
        spinnerClasses.setAdapter(classRoomAdapter);

        spinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Classroom clickedClass = (Classroom) adapterView.getItemAtPosition(i);
                getStudentList(clickedClass.getClassID());
                String clickedClassName = clickedClass.getClassName();
                Toast.makeText(getApplicationContext(), clickedClassName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getClassList() {
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
                                setSpinnerClasses();
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

    public void getStudentList(final int classID) {
        studentList.clear();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_STUDENT_LIST,
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
                //For teacher
                params.put("classID", String.valueOf(classID));
                //For Parent
//                params.put("user_id", SharedPrefManager.getInstance(getActivity()).getUserID());
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
            myStudentRecyclerViewAdapter = new MyStudentRecyclerViewAdapter(getDataSetMatches(), StudentList.this);
            recyclerView.setAdapter(myStudentRecyclerViewAdapter);
            return true;
        }

        s = s.toLowerCase();
        ArrayList<Student> searchedList = new ArrayList<>();
        for(Student student : getDataSetMatches())
        {
            String name = student.getName().toLowerCase();
            if(name.contains(s)){
                searchedList.add(student);
            }
        }
        myStudentRecyclerViewAdapter.setFilter(searchedList);
        return true;
    }
}

