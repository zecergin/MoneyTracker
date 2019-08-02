package com.example.moneytracker;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.moneytracker.Database.CatDatabase;
import com.example.moneytracker.Database.Category;
import com.example.moneytracker.Database.TrnDatabase;

import java.util.List;

public class LoggedExpense extends AppCompatActivity {

    private static final int LOGGED_EXPENSE_ACTIVITY_REQUEST_CODE = 1;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.appDB = Room.databaseBuilder(getApplicationContext(), TrnDatabase.class,"TransactionsDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        MainActivity.catDB = Room.databaseBuilder(getApplicationContext(), CatDatabase.class,"CategoriesDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        String currencySign;
        currencySign = "Euro";

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

        setContentView(R.layout.activity_logged_expense);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggedExpense.this, NewExpense.class);
                startActivityForResult(intent, LOGGED_EXPENSE_ACTIVITY_REQUEST_CODE);
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

                    Intent intent_LogExpense = new Intent(LoggedExpense.this, NewExpense.class);
                    startActivity(intent_LogExpense);
                }
                else if(id == R.id.reports){

                    Intent intent_Reports = new Intent(LoggedExpense.this, ReportFilter.class);
                    startActivity(intent_Reports);
                }
                else if(id == R.id.settings){

                    Intent intent_Settings = new Intent(LoggedExpense.this, SettingsActivity.class);
                    startActivity(intent_Settings);
                }
                else if(id == R.id.sendEmail){

                    Intent intent_sendEmail = new Intent(LoggedExpense.this, SendEmail.class);
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

