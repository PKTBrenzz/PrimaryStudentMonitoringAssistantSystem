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
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.PredictionDialog;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.StudentDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements PredictionDialog.OnCompleteListener{

    @Override
    public void onComplete(String time) {
        if(time.equals("L")){
            time = "Bad";

        }
        else if(time.equals("M")){
            time = "Average";
        }
        else{
            time = "Good";
        }

        mClassPerformanceTextView.setText(getResources().getString(R.string.predict) + time);
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setTitle("Result");
        builder.setMessage(getResources().getString(R.string.predict) + time);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private TextView mClassPerformanceTextView;

    private SpannableStringBuilder spannableStringBuilder;

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

        spannableStringBuilder = new SpannableStringBuilder();

        mClassPerformanceTextView = findViewById(R.id.classPerformanceTextView);

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
                        Bundle bundle = new Bundle();
                        bundle.putString("studentID", intent.getStringExtra("STUDENT_ID"));
                        bundle.putString("gender", intent.getStringExtra("gender"));

                        PredictionDialog predictionDialog = new PredictionDialog();
                        predictionDialog.setArguments(bundle);

                        predictionDialog.show(getSupportFragmentManager(), "my_dialog");
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
