package com.example.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneytracker.Database.Transactions;
import com.example.moneytracker.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {

    Spinner category3,enterPaymentMethod,currency2;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    // private static final String TAG = "GraphFragment";
    // private TextView mDisplayDate;
    //  private DatePickerDialog.OnDateSetListener mDateSetListener;

    private String dateData, categories, currData;
    private float total;
    private List<Transactions> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Bundle b = getIntent().getBundleExtra("tb");
        ArrayList<ListItems> listItems = (ArrayList<ListItems>) b.getSerializable("reportlist");
        dateData = getIntent().getStringExtra("dateData");
        categories = getIntent().getStringExtra("categories");
        currData = getIntent().getStringExtra("currData");
        total = getIntent().getFloatExtra("Total", 0);
        transactions = (List<Transactions>) getIntent().getSerializableExtra("Transactions");


        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), listItems, dateData, categories, currData, total, transactions);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open, R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.log_expense){

                    Intent intent_LogExpense = new Intent(Report.this, NewExpense.class);
                    startActivity(intent_LogExpense);
                }
                else if(id == R.id.reports){

                    Intent intent_Reports = new Intent(Report.this, ReportFilter.class);
                    startActivity(intent_Reports);
                }
                else if(id == R.id.settings){

                    Intent intent_Settings = new Intent(Report.this, SettingsActivity.class);
                    startActivity(intent_Settings);
                }
                else if(id == R.id.sendEmail){

                    Intent intent_sendEmail = new Intent(Report.this, SendEmail.class);
                    startActivity(intent_sendEmail);
                }

                return true;

            }


        });


        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        } catch (NullPointerException npe){
            npe.printStackTrace();
            Toast.makeText(this, "Something wrong with the toolbar", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }






}