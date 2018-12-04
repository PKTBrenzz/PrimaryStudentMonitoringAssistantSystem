package com.example.poong.primarystudentmonitoringassistantsystem;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PredictionDialog extends android.support.v4.app.DialogFragment {

    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }

    private OnCompleteListener mListener;

    private EditText mGenderText, mAttendanceText, mStageID, mSemester, mRaisedhands, mDiscussion;
    private String absenceCount = "";
    private Date date;
    private String classPredicted = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_prediction_dialog);
        date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String currentDate = simpleDateFormat.format(date);

        final String studentID = getArguments().getString("studentID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_prediction_dialog, null);

        mStageID = view.findViewById(R.id.Stage1D);
        mSemester = view.findViewById(R.id.Semester);
        mRaisedhands = view.findViewById(R.id.raisedhands);
        mDiscussion = view.findViewById(R.id.Discussion);

        getAbsencesCount(studentID, view);

        mGenderText = view.findViewById(R.id.gender);
        final String gender;
        if(getArguments().getString("gender").equals("Male")){
            gender = "M";
        }else{
            gender = "F";
        }

        mGenderText.setText(gender);

        builder.setView(view);
        builder.setPositiveButton("Predict", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String stageID = mStageID.getText().toString().trim();
                String semester = mSemester.getText().toString().trim();
                String raisedhands = mRaisedhands.getText().toString().trim();
                String discussion = mDiscussion.getText().toString().trim();
//                predict(studentID,gender,"afternoon", "F", absenceCount,"15", "20", currentDate);

                predict(studentID,gender,stageID, semester, absenceCount,raisedhands, discussion, currentDate);

//                Toast.makeText(
//                        getActivity().getApplicationContext(),
//                        classPredicted,
//                        Toast.LENGTH_LONG
//                ).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener)getActivity();
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

    private void getAbsencesCount(final String studentID, View view) {
        mAttendanceText= view.findViewById(R.id.StudentAbsenceDays);

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
                                    int count = obj.getInt("Count");

                                    if(attendanceStatus.equals("absent")){
                                        if(count <= 7 ){
                                            absenceCount = "Under-7";
                                        }
                                        else{
                                            absenceCount = "Above-7";
                                        }
                                    }
                                }
                                mAttendanceText.setText(absenceCount);
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        absenceCount,
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
                params.put("studentID", studentID);
                return params;
            }
        };

        RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void predict(final String studentID, final String gender, final String StageID, final String Semester, final String StudentAbsenceDays, final String raisedhands, final String Discussion, final String currentDate) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_PREDICTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("message");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    Log.d("TTT",obj.getString("Class"));
                                    classPredicted = obj.getString("Class");
                                }

                                mListener.onComplete(classPredicted);

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
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("studentID", studentID);
                params.put("gender", gender);
                params.put("StageID", StageID);
                params.put("Semester", Semester);
                params.put("StudentAbsenceDays", StudentAbsenceDays);
                params.put("raisedhands",raisedhands);
                params.put("Discussion", Discussion);
                params.put("predictDate", currentDate);
                return params;
            }
        };

        RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
