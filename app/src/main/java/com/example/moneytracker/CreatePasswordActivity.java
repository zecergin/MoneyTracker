package com.example.moneytracker;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePasswordActivity extends AppCompatActivity {

    Button save_btn, cancel_btn;
    EditText confirm_to_modify_pin, new_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        save_btn = findViewById(R.id.save_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        confirm_to_modify_pin = findViewById(R.id.confirm_to_modify_pin);
        new_pin = findViewById(R.id.new_pin);

        save_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String new_pin_value = new_pin.getText().toString();
                String confirm_to_modify_pin_value = confirm_to_modify_pin.getText().toString();
                if (confirm_to_modify_pin_value.equals(new_pin_value)){
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("SetPin",new_pin_value);
                    editor.putBoolean("EnablePin",true);
                    editor.apply();
                    if (SettingsActivity.switchPreference  != null){
                        SettingsActivity.switchPreference.setChecked(true);
                    }
                    CreatePasswordActivity.this.finish();
                } else {
                    Toast.makeText(CreatePasswordActivity.this, "Entered PINs do not match", Toast.LENGTH_LONG).show();
                }

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePasswordActivity.this.finish();
            }
        });

    }
}