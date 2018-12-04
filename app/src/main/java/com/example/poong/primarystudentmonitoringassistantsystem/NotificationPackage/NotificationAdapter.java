package com.example.poong.primarystudentmonitoringassistantsystem.NotificationPackage;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.Chat.ChatMessageActivity;
import com.example.poong.primarystudentmonitoringassistantsystem.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        final Map<String, String> map = stringList.get(position).data;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map == null){
                    return;
                }
                else if (map.get("tag").equals("chat")){
                    Intent intent = new Intent(view.getContext(), ChatMessageActivity.class);
                    intent.putExtra("NAME", map.get("receiver"));
                    intent.putExtra("ROOM", map.get("name"));
                    view.getContext().startActivity(intent);
                }
            }
        });
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
