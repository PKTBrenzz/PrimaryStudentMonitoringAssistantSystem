package com.example.poong.primarystudentmonitoringassistantsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.example.poong.primarystudentmonitoringassistantsystem.Students.MyStudentRecyclerViewAdapter;
import com.example.poong.primarystudentmonitoringassistantsystem.Students.Student;
import com.example.poong.primarystudentmonitoringassistantsystem.Students.StudentList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentFragment extends Fragment implements SearchView.OnQueryTextListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public Student temp;
    public List<Student> studentList = new ArrayList<Student>();
    public RecyclerView recyclerView;

    private ArrayList<Classroom> mClassList;
    private ClassRoomAdapter classRoomAdapter;
    Spinner spinner;
    Toolbar toolbar;

    public MyStudentRecyclerViewAdapter arrayAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StudentFragment newInstance(int columnCount) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        classRoomAdapter = new ClassRoomAdapter(getActivity().getApplicationContext(), mClassList);

//        arrayAdapter = new MyStudentRecyclerViewAdapter(studentList, StudentList.this);
//        //TODO: Create test models from MySQL for students class
//        List<Student> test = new ArrayList<>();
//
//        //Get mysql student count
//        int studentCount = 0;
//        String[] names;
//        String[] id;
//        //Get all student data
//        for(int i = 0; i < studentCount; i++){
//            Student data = new Student();
//            //todo: Get data from mySQL and assign it into data in correct order
//            studentList.add(data);
//        }
        //TEST MODEL REMOVE IF NEEDED TO TEST REAL DATA
//        for (int i = 1; i <= 3; i++) {
//            studentList.add(new Student(String.valueOf(i), String.valueOf(i)));
//            arrayAdapter.notifyDataSetChanged();
//        }
//        loadClassList();
        loadStudentList(1);
    }

    private void loadClassList() {
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

                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        SharedPrefManager.getInstance(getActivity()).getUserID(),
                                        Toast.LENGTH_LONG
                                ).show();
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
                params.put("user_id", SharedPrefManager.getInstance(getActivity()).getUserID());
                return params;
            }
        };

        RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void loadStudentList(final int classID) {
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
                                    Student student = new Student(obj.getString("name"), obj.getString("student_id"));
                                    studentList.add(student);
                                    arrayAdapter.notifyDataSetChanged();
                                }

                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        SharedPrefManager.getInstance(getActivity()).getUserID(),
                                        Toast.LENGTH_LONG
                                ).show();
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
                //For teacher
                params.put("classID", String.valueOf(classID));
                //For Parent
//                params.put("user_id", SharedPrefManager.getInstance(getActivity()).getUserID());
                return params;
            }
        }
        ;

        RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        Context context = view.getContext();

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        spinner = view.findViewById(R.id.spinner_classes);
//        spinner.setAdapter(classRoomAdapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Classroom clickedClass = (Classroom) adapterView.getItemAtPosition(i);
//                loadStudentList(clickedClass.getClassID());
//                String clickedClassName = clickedClass.getClassName();
//                Toast.makeText(getActivity().getApplicationContext(), clickedClassName, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

        recyclerView = (RecyclerView) view.findViewById(R.id.student_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
        recyclerView.setAdapter(arrayAdapter);
        // Set the adapter
//        if (view instanceof RecyclerView) {
//
//        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.searchtool_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(this);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                arrayAdapter.getFilter().filter(s);
//                return false;
//            }
//        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        s = s.toLowerCase();
        ArrayList<Student> newList = new ArrayList<>();
        for(Student newstudent : studentList)
        {
            String name = newstudent.getName().toLowerCase();
            if(name.contains(s)){
                newList.add(newstudent);
            }

        }
        arrayAdapter.setFilter(newList);

        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
