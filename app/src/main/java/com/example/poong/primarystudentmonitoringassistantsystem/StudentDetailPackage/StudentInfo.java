package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.poong.primarystudentmonitoringassistantsystem.Constants;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;
import com.example.poong.primarystudentmonitoringassistantsystem.Teacher.Teacher;
import com.example.poong.primarystudentmonitoringassistantsystem.Teacher.TeacherProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentInfo extends Fragment {

    private TextView studentName, studentClass, studentMatric, studentDOB, studentGender,parentTextView;

    public StudentInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);

        studentName = view.findViewById(R.id.name);
        studentClass = view.findViewById(R.id.classroom);
        studentMatric = view.findViewById(R.id.studentID);
        studentDOB = view.findViewById(R.id.birthdate);
        studentGender = view.findViewById(R.id.gender);
        parentTextView = view.findViewById(R.id.parent_name);

        String studentID = getArguments().getString("studentID");

        getStudentDetails(studentID);

        parentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TeacherProfile.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    public void getStudentDetails(final String studentID) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_STUDENT_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("studentDetails");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    studentName.setText(obj.getString("studentName"));
                                    studentClass.setText(obj.getString("className"));
                                    studentMatric.setText(obj.getString("studentID"));
                                    studentDOB.setText(obj.getString("studentDOB"));
                                    studentGender.setText(obj.getString("studentGender"));
                                    parentTextView.setText(obj.getString("parentName"));
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
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
