package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poong.primarystudentmonitoringassistantsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentTeacherNote extends Fragment {


    public StudentTeacherNote() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_teacher_note, container, false);
    }

}