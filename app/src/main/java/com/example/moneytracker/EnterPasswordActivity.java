package com.example.moneytracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPasswordActivity extends AppCompatActivity {

    Button login;
    EditText enteredPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean enable_pin_flag = shared_preferences.getBoolean("EnablePin",false);

        if (!enable_pin_flag){
            Intent launch_MainActivity = new Intent(EnterPasswordActivity.this,NewExpense.class);
            startActivity(launch_MainActivity);
            finish();
        }

        login = findViewById(R.id.login_button);
        enteredPin = findViewById(R.id.entered_pin);
        final String set_pin = shared_preferences.getString("SetPin","");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enteredPin.getText().toString().equals(set_pin)){
                    Intent launch_MainActivity = new Intent(EnterPasswordActivity.this,NewExpense.class);
                    startActivity(launch_MainActivity);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),R.string.message_incorrect_pin,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}