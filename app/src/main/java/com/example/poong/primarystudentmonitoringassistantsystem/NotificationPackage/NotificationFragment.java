package com.example.poong.primarystudentmonitoringassistantsystem.NotificationPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.poong.primarystudentmonitoringassistantsystem.LoginActivity;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private NotificationViewModel mViewModel;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    public NotificationFragment(){}

    private RecyclerView mRecyclerView;
    private NotificationAdapter adapter;
    private List<NotificationMessage> message = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_fragment, container, false);

        DatabaseReference ref = database.getReference().child("notification").child(SharedPrefManager.getInstance(view.getContext()).getUserID());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NotificationMessage notificationMessage = dataSnapshot.getValue(NotificationMessage.class);
                message.add(adapter.getItemCount(), notificationMessage);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        mRecyclerView = view.findViewById(R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new NotificationAdapter(message);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuLogout:
                FirebaseMessaging.getInstance().unsubscribeFromTopic(SharedPrefManager.getInstance(getActivity()).getUserID());
                SharedPrefManager.getInstance(getActivity()).logout();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }

        return true;
    }


}
