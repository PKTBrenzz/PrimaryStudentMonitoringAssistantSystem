package com.example.poong.primarystudentmonitoringassistantsystem;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.Attendance.AttendanceActivity;
import com.example.poong.primarystudentmonitoringassistantsystem.Students.StudentList;
import com.example.poong.primarystudentmonitoringassistantsystem.Teacher.TeacherList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;
    private HomeViewModel mViewModel;
    private Button adttendanceButton, mShowTeacherButton;

    private ImageButton attendanceButton, studentButton;
    private TextView userName;

    private String identity = SharedPrefManager.getInstance(getActivity()).getUserIdentity();

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_teacher, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        userName = view.findViewById(R.id.userName);
        userName.setText(SharedPrefManager.getInstance(getActivity()).getUsername());

        attendanceButton = view.findViewById(R.id.attendance);
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AttendanceActivity.class);
                startActivity(intent);
            }
        });

        studentButton = view.findViewById(R.id.show_student);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StudentList.class);
                startActivity(intent);
            }
        });

//        mShowTeacherButton = view.findViewById(R.id.show_teacher);
//        if(identity.equals("P")){
//            mShowTeacherButton.setVisibility(View.VISIBLE);
//            mShowTeacherButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), TeacherList.class);
//                    startActivity(intent);
//                }
//            });
//        }
//        else if(identity.equals("T")){
//            mShowTeacherButton.setVisibility(View.GONE);
//        }


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
