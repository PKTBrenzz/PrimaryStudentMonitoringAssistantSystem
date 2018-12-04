package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.poong.primarystudentmonitoringassistantsystem.ConstantURLs;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.RequestHandler;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentGradeAnalyticsFragment extends Fragment {

    public RadarChart radarChart;


    private LineChart lineChart;

    public ArrayList<RadarEntry> RadarEntries = new ArrayList<>();
    public ArrayList<String> CourseLabels = new ArrayList<>();

    List<String> monthLabel = new ArrayList<>();
    public StudentGradeAnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_grade_analytics, container, false);

        String studentID = getArguments().getString("studentID");

        radarChart = (RadarChart) view.findViewById(R.id.radarchart);
        radarChart.setBackgroundColor(Color.rgb(60, 65, 82));
        radarChart.getDescription().setEnabled(false);
//        radarChart.getXAxis().setDrawLabels(false);

        radarChart.getLegend().setEnabled(false);
        radarChart.setWebLineWidth(1f);
        radarChart.setWebColor(Color.LTGRAY);
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColorInner(Color.LTGRAY);
        radarChart.setWebAlpha(50);

        getStudentRadarResult(studentID);

        radarChart.animateXY(1400, 1400, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

//            private final String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                return mActivities[(int) value % mActivities.length];
                return CourseLabels.get((int) value % CourseLabels.size());
            }
        });

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);

//        Legend l = radarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(1f);
//        l.setYEntrySpace(1f);
//        l.setTextColor(Color.WHITE);


//        lineChart = view.findViewById(R.id.linechart);
//        monthLabel.add("January");
//        monthLabel.add("February");
//        monthLabel.add("March");
//
//        ArrayList<Entry> yData1 = new ArrayList<>();
//        yData1.add(new Entry(1, 55));
//        yData1.add(new Entry(2, 66));
//        yData1.add(new Entry(3, 75));
//
//        LineDataSet lineDataSet1 = new LineDataSet(yData1, "ENGLISH");
//        lineDataSet1.setFillAlpha(110);
//        lineDataSet1.setColor(Color.RED);
//
//        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
//        lineDataSets.add(lineDataSet1);
//
//        LineData lineData = new LineData(lineDataSets);
//        lineChart.setData(lineData);

        return view;
    }

    private void getStudentRadarResult(final String studentID){
        RadarEntries.clear();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConstantURLs.URL_STUDENT_RADAR_RESULT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    String course = obj.getString("courseName").trim();
                                    Double mark = obj.getDouble("averageMark");

                                    RadarEntries.add(new RadarEntry(mark.floatValue()));
                                    CourseLabels.add(course);
                                }
                            }else{
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RadarDataSet set1 = new RadarDataSet(RadarEntries, "Course Average Mark");
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("studentID", studentID);
//                params.put("student_id", "S00001");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
