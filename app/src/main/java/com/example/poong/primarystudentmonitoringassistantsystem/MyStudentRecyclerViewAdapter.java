package com.example.poong.primarystudentmonitoringassistantsystem;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.poong.primarystudentmonitoringassistantsystem.dummy.DummyContent.DummyItem;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 *
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStudentRecyclerViewAdapter extends RecyclerView.Adapter<MyStudentRecyclerViewAdapter.ViewHolder> implements Filterable{

    private List<Student> mValues = new ArrayList<>();
    private final List<Student> mValuesFull ;


    public MyStudentRecyclerViewAdapter(List<Student> items) {
        mValues = items;
        mValuesFull = new ArrayList<>(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StudentDetail.class);
                intent.putExtra("NAME", mValues.get(position).getName());
                intent.putExtra("STUDENT_ID", mValues.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter(){
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            List<Student> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(mValuesFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Student item : mValuesFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            mValues.clear();
            mValues.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

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


    public void setFilter(ArrayList<Student> newList){
        mValues = new ArrayList<>();
        mValues.addAll(newList);
        notifyDataSetChanged();
    }
}
