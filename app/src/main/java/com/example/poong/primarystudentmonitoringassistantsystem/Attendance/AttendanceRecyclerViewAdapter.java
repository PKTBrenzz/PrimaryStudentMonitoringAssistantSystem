package com.example.poong.primarystudentmonitoringassistantsystem.Attendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.NotificationPackage.NotificationMessage;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceRecyclerViewAdapter extends RecyclerView.Adapter<AttendanceRecyclerViewAdapter.ViewHolder> {

    private List<Attendance> items;
    private Context context;
//    ArrayList<Attendance> edited = new ArrayList<>();

    public AttendanceRecyclerViewAdapter(List<Attendance> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_attendance, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = items.get(position);
        holder.mAttendanceStudentName.setText(items.get(position).getStudentName());
        if(items.get(position).getAttendance_status().equals("present")){
            holder.mCheckBox.setChecked(true);
            holder.mCheckBox.setEnabled(items.get(position).isEnabled());
        }
        else{
            holder.mCheckBox.setChecked(false);
            holder.mCheckBox.setEnabled(items.get(position).isEnabled());
        }

        holder.setItemClickListener(new ViewHolder.ItemClickListener(){
            @Override
            public void onItemClick(View v, int pos){
                CheckBox mCheckBox = (CheckBox) v;
                if(mCheckBox.isChecked()){
                    items.get(pos).setAttendance_status("present");
                }else{
                    items.get(pos).setAttendance_status("absent");
                }
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notification").child(items.get(pos).getParentID()).push();
//                NotificationMessage message = new NotificationMessage("Attendance Notification",
//                        items.get(pos).getStudentName() + " is " + items.get(pos).getAttendance_status() + " today",new Date().getTime());
//                message.data.put("tag", "student");
//                message.data.put("student", items.get(pos).getStudent_id());
//                ref.setValue(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setFilter(ArrayList<Attendance> searchedList){
        items = new ArrayList<>();
        items.addAll(searchedList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View mView;
        TextView mAttendanceStudentName;
        CheckBox mCheckBox;
        Attendance mItem;

        ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAttendanceStudentName = (TextView) view.findViewById(R.id.attendanceStudentName);
            mCheckBox = (CheckBox) view.findViewById(R.id.attendanceCheckBox);

            mCheckBox.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v){
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }

        interface ItemClickListener {
            void onItemClick(View v,int pos);
        }

        @Override
        public String toString() {
            return super.toString() + "'";
        }
    }
}
