package com.example.poong.primarystudentmonitoringassistantsystem.Teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.Chat.ChatMessageActivity;
import com.example.poong.primarystudentmonitoringassistantsystem.ConstantURLs;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;
import com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage.AddNoteActivity;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TeacherProfile extends AppCompatActivity {

    private TextView mTeacherName,mTeacherEmail,mTeacherPhone;
    private FloatingActionButton fab;

    private Context context = this;
    private String teacherId;
    private String name;
    private AlertDialog.Builder builder;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        intent = getIntent();
        intent.getStringExtra("profileEmail");
        Toast.makeText(
                getApplicationContext(),
                intent.getStringExtra("profileEmail"),
                Toast.LENGTH_LONG
        ).show();

        mTeacherName = findViewById(R.id.teacher_name);
        mTeacherEmail = findViewById(R.id.textView18);
        mTeacherPhone = findViewById(R.id.textView20);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        getTeacherProfile();
    }

    private void setDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Actions");

        String[] options = {
                "Start instant messaging"
        };
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case 0:
                        FirebaseDatabase.getInstance().getReference().child("chat_with").child(SharedPrefManager.getInstance(context).getUserID()).push()
                                .setValue(teacherId);
                        FirebaseDatabase.getInstance().getReference().child("chat_with").child(teacherId).push()
                                .setValue(SharedPrefManager.getInstance(context).getUserID());
                        Intent intent = new Intent(context, ChatMessageActivity.class);
                        intent.putExtra("NAME", teacherId);
                        intent.putExtra("ROOM", name);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    private void getTeacherProfile() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_PROFILE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                teacherId = obj.getString("teacherID");
                                name = obj.getString("teacherName");
                                mTeacherName.setText(obj.getString("teacherName"));
                                mTeacherEmail.setText(obj.getString("teacherEmail"));
                                mTeacherPhone.setText("+60" + obj.getString("teacherPhone"));
                                setDialog();

                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("role", "teacher");
                params.put("profileEmail", intent.getStringExtra("profileEmail"));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
