package com.example.poong.primarystudentmonitoringassistantsystem.Parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.ConstantURLs;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParentProfile extends AppCompatActivity {

    private TextView mParentName,mParentEmail,mParentPhone;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_detail);

        intent = getIntent();

        mParentName = findViewById(R.id.parent_name);
        mParentEmail = findViewById(R.id.textView18);
        mParentPhone = findViewById(R.id.textView20);

        getParentProfile();
    }

    private void getParentProfile() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_PROFILE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){

                                mParentName.setText(obj.getString("parentName"));
                                mParentEmail.setText(obj.getString("parentEmail"));
                                mParentPhone.setText("+60" + obj.getString("parentPhone"));
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
                params.put("role", "parent");
                params.put("profileEmail", intent.getStringExtra("profileEmail"));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
