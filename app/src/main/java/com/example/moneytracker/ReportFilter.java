package com.example.moneytracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Database.Transactions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ReportFilter extends AppCompatActivity {

    EditText minEnteredAmount, maxEnteredAmount;
    TextView displayDateStart, displayDateEnd;
    MultiSelectSpinner categorySelect, paymentMethodSelect;
    Spinner currencySelect;
    private ArrayList<ListItems> listItems = new ArrayList<>();
    private String dateData;
    private String currData;
    String transactionType;

    Button search;

    int dayFrom = 0, monthFrom = 0, yearFrom = 0, dayTo = 0, monthTo = 0, yearTo = 0;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_filter);

        final String[] categoryList = getCategories();
        //String[] currencyList = getResources().getStringArray(R.array.currencyList);
        String[] paymentMethodList = getResources().getStringArray(R.array.paymentMethodList);

        displayDateStart = findViewById(R.id.enter_start_date_graph_text);
        displayDateEnd = findViewById(R.id.enter_end_date);

        minEnteredAmount = findViewById(R.id.min_amount);
        maxEnteredAmount = findViewById(R.id.max_amount);

        categorySelect = findViewById(R.id.category);
        paymentMethodSelect = findViewById(R.id.enter_payment_graph);
        currencySelect = findViewById(R.id.currency);

        categorySelect.setItemList(categoryList);
        paymentMethodSelect.setItemList(paymentMethodList);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.currencyList, android.R.layout.simple_spinner_dropdown_item);
        //currencySelect.setAdapter(adapter);

        displayDateStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int md = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(ReportFilter.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day){
                        String date = ""+day+ "/" +(month+1)+"/"+year;
                        displayDateStart.setText(date);
                        dayFrom=day;
                        monthFrom=month+1;
                        yearFrom=year;

                    }
                },y,md,d);
                datePicker.show();
            }
        });
        displayDateEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int md = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(ReportFilter.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year,int month, int day){
                        String date = ""+day+ "/" + (month+1) +"/"+year;
                        displayDateEnd.setText(date);
                        dayTo = day;
                        monthTo = month+1;
                        yearTo = year;
                    }
                },y,md,d);
                datePicker.show();
            }
        } );

        search = findViewById(R.id.search_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> categoryChosen = categorySelect.getSelectedStrings(),
                        paymentMethodChosen = paymentMethodSelect.getSelectedStrings();
                //currency = currencySelect.getSelectedStrings();

                int minAmount, maxAmount;
                List<Transactions> transTypem;
                List<Transactions> transType = new ArrayList<>();

                if (minEnteredAmount.getText().toString().equals("")) {
                    minAmount = 0;
                } else {
                    minAmount = Integer.parseInt(minEnteredAmount.getText().toString());
                }

                if (maxEnteredAmount.getText().toString().equals("")) {
                    maxAmount = 0;
                } else {
                    maxAmount = Integer.parseInt(maxEnteredAmount.getText().toString());
                }


                if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount == 0 && maxAmount == 0)) {
                    transTypem = MainActivity.appDB.daoAccess().getAllTransactions();
                    transType.addAll(transTypem);
                } else if ((categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String paymentMethodC : paymentMethodChosen) {
                        transTypem = MainActivity.appDB.daoAccess().getTransactionPaymentMethod(paymentMethodC);
                        transType.addAll(transTypem);
                    }
                } else if ((!categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount == 0 && maxAmount == 0)) {

                    for (String categoryC : categoryChosen) {
                        transTypem = MainActivity.appDB.daoAccess().getTransactionCategory(categoryC);
                        transType.addAll(transTypem);
                    }
                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount == 0 && maxAmount == 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDate(dayFrom, monthFrom,
                            yearFrom, dayTo, monthTo, yearTo);
                    transType.addAll(transTypem);
                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount == 0 && maxAmount == 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateFrom(dayFrom, monthFrom, yearFrom);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount == 0 && maxAmount == 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateTo(dayTo, monthTo, yearTo);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount != 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionAmount(minAmount, maxAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount != 0 && maxAmount == 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionMinAmount(minAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount == 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionMaxAmount(maxAmount);
                    transType.addAll(transTypem);

                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionCategoryPaymentMethod(categoryC, paymentMethodC);
                            transType.addAll(transTypem);
                        }

                    }
                } else if ((!categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionCategoryDate(categoryC, dayFrom, monthFrom,
                                yearFrom, dayTo, monthTo, yearTo);
                        transType.addAll(transTypem);
                    }

                } else if ((!categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionCategoryDateFrom(categoryC, dayFrom, monthFrom, yearFrom);
                        transType.addAll(transTypem);
                    }

                } else if ((!categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionCategoryDateTo(categoryC, dayTo, monthTo, yearTo);
                        transType.addAll(transTypem);
                    }

                } else if ((!categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount != 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionCategoryAmount(categoryC, minAmount, maxAmount);
                        transType.addAll(transTypem);
                    }

                } else if ((!categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount != 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionCategoryMinAmount(categoryC, minAmount);
                        transType.addAll(transTypem);
                    }

                } else if ((!categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount == 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionCategoryMaxAmount(categoryC, maxAmount);
                        transType.addAll(transTypem);
                    }
                } else if ((categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String paymentMethodC : paymentMethodChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionPaymentMethodDate(paymentMethodC, dayFrom, monthFrom,
                                yearFrom, dayTo, monthTo, yearTo);
                        transType.addAll(transTypem);
                    }

                } else if ((categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String paymentMethodC : paymentMethodChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionPaymentMethodDateFrom(paymentMethodC, dayFrom, monthFrom, yearFrom);
                        transType.addAll(transTypem);
                    }
                } else if ((categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount == 0 && maxAmount == 0)) {
                    for (String paymentMethodC : paymentMethodChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionPaymentMethodDateTo(paymentMethodC, dayTo, monthTo, yearTo);
                        transType.addAll(transTypem);
                    }
                } else if ((categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount != 0 && maxAmount != 0)) {
                    for (String paymentMethodC : paymentMethodChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionPaymentMethodAmount(paymentMethodC, minAmount, maxAmount);
                        transType.addAll(transTypem);
                    }
                } else if ((categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount != 0 && maxAmount == 0)) {
                    for (String paymentMethodC : paymentMethodChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionPaymentMethodMinAmount(paymentMethodC, minAmount);
                        transType.addAll(transTypem);
                    }
                } else if ((categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo == 0 && minAmount == 0 && maxAmount != 0)) {
                    for (String paymentMethodC : paymentMethodChosen) {

                        transTypem = MainActivity.appDB.daoAccess().getTransactionPaymentMethodMaxAmount(paymentMethodC, maxAmount);
                        transType.addAll(transTypem);
                    }
                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount != 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateAmount(dayFrom, monthFrom,
                            yearFrom, dayTo, monthTo, yearTo, minAmount, maxAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount != 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateFromAmount(dayFrom, monthFrom,
                            yearFrom, minAmount, maxAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount != 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateToAmount( dayTo, monthTo, yearTo, minAmount, maxAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount != 0 && maxAmount == 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateFromMinAmount(dayFrom, monthFrom,
                            yearFrom, minAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount != 0 && maxAmount == 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateToMinAmount(dayTo, monthTo, yearTo, minAmount);
                    transType.addAll(transTypem);
                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount == 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateFromMaxAmount(dayFrom, monthFrom,
                            yearFrom, maxAmount);
                    transType.addAll(transTypem);
                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount == 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateToMaxAmount( dayTo, monthTo, yearTo, maxAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount != 0 && maxAmount == 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateMinAmount(dayFrom, monthFrom,
                            yearFrom, dayTo, monthTo, yearTo, minAmount);
                    transType.addAll(transTypem);

                } else if ((categoryChosen.isEmpty() && paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount == 0 && maxAmount != 0)) {

                    transTypem = MainActivity.appDB.daoAccess().getTransactionDateMaxAmount(dayFrom, monthFrom,
                            yearFrom, dayTo, monthTo, yearTo, maxAmount);
                    transType.addAll(transTypem);

                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount != 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAll(categoryC, paymentMethodC, dayFrom, monthFrom,
                                    yearFrom, dayTo, monthTo, yearTo, minAmount, maxAmount);
                            transType.addAll(transTypem);
                        }

                    }
                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount != 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateFromAmount(categoryC, paymentMethodC, dayFrom, monthFrom,
                                    yearFrom, minAmount, maxAmount);
                            transType.addAll(transTypem);
                        }

                    }
                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount != 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateMinAmount(categoryC, paymentMethodC, dayFrom, monthFrom,
                                    yearFrom, dayTo, monthTo, yearTo, minAmount);
                            transType.addAll(transTypem);
                        }

                    }
                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo != 0 && minAmount == 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateMaxAmount(categoryC, paymentMethodC, dayFrom, monthFrom,
                                    yearFrom, dayTo, monthTo, yearTo, maxAmount);
                            transType.addAll(transTypem);
                        }

                    }

                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount != 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateToAmount(categoryC, paymentMethodC, dayTo, monthTo, yearTo, minAmount, maxAmount);
                            transType.addAll(transTypem);
                        }

                    }
                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount != 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateToMinAmount(categoryC, paymentMethodC, dayTo, monthTo, yearTo, minAmount);
                            transType.addAll(transTypem);
                        }

                    }
                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom == 0 && dayTo != 0 && minAmount == 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateToMaxAmount(categoryC, paymentMethodC,  dayTo, monthTo, yearTo, maxAmount);
                            transType.addAll(transTypem);
                        }

                    }

                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount == 0 && maxAmount != 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateFromMaxAmount(categoryC, paymentMethodC, dayFrom, monthFrom,
                                    yearFrom, maxAmount);
                            transType.addAll(transTypem);
                        }

                    }

                } else if ((!categoryChosen.isEmpty() && !paymentMethodChosen.isEmpty() && dayFrom != 0 && dayTo == 0 && minAmount != 0 && maxAmount == 0)) {
                    for (String categoryC : categoryChosen) {
                        for (String paymentMethodC : paymentMethodChosen) {
                            transTypem = MainActivity.appDB.daoAccess().getTransactionAllDateFromMinAmount(categoryC, paymentMethodC, dayFrom, monthFrom,
                                    yearFrom,  minAmount);
                            transType.addAll(transTypem);
                        }

                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Check the filters", Toast.LENGTH_SHORT).show();
                }

                float sum = 0;
                if (transType != null) {
                    listItems.clear();
                    sum = 0;
                    for (Transactions transaction : transType) {
                        String id = transaction.getUid();
                        String category = transaction.getCategory();
                        Boolean type = transaction.getType();
                        if (!type) {
                            transactionType = "Expense";
                        } else {
                            transactionType = "Income";
                        }
                        int day = transaction.getCreatedDay();
                        int month = transaction.getCreatedMonth();
                        int year = transaction.getCreatedYear();
                        String paymentMet = transaction.getPaymentMethod();
                        String not = transaction.getNote();
                        float amount = transaction.getAmount();
                        String repDuration = transaction.getDuration();
                        String Date = day + "/" + month + "/" + year;
                        int iconDisplay = MainActivity.catDB.daoAccess().getIcon(category);
                        if (transactionType.equals("Expense")) {
                            sum -= amount;
                        } else {
                            sum += amount;
                        }

                        listItems.add(new ListItems(iconDisplay, type,  category,  "Method: " +paymentMet, "" + amount,
                                Date,  "Duration: " +repDuration, not, id));
                    }

                }

                switch (getCurrency()) {
                    case "Euro":
                        currData = " €";
                        break;
                    case "Dollar":
                        currData = " $";
                        break;
                    case "Rupee":
                        currData = " Rupee";
                        break;
                    case "Turkish Lira":
                        currData = " TL";
                        break;
                    default:
                        currData = " €";
                }

                dateData = "";
                dateData += dayFrom + ",";
                dateData += dayTo + ",";
                dateData += monthFrom + ",";
                dateData += monthTo + ",";
                dateData += yearFrom + ",";
                dateData += yearTo + ",";

                Intent report = new Intent(ReportFilter.this, Report.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("reportlist", listItems);
                report.putExtra("Transactions", (Serializable) transType);
                report.putExtra("tb", bundle);
                report.putExtra("dateData", dateData);
                report.putExtra("currData", currData);
                report.putExtra("Total", sum);
                String catString = "";

                Set<String> categoryHelp = new LinkedHashSet<>();
                for (Transactions T : transType) {
                    if (T.getAmount() != 0 && !categoryHelp.contains(T.getCategory())) {
                        categoryHelp.add(T.getCategory());
                    }
                }
                String[] iterator = categoryHelp.toArray(new String[0]);
                for (int i = 0; i < iterator.length; i++) {
                    if (i < iterator.length - 1) {
                        catString += iterator[i] + ",";
                    } else {
                        catString += iterator[i];
                    }
                }

                report.putExtra("categories", catString);
                startActivity(report);
            }

        });

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open, R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.log_expense){

                    Intent intent_LogExpense = new Intent(ReportFilter.this, NewExpense.class);
                    startActivity(intent_LogExpense);
                }
                else if(id == R.id.reports){

                    Intent intent_Reports = new Intent(ReportFilter.this, ReportFilter.class);
                    startActivity(intent_Reports);
                }
                else if(id == R.id.settings){

                    Intent intent_Settings = new Intent(ReportFilter.this, SettingsActivity.class);
                    startActivity(intent_Settings);
                }
                else if(id == R.id.sendEmail){

                    Intent intent_sendEmail = new Intent(ReportFilter.this, SendEmail.class);
                    startActivity(intent_sendEmail);
                }

                return true;

            }


        });


        try {
            //Toolbar toolbar = findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        } catch (NullPointerException npe){
            npe.printStackTrace();
            Toast.makeText(this, "Something wrong with the toolbar", Toast.LENGTH_SHORT).show();
        }

    }

    private String[] getCategories() {
        List<String> categories = MainActivity.catDB.daoAccess().getCategoriesWithName();
        String[] arrayCat = new String[categories.size()];
        int i = 0;
        for (String cate : categories) {
            arrayCat[i] = cate;
            i++;
        }
        return arrayCat;
    }

    private String getCurrency() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(MainActivity.CURRENCY, "Euro");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}