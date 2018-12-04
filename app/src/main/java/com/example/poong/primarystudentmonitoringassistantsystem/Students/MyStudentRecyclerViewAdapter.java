package com.example.poong.primarystudentmonitoringassistantsystem.Students;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.poong.primarystudentmonitoringassistantsystem.Attendance.AttendanceRecyclerViewAdapter;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.StudentDetail;
import com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage.StudentActivity;
import com.example.poong.primarystudentmonitoringassistantsystem.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 *
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStudentRecyclerViewAdapter extends RecyclerView.Adapter<MyStudentRecyclerViewAdapter.ViewHolder>{

    private List<Student> mValues;
    private Context context;

    public MyStudentRecyclerViewAdapter(List<Student> items, Context context) {
        this.mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_student, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_student, parent, false);
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StudentActivity.class);

//                Intent intent = new Intent(v.getContext(), StudentDetail.class);
                intent.putExtra("NAME", mValues.get(position).getName());
                intent.putExtra("STUDENT_ID", mValues.get(position).getId());
                intent.putExtra("gender", mValues.get(position).getGender());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setFilter(ArrayList<Student> newList){
        mValues = new ArrayList<>();
        mValues.addAll(newList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public Student mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.studentName);
        }

        @Override
        public String toString() {
            return super.toString() + "'";
        }
    }
}
