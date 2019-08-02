package com.example.moneytracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import static com.example.moneytracker.MainActivity.CURRENCY;

public class ChangeCurrency extends AppCompatActivity {
    Spinner Currency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_currency);

        Currency = findViewById(R.id.currency_choose);

        final String currency[]={"Euro","Dollar","Rupee", "Turkish Lira"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,currency);

        Currency.setAdapter(adapter1);

        Button saveBtn = findViewById(R.id.save_button_currency);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String mCurrency = Currency.getSelectedItem().toString();
                editor.putString(CURRENCY, mCurrency);
                editor.apply();

                Intent intent_newExpense = new Intent(ChangeCurrency.this, NewExpense.class);
                startActivity(intent_newExpense);

            }
        });

    }
}