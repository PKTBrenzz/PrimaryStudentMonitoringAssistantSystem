package com.example.poong.primarystudentmonitoringassistantsystem.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.R;

import java.util.ArrayList;

public class ClassRoomAdapter extends ArrayAdapter<Classroom>{

    public ClassRoomAdapter(Context context, ArrayList<Classroom> mClassroomList){
        super(context, 0, mClassroomList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_classes, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.className);

        Classroom currentClassroom = getItem(position);

        if(currentClassroom != null){
        textView.setText(currentClassroom.getClassName());}

        return convertView;
    }
}
