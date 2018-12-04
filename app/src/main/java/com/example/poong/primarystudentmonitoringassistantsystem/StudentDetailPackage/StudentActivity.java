package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.poong.primarystudentmonitoringassistantsystem.PredictionDialog;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    private Context context = this;

    private AlertDialog.Builder builder;
    private String studentId;

    Intent intent;

    public StudentActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student2);



        intent = getIntent();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Student Detail");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setDialog();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StudentInfo(), "INFO");
        adapter.addFragment(new StudentGradeAnalyticsFragment(), "GRADE");
        adapter.addFragment(new StudentAttendanceCalender(), "ATTENDANCE");
        adapter.addFragment(new StudentTeacherNote(), "NOTE");
        viewPager.setAdapter(adapter);
    }

    private void setDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Actions");

        String[] options = {
                "Add teacher notes",
                "Perform student performance prediction",
                "Send emergency message to parents"
        };
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case 0:
                        Intent noteIntent = new Intent(context, AddNoteActivity.class);
                        noteIntent.putExtra("studentId", intent.getStringExtra("STUDENT_ID") );
                        context.startActivity(noteIntent);
                        break;
                    case 1:

                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            //TODO: bundle
            Bundle bundle = new Bundle();
            bundle.putString("studentID", intent.getStringExtra("STUDENT_ID"));

            fragment.setArguments(bundle);

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
