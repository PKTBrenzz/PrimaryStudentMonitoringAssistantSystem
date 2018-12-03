package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.Teacher.TeacherProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentInfo extends Fragment {

    TextView parentTextView;

    public StudentInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);

        parentTextView = view.findViewById(R.id.parent_name);
        parentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TeacherProfile.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

}
