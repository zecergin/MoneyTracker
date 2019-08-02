package com.example.moneytracker;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.moneytracker.Database.CatDatabase;
import com.example.moneytracker.Database.Category;
import com.example.moneytracker.Database.TrnDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static TrnDatabase appDB;
    public static CatDatabase catDB;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CATEGORIES = "categories";
    public static final String CURRENCY = "currency";
    public static final String AUTHENTICATION = "authentication";
    public static final String PASSWORD = "password";
    public static final String JOBID = "jobId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


    }
}