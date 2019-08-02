package com.example.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Navigation extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

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

                    Intent intent_LogExpense = new Intent(Navigation.this, NewExpense.class);
                    startActivity(intent_LogExpense);
                }
                else if(id == R.id.reports){

                    Intent intent_Reports = new Intent(Navigation.this, ReportFilter.class);
                    startActivity(intent_Reports);
                }
                else if(id == R.id.settings){

                    Intent intent_Settings = new Intent(Navigation.this, SettingsActivity.class);
                    startActivity(intent_Settings);
                }

                else if(id == R.id.sendEmail){

                    Intent intent_sendEmail = new Intent(Navigation.this, SendEmail.class);
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
}