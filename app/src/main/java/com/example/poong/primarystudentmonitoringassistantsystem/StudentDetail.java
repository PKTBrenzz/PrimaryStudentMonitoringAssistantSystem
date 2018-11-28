package com.example.poong.primarystudentmonitoringassistantsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDetail extends AppCompatActivity {

    public static final float MAX = 12, MIN = 1f;
    public static final int NB_QUALITIES = 5;

    RadarChart radarChart;

    public ArrayList<String> labels = new ArrayList<>();

    public List<Grade> gradesList = new ArrayList<Grade>();

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        intent = getIntent();

        TextView textView = findViewById(R.id.name);
        textView.setText(intent.getStringExtra("NAME"));

        radarChart = (RadarChart) findViewById(R.id.grade_chart);
        radarChart.setBackgroundColor(Color.rgb(60, 65, 82));
        radarChart.getDescription().setEnabled(false);
//        radarChart.getXAxis().setDrawLabels(false);

        radarChart.getLegend().setEnabled(false);
        radarChart.setWebLineWidth(1f);
        radarChart.setWebColor(Color.LTGRAY);
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColorInner(Color.LTGRAY);
        radarChart.setWebAlpha(50);

        loadStudentGrade();

        radarChart.animateXY(1400, 1400, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

//            private final String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                return mActivities[(int) value % mActivities.length];
                return labels.get((int) value % labels.size());
            }
        });

        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);
//
        Legend l = radarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(1f);
        l.setYEntrySpace(1f);
        l.setTextColor(Color.WHITE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void loadStudentGrade() {
        final ArrayList<RadarEntry> entries = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_STUDENT_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("grade");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    String grade_id = obj.getString("grades_id").trim();
                                    String subject = obj.getString("subject").trim();
                                    Integer mark = obj.getInt("mark");

                                    String student_id = obj.getString("student_id").trim();

//                                    Grade grade = new Grade(grade_id, student_id, subject, mark);
//                                    gradesList.add(grade);

//                                    float val1 = (float) (Math.random() * MAX) + MIN;
                                    entries.add(new RadarEntry(mark.floatValue()));
                                    labels.add(subject);
                                }
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RadarDataSet set1 = new RadarDataSet(entries, "Last Week");
                        set1.setColor(Color.RED);
                        set1.setFillColor(Color.RED);
                        set1.setDrawFilled(true);
                        set1.setFillAlpha(180);
                        set1.setLineWidth(1f);
                        set1.setDrawHighlightCircleEnabled(true);
                        set1.setDrawHighlightIndicators(false);

                        ArrayList<IRadarDataSet> sets = new ArrayList<>();
                        sets.add(set1);

                        RadarData data = new RadarData(sets);
                        data.setValueTextSize(8f);
                        data.setDrawValues(false);
                        data.setValueTextColor(Color.WHITE);

                        radarChart.setData(data);
                        for(IDataSet<?> set : radarChart.getData().getDataSets()){
                            set.setDrawValues(!set.isDrawValuesEnabled());
                        }
                        radarChart.invalidate();
                        Toast.makeText(
                                getApplicationContext(),
                                intent.getStringExtra("STUDENT_ID"),
                                Toast.LENGTH_LONG
                        ).show();
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
                params.put("student_id", intent.getStringExtra("STUDENT_ID"));
//                params.put("student_id", "S00001");
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}
