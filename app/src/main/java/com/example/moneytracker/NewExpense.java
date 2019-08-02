package com.example.moneytracker;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Database.CatDatabase;
import com.example.moneytracker.Database.Category;
import com.example.moneytracker.Database.Transactions;
import com.example.moneytracker.Database.TrnDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class NewExpense extends AppCompatActivity {

    Spinner category,paymentMethod, repetitiveSpinner;
    EditText amount, note;
    Boolean repetitiveValue = false;
    Boolean incomeValue = false;

    private static final String TAG ="NewExpense";
    private TextView nDisplayDate;
    private DatePickerDialog.OnDateSetListener nDateSetListener;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    int mYear, mMonth, mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        MainActivity.appDB = Room.databaseBuilder(getApplicationContext(), TrnDatabase.class,"TransactionsDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        MainActivity.catDB = Room.databaseBuilder(getApplicationContext(), CatDatabase.class,"CategoriesDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        List<Category> categories = MainActivity.catDB.daoAccess().getAllCategories();
        if (categories.size() == 0){
            Category c = new Category();
            String[] Cat = getResources().getStringArray(R.array.categoryList);
            for (int i=0; i<Cat.length; i++){
                c.setCategoryName(Cat[i]);
                // c.setIcon("ic_grocery_image");
                MainActivity.catDB.daoAccess().addCategory(c);
            }
        }

        amount = findViewById(R.id.amount_new_add);
        note = findViewById(R.id.note_new_add);

        repetitiveSpinner =findViewById(R.id.repetitiveSpinner_new_add);
        repetitiveSpinner.setEnabled(false);

        TextView durationText = findViewById(R.id.duration_text_new_add);
        durationText.setEnabled(false);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        nDisplayDate = findViewById(R.id.date_new_add);

// this is the date picker dialog box code

        nDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(NewExpense.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        nDateSetListener, year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        nDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int chosenYear, int chosenMonth, int chosenDay) {

                Log.d(TAG, "onDateSet : date: " + chosenDay + "/" + (chosenMonth+1) + "/" + chosenYear);

                mYear = chosenYear;
                mMonth = chosenMonth+1;
                mDay = chosenDay;

                String date = mDay + "/" + mMonth + "/" + mYear;

                nDisplayDate.setText(date);
            }
        };


        final Switch incomeSwitch = findViewById(R.id.income_new_add);

        incomeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!incomeSwitch.isChecked()){
                    incomeValue = false;
                } else {
                    incomeValue = true;
                }
            }
        });

        final Switch repetitiveSwitch = findViewById(R.id.repetitive_new_add);

        repetitiveSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!repetitiveSwitch.isChecked() ) {
                    Spinner repetitiveSpinner = findViewById(R.id.repetitiveSpinner_new_add);
                    repetitiveSpinner.setEnabled(false);

                    TextView durationText = findViewById(R.id.duration_text_new_add);
                    durationText.setEnabled(false);

                    repetitiveValue = false;

                } else {
                    Spinner repetitiveSpinner = findViewById(R.id.repetitiveSpinner_new_add);
                    repetitiveSpinner.setEnabled(true);

                    TextView durationText = findViewById(R.id.duration_text_new_add);

                    durationText.setEnabled(true);
                    repetitiveValue = true;
                }
            }
        });

        category = findViewById(R.id.category1_new_add);
        paymentMethod =findViewById(R.id.paymentMethod_new_add);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.paymentMethodList, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.repetitiveDuration, android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(category_refresh());
        paymentMethod.setAdapter(adapter2);
        repetitiveSpinner.setAdapter(adapter3);

        Button cancel_button = findViewById(R.id.cancel_button_new_add);
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent_LogExpense = new Intent(NewExpense.this, NewExpense.class);
                startActivity(intent_LogExpense);

            }
        });


        Button save_button = findViewById(R.id.save_button_category_new_add);
        save_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String uid = UUID.randomUUID().toString();
                String mPaymentMethod = paymentMethod.getSelectedItem().toString();
                String mCategory = category.getSelectedItem().toString();
                String mRepetitiveDuration = repetitiveSpinner.getSelectedItem().toString();
                String mNote = note.getText().toString();
                boolean mRepetitive =repetitiveSwitch.isChecked();
                boolean mIncome = incomeSwitch.isChecked();

                if(amount.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter the amount", Toast.LENGTH_SHORT).show();
                } else if(mDay == 0){
                    Toast.makeText(getApplicationContext(),"Choose the date", Toast.LENGTH_SHORT).show();
                } else {
                    int mAmount = Integer.parseInt(amount.getText().toString());
                    Transactions transactions = new Transactions();
                    transactions.setUid(uid);
                    transactions.setAmount(mAmount);
                    transactions.setCreatedDay(mDay);
                    transactions.setCreatedMonth(mMonth);
                    transactions.setCreatedYear(mYear);
                    transactions.setPaymentMethod(mPaymentMethod);
                    transactions.setDuration(mRepetitiveDuration);
                    if(mIncome){
                        transactions.setType(true);
                    } else {
                        transactions.setType(false);}
                    transactions.setCategory(mCategory);
                    transactions.setNote(mNote);
                    if(mRepetitive){
                        transactions.setRepetitive(true);
                    } else {
                        transactions.setRepetitive(false);}

                    MainActivity.appDB.daoAccess().insert(transactions);

                    Toast.makeText(getApplicationContext(), "Saved.", Toast.LENGTH_SHORT).show();

                    finish();
                    //Intent intent_newExpense = new Intent(NewExpense.this, NewExpense.class);
                    //startActivity(intent_newExpense);
                }
                amount.setText("");

            }
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open, R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.log_expense){

                    Intent intent_LogExpense = new Intent(NewExpense.this, NewExpense.class);
                    startActivity(intent_LogExpense);
                }
                else if(id == R.id.reports){

                    Intent intent_Reports = new Intent(NewExpense.this, ReportFilter.class);
                    startActivity(intent_Reports);
                }
                else if(id == R.id.settings){

                    Intent intent_Settings = new Intent(NewExpense.this, SettingsActivity.class);
                    startActivity(intent_Settings);
                }

                else if(id == R.id.sendEmail){

                    Intent intent_sendEmail = new Intent(NewExpense.this, SendEmail.class);
                    startActivity(intent_sendEmail);
                }

                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private ArrayAdapter<String> category_refresh(){
        List<Category> categories = null;
        categories = MainActivity.catDB.daoAccess().getAllCategories();
        Spinner category = findViewById(R.id.category3_add);

        List<String> list = new ArrayList<String>();
        for(Category c : categories){
            list.add(c.getCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }
}