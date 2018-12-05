package com.example.poong.primarystudentmonitoringassistantsystem.Teacher;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.Attendance.Attendance;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.StudentDetail;

import java.util.List;

public class TeacherRecyclerViewAdapter extends RecyclerView.Adapter<TeacherRecyclerViewAdapter.ViewHolder> {

    private final List<Teacher> items;
    private Context context;

    public TeacherRecyclerViewAdapter(List<Teacher> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        TeacherRecyclerViewAdapter.ViewHolder viewHolder = new TeacherRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TeacherRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mItem = items.get(position);
        holder.mTeacherName.setText(items.get(position).getTeacher_name());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeacherProfile.class);
                intent.putExtra("profileEmail", items.get(position).getTeacher_email());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView mTeacherName;
        Teacher mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeacherName = (TextView) view.findViewById(R.id.teacherName);
        }

        @Override
        public String toString() {
            return super.toString() + "'";
        }
    }
}
