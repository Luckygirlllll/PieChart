package com.example.iryna.piechart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private PieChart mChart;
    private FrameLayout chartContainer;
    //we are going to display the pie chart for smartphone market shares
    private float[] yData ={5, 10, 15, 30, 40};
    private String[] xData ={"Sony", "Huawei","LG","Apple", "Samsung"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        chartContainer =(FrameLayout) findViewById(R.id.chartContainer);

        mChart = new PieChart(this);
        //add pie chart to main layout
        //mainLayout.addView(mChart);
        chartContainer.addView(mChart);
       //mainLayout.setBackgroundColor(Color.LTGRAY);
      //  mainLayout.setBackgroundColor(Color.parseColor("#55656C"));
        mainLayout.setBackgroundColor(Color.WHITE);

        //configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Smartphones Market Shares");

        //enable hole and configure
        mChart.setDrawHoleEnabled(true);
        // mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotation(0);
        mChart.setRotationEnabled(true);

        //set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                //display message when value selected
                if (e == null)
                    return;

                Toast.makeText(MainActivity.this, xData[e.getXIndex()]+" = "+e.getVal()+"%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        // add Data
        addData();

        //customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    private void addData(){
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i=0; i< yData.length; i++) {
            yVals1.add(new Entry(yData[i], i));
        }
            ArrayList<String> xVals = new ArrayList<String>();
        for (int i=0; i< xData.length; i++) {
            xVals.add(xData[i]);
        }
        //create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Market Share");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c: ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c: ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c: ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c: ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        //undo all higlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }
}
