package com.example.moneytracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moneytracker.Database.Transactions;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;



//import com.github.mikephil.charting.data.PieEntry;


public class PieGraphFragment extends Fragment {

    List<Transactions> pieList;
    View view;
    public String[] dateData;
    public String[] catData;
    public String currData;
    public static final int[] COLORS = {
            rgb("#153"), rgb("#FFD700"), rgb("#07f50f"), rgb("#05f0ec"),
            rgb("#7a05f0"), rgb("#d305e6"), rgb("#e30264"), rgb("#380204"), rgb("#c98feb"),
            rgb("#8ed3e8"), rgb("#8ce6cb"), rgb("#9ce68a"), rgb("#edc980"), rgb("#473d28"),
            rgb("#483D8B")};

    public PieGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie_graph, container, false);

        PieChart pieChart = view.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(false);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();


        if (pieList != null) {
            List<Transactions> expenseList = new ArrayList<>();
            List<Transactions> incomeList = new ArrayList<>();
            float totalExpense = 0;
            float totalIncome = 0;

            float[] ctgAmts = new float[catData.length];

            for (Transactions transactions1 : pieList) {
                if (transactions1.getType().equals(false)) {
                    totalExpense += transactions1.getAmount();
                    expenseList.add(transactions1);
                } else {
                    totalIncome += transactions1.getAmount();
                    incomeList.add(transactions1);
                }
            }

            if (incomeList.size() == 0 || expenseList.size() != 0) {

                for (int i = 0; i < catData.length; i++) {
                    for (Transactions transactions1 : expenseList) {
                        if (transactions1.getCategory().equalsIgnoreCase(catData[i])) {
                            ctgAmts[i] += transactions1.getAmount();
                            if (ctgAmts[i] > 1000000000000.0) {
                                ctgAmts[i] = (float) 1000000000000.0;
                            }
                        }
                    }
                }

                for (int x = 0; x < catData.length; x++) {
                    if (!catData[x].equalsIgnoreCase("") && ctgAmts[x] != 0) {
                        yValues.add(new PieEntry(ctgAmts[x], catData[x]));
                        xValues.add(catData[x]);
                    }
                }

                /*Description description = new Description();
                description.setText("Total Expense: " + String.format("%.2f", totalExpense) + currData);
                description.setTextSize(17f);
                pieChart.setDescription(description);*/
                pieChart.getDescription().setEnabled(false);

                PieDataSet dataSet = new PieDataSet(yValues, "");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(COLORS);

                PieData data = new PieData(dataSet);
                data.setValueFormatter(new DefaultValueFormatter(0));
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.WHITE);
                pieChart.setData(data);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setCenterText("Total Expense: " + String.format("%.2f", totalExpense) + currData);
                pieChart.setDrawEntryLabels(false);

                //Set legends at the top
                Legend l = pieChart.getLegend();
                l.setEnabled(true);
                l.setWordWrapEnabled(true);
                l.setTextSize(14);
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setForm(Legend.LegendForm.CIRCLE);

                pieChart.invalidate();//Refresh
            } else {
                for (int i = 0; i < catData.length; i++) {
                    for (Transactions transactions1 : incomeList) {
                        if (transactions1.getCategory().equalsIgnoreCase(catData[i])) {
                            ctgAmts[i] += transactions1.getAmount();
                            if (ctgAmts[i] > 1000000000000.0) {
                                ctgAmts[i] = (float) 1000000000000.0;
                            }
                        }
                    }
                }

                for (int x = 0; x < catData.length; x++) {
                    if (!catData[x].equalsIgnoreCase("") && ctgAmts[x] != 0) {
                        yValues.add(new PieEntry(ctgAmts[x], catData[x]));
                        xValues.add(catData[x]);
                    }
                }

                Description description = new Description();
                description.setText("Total Income: " + String.format("%.2f", totalExpense) + currData);
                description.setTextSize(17f);
                pieChart.setDescription(description);


                PieDataSet dataSet = new PieDataSet(yValues, "");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(COLORS);

                PieData data = new PieData(dataSet);
                data.setValueFormatter(new DefaultValueFormatter(0));
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.WHITE);
                pieChart.setData(data);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setCenterText("Income\n" + "in " + currData);
                pieChart.setDrawEntryLabels(false);

                //Set legends at the top
                Legend l = pieChart.getLegend();
                l.setEnabled(true);
                l.setWordWrapEnabled(true);
                l.setTextSize(14);
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setForm(Legend.LegendForm.CIRCLE);

                pieChart.invalidate(); //Refresh
            }


        } else {
            Toast.makeText(getContext(), "Null Data", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    public void setTransactionList(List<Transactions> PieList) {
        this.pieList = PieList;
    }
}