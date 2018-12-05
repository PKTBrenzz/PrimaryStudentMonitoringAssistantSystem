package com.example.poong.primarystudentmonitoringassistantsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.Chat.ChatListFragment;
import com.example.poong.primarystudentmonitoringassistantsystem.NotificationPackage.NotificationFragment;

public class Main2Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        if(SharedPrefManager.getInstance(getApplicationContext()).getUserIdentity().equals("T")){

            loadFragment(new HomeTeacherFragment());
        }
        else{
            loadFragment(new HomeParentFragment());
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Log.d("TTT", SharedPrefManager.getInstance(getApplicationContext()).getUserIdentity());
                if(SharedPrefManager.getInstance(getApplicationContext()).getUserIdentity().equals("T")){

                    fragment = new HomeTeacherFragment();
                }
                else{
                    fragment = new HomeParentFragment();
                }
                break;
//            case R.id.navigation_student:
//                fragment = new StudentFragment();
//                break;
            case R.id.navigation_dashboard:
                fragment = new ChatListFragment();
                break;
            case R.id.navigation_notifications:
                fragment = new NotificationFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
