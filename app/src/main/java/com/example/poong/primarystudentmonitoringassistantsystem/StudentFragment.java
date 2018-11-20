package com.example.poong.primarystudentmonitoringassistantsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poong.primarystudentmonitoringassistantsystem.dummy.DummyContent;
import com.example.poong.primarystudentmonitoringassistantsystem.dummy.DummyContent.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public Student temp;
    public List<Student> studentList = new ArrayList<Student>();
    public RecyclerView recyclerView;

    public com.example.poong.primarystudentmonitoringassistantsystem.MyStudentRecyclerViewAdapter arrayAdapter;
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

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        arrayAdapter = new MyStudentRecyclerViewAdapter(studentList);
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
        for (int i = 1; i <= 3; i++) {
            studentList.add(new Student(String.valueOf(i), String.valueOf(i)));
            arrayAdapter.notifyDataSetChanged();
        }
        loadStudentList();
    }

    public void loadStudentList() {

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
                                    arrayAdapter.notifyDataSetChanged();
                                    studentList.add(student);
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
        }
        ;

        RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
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
