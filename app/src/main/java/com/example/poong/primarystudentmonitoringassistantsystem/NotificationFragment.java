package com.example.poong.primarystudentmonitoringassistantsystem;

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

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private NotificationViewModel mViewModel;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    public NotificationFragment(){}

    private RecyclerView mRecyclerView;

    private List<String> message = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        for(int i = 0; i < 20; i++){
            message.add(String.valueOf(i));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_fragment, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        mRecyclerView = view.findViewById(R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setAdapter(new NotificationAdapter(message));

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
                SharedPrefManager.getInstance(getActivity()).logout();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }

        return true;
    }


}
