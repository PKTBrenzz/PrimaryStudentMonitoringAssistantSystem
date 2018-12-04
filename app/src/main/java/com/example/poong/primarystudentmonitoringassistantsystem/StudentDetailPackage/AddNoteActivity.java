package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    Context context = this;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        intent = getIntent();
        final String studentId = intent.getStringExtra("studentId");

        editText = findViewById(R.id.editText);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeacherNote note = new TeacherNote();
                note.id = SharedPrefManager.getInstance(context).getUserID();
                note.name = SharedPrefManager.getInstance(context).getUsername();
                note.content = editText.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("student_note").child(studentId);
                ref.push().setValue(note);

                finish();
            }
        });

    }
}
