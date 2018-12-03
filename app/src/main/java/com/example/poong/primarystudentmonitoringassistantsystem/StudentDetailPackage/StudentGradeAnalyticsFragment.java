package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poong.primarystudentmonitoringassistantsystem.R;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentGradeAnalyticsFragment extends Fragment {

    private RadarChart radarChart;
    private LineChart lineChart;
    List<String> labels = new ArrayList<>();
    List<String> monthLabel = new ArrayList<>();
    public StudentGradeAnalyticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_grade_analytics, container, false);


        labels.add("ENGLISH");
        labels.add("MALAY");
        labels.add("MATH");
        labels.add("SCIENCE");
        labels.add("CHINESE");

        ArrayList<RadarEntry> entries = new ArrayList<>();
        entries.add(new RadarEntry(new Integer(80).floatValue()));
        entries.add(new RadarEntry(new Integer(65).floatValue()));
        entries.add(new RadarEntry(new Integer(40).floatValue()));
        entries.add(new RadarEntry(new Integer(90).floatValue()));
        entries.add(new RadarEntry(new Integer(40).floatValue()));

        radarChart = (RadarChart) view.findViewById(R.id.radarchart);
        radarChart.setBackgroundColor(Color.rgb(60, 65, 82));
        radarChart.getDescription().setEnabled(false);
        radarChart.getLegend().setEnabled(false);
        radarChart.setWebLineWidth(1f);
        radarChart.setWebColor(Color.LTGRAY);
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColorInner(Color.LTGRAY);
        radarChart.setWebAlpha(50);

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

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
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

        Legend l = radarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(1f);
        l.setYEntrySpace(1f);
        l.setTextColor(Color.WHITE);

        lineChart = view.findViewById(R.id.linechart);
        monthLabel.add("January");
        monthLabel.add("February");
        monthLabel.add("March");

        ArrayList<Entry> yData1 = new ArrayList<>();
        yData1.add(new Entry(1, 55));
        yData1.add(new Entry(2, 66));
        yData1.add(new Entry(3, 75));

        LineDataSet lineDataSet1 = new LineDataSet(yData1, "ENGLISH");
        lineDataSet1.setFillAlpha(110);
        lineDataSet1.setColor(Color.RED);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet1);

        LineData lineData = new LineData(lineDataSets);
        lineChart.setData(lineData);

        return view;
    }

}
