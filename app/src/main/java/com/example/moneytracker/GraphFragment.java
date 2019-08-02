package com.example.moneytracker;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moneytracker.Database.Transactions;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import com.github.mikephil.charting.charts.BarChart;


public class GraphFragment extends Fragment {

    View view;
    public BarChart barChart;
    public String[] dateData;
    public String currData;
    List<Transactions> barList;
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    float defaultBarWidth = -1;
    ArrayList<String> catList = new ArrayList<>();


    public GraphFragment() {
        // Required empty public constructor
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        barChart = view.findViewById(R.id.barChart);
        barChart.setDrawValueAboveBar(true);
        barChart.setPinchZoom(true);
        barChart.setMaxVisibleValueCount(62);
        barChart.setDrawGridBackground(true);
        barChart.setDoubleTapToZoomEnabled(true);


        if (barList != null) {
            List<Transactions> expenseList = new ArrayList<>();
            List<Transactions> incomeList = new ArrayList<>();
            catList = new ArrayList<>();

            int totalExpense = 0;
            int totalIncome = 0;

            for (Transactions transactionsForGraph : barList) {
                if (transactionsForGraph.getType().equals(false)) {
                    expenseList.add(transactionsForGraph);
                    totalExpense += transactionsForGraph.getAmount();
                } else {
                    incomeList.add(transactionsForGraph);
                    totalIncome += transactionsForGraph.getAmount();
                }
            }


            int bCount = 0;
            for (Transactions transactionsForGraph : barList) {
                String transDate;
                if (transactionsForGraph.getCreatedDay() > 9 && transactionsForGraph.getCreatedMonth() > 9 ) {
                    transDate = transactionsForGraph.getCreatedDay() + "/" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else if (transactionsForGraph.getCreatedDay() < 10 && transactionsForGraph.getCreatedMonth() < 10 ) {
                    transDate = "0" + transactionsForGraph.getCreatedDay() + "/" + "0" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else if (transactionsForGraph.getCreatedDay() > 9 && transactionsForGraph.getCreatedMonth() < 10 ) {
                    transDate = transactionsForGraph.getCreatedDay() + "/" + "0" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else {
                    transDate = "0" + transactionsForGraph.getCreatedDay() + "/" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                }
                if (!catList.contains(transDate)) {
                    catList.add(bCount, transDate);
                    bCount++;
                }
            }

            Collections.sort(catList, new Comparator<String>() {
                DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                @Override
                public int compare(String lhs, String rhs) {
                    try {
                        return f.parse(lhs).compareTo(f.parse(rhs));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });

            //Collections.sort(catList);
            int num = catList.size();

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                barEntries.add(i, new BarEntry(i, 0));
            }

            ArrayList<BarEntry> barEntries1 = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                barEntries1.add(i, new BarEntry(i, 0));
            }

            for (Transactions transactionsForGraph : expenseList) {
                String transDate;
                if (transactionsForGraph.getCreatedDay() > 9 && transactionsForGraph.getCreatedMonth() > 9 ) {
                    transDate = transactionsForGraph.getCreatedDay() + "/" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else if (transactionsForGraph.getCreatedDay() < 10 && transactionsForGraph.getCreatedMonth() < 10 ) {
                    transDate = "0" + transactionsForGraph.getCreatedDay() + "/" + "0" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else if (transactionsForGraph.getCreatedDay() > 9 && transactionsForGraph.getCreatedMonth() < 10 ) {
                    transDate = transactionsForGraph.getCreatedDay() + "/" + "0" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else {
                    transDate = "0" + transactionsForGraph.getCreatedDay() + "/" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                }
                int isHere = catList.indexOf(transDate);
                if (barEntries.get(isHere).getY() == 0) {
                    barEntries.set(isHere, new BarEntry(isHere, transactionsForGraph.getAmount()));
                } else {
                    float limit = transactionsForGraph.getAmount() + barEntries.get(isHere).getY();
                    if (limit > 1000000000000.0) {
                        limit = (float) 1000000000000.0;
                    }
                    BarEntry mEntry = new BarEntry(isHere, limit);
                    barEntries.set(isHere, mEntry);
                }
            }

            for (Transactions transactionsForGraph : incomeList) {
                String transDate;
                if (transactionsForGraph.getCreatedDay() > 9 && transactionsForGraph.getCreatedMonth() > 9 ) {
                    transDate = transactionsForGraph.getCreatedDay() + "/" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else if (transactionsForGraph.getCreatedDay() < 10 && transactionsForGraph.getCreatedMonth() < 10 ) {
                    transDate = "0" + transactionsForGraph.getCreatedDay() + "/" + "0" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else if (transactionsForGraph.getCreatedDay() > 9 && transactionsForGraph.getCreatedMonth() < 10 ) {
                    transDate = transactionsForGraph.getCreatedDay() + "/" + "0" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                } else {
                    transDate = "0" + transactionsForGraph.getCreatedDay() + "/" + transactionsForGraph.getCreatedMonth() + "/" + transactionsForGraph.getCreatedYear();
                }
                int isHere = catList.indexOf(transDate);
                if (barEntries1.get(isHere).getY() == 0) {
                    barEntries1.set(isHere, new BarEntry(isHere, transactionsForGraph.getAmount()));
                } else {
                    float limit = transactionsForGraph.getAmount() + barEntries.get(isHere).getY();
                    if (limit > 1000000000000.0) {
                        limit = (float) 1000000000000.0;
                    }
                    BarEntry mEntry = new BarEntry(isHere, limit);
                    barEntries1.set(isHere, mEntry);
                }
            }



            if (expenseList.size() == 0) {
                BarDataSet dataSetIncome = new BarDataSet(barEntries1, "Income ");
                dataSetIncome.setColor(Color.YELLOW);
                dataSetIncome.setValueTextColor(Color.rgb(55, 70, 73));
                dataSetIncome.setValueTextSize(10f);


                BarData data = new BarData(dataSetIncome);
                barChart.setData(data);
                barChart.getAxisLeft().setAxisMinimum(0);
                barChart.getDescription().setEnabled(true);
                barChart.getAxisRight().setAxisMinimum(0);
                barChart.setDrawValueAboveBar(true);
                barChart.setPinchZoom(true);

                Legend legend = barChart.getLegend();
                legend.setWordWrapEnabled(true);
                legend.setTextSize(12);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);
                legend.setForm(Legend.LegendForm.SQUARE);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                //xAxis.setCenterAxisLabels(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setAxisMaximum(catList.size());

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(catList));

                YAxis yAxis = barChart.getAxisLeft();
                //yAxis.removeAllLimitLines();
                yAxis.setTypeface(Typeface.DEFAULT);
                yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                yAxis.setTextColor(Color.BLACK);
                yAxis.setSpaceTop(10f);
                barChart.getAxisRight().setEnabled(true);

                Description description = new Description();
                description.setText("Total Expense: " +  totalExpense + currData + "\nTotal Income: " + totalIncome + currData);
                description.setTextSize(12f);
                description.setPosition(700F, 25F);
                barChart.setDescription(description);

            } else if (incomeList.size() == 0) {
                BarDataSet dataSet = new BarDataSet(barEntries, "Expenses");
                dataSet.setColor(Color.GRAY);
                dataSet.setValueTextColor(Color.rgb(55, 70, 73));
                dataSet.setValueTextSize(10f);
                BarData data = new BarData(dataSet);
                barChart.setData(data);
                barChart.getAxisLeft().setAxisMinimum(0);

                barChart.getDescription().setEnabled(true);
                barChart.getAxisRight().setAxisMinimum(0);
                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(62);
                barChart.setPinchZoom(true);
                barChart.setDrawGridBackground(false);

                Legend legend = barChart.getLegend();
                legend.setWordWrapEnabled(true);
                legend.setTextSize(12);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);
                legend.setForm(Legend.LegendForm.SQUARE);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                //xAxis.setCenterAxisLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setAxisMaximum(catList.size());

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(catList));

                YAxis leftAxis = barChart.getAxisLeft();
                //leftAxis.removeAllLimitLines();
                leftAxis.setTypeface(Typeface.DEFAULT);
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                leftAxis.setTextColor(Color.BLACK);
                leftAxis.setDrawGridLines(false);
                leftAxis.setSpaceTop(10f);
                barChart.getAxisRight().setEnabled(false);

                Description description = new Description();
                description.setText("Total Expense: " +  totalExpense + currData + "\nTotal Income: " + totalIncome + currData);
                description.setTextSize(12f);
                description.setPosition(700F, 25F);
                barChart.setDescription(description);

            } else {

                BarDataSet dataSet = new BarDataSet(barEntries, "Expenses");
                dataSet.setColor(Color.GRAY);
                dataSet.setValueTextColor(Color.rgb(55, 70, 73));
                dataSet.setValueTextSize(10f);
                BarDataSet dataSetIncome = new BarDataSet(barEntries1, "Income");
                dataSetIncome.setColor(Color.YELLOW);
                dataSetIncome.setValueTextColor(Color.rgb(55, 70, 73));
                dataSetIncome.setValueTextSize(10f);

                dataSets.add(dataSet);
                dataSets.add(dataSetIncome);
                BarData data = new BarData(dataSets);
                barChart.setData(data);
                barChart.getAxisLeft().setAxisMinimum(0);

                barChart.getDescription().setEnabled(true);
                barChart.getAxisRight().setAxisMinimum(0);
                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(62);
                barChart.setPinchZoom(true);

                Legend legend = barChart.getLegend();
                legend.setWordWrapEnabled(true);
                legend.setTextSize(12);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);
                legend.setForm(Legend.LegendForm.SQUARE);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setCenterAxisLabels(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setAxisMaximum(catList.size());

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(catList));

                YAxis leftAxis = barChart.getAxisLeft();
                //leftAxis.removeAllLimitLines();
                leftAxis.setTypeface(Typeface.DEFAULT);
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                leftAxis.setTextColor(Color.BLACK);
                leftAxis.setDrawGridLines(false);
                leftAxis.setSpaceTop(10f);
                barChart.getAxisRight().setEnabled(true);

                setBarWidth(data, catList.size());
                barChart.invalidate();

                Description description = new Description();
                description.setText("Total Expense: " +  totalExpense + currData + "\nTotal Income: " + totalIncome + currData);
                description.setTextSize(12f);
                description.setPosition(700F, 25F);
                barChart.setDescription(description);
            }
        } else {
            Toast.makeText(getContext(), "No data to show", Toast.LENGTH_SHORT).show();
        }

        return view;

    }

    public void setTransactionList(List<Transactions> TransList) {
        this.barList = TransList;
    }

    private void setBarWidth(BarData barData, int size) {
        if (dataSets.size() > 1) {
            float barSpace = 0.02f;
            float groupSpace = 0.3f;
            defaultBarWidth = (1 - groupSpace) / dataSets.size() - barSpace;
            if (defaultBarWidth >= 0) {
                barData.setBarWidth(defaultBarWidth);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Default Barwidth " + defaultBarWidth, Toast.LENGTH_SHORT).show();
            }
            int groupCount = catList.size();
            if (groupCount != -1) {
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart.getXAxis().setCenterAxisLabels(true);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "no of bar groups is " + groupCount, Toast.LENGTH_SHORT).show();
            }

            barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
            barChart.invalidate();
        }
    }

}