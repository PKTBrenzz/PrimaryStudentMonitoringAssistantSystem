package com.example.poong.primarystudentmonitoringassistantsystem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationMessage> stringList = new ArrayList<>();

    public NotificationAdapter(List<NotificationMessage> messages){
        stringList = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notify_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleView.setText(stringList.get(position).title);
        holder.messageView.setText(stringList.get(position).body);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView titleView;
        public final TextView messageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            titleView = itemView.findViewById(R.id.notify_title);
            messageView = (TextView) itemView.findViewById(R.id.notify_message);
        }
    }
}
