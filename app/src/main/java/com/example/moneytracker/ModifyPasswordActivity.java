package com.example.moneytracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyPasswordActivity extends AppCompatActivity {

    Button modify_cancel_btn;
    Button modify_save_btn;
    EditText confirm_to_modify_pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        modify_save_btn = findViewById(R.id.save_btn);
        modify_cancel_btn = findViewById(R.id.cancel_btn);
        confirm_to_modify_pin = findViewById(R.id.confirm_to_modify_pin);
        modify_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered_pin_value = confirm_to_modify_pin.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String set_pin = sharedPreferences.getString("SetPin","");
                if (entered_pin_value.equals(set_pin)){
                    Intent showModifyDialog = new Intent(ModifyPasswordActivity.this,CreatePasswordActivity.class);
                    startActivity(showModifyDialog);
                    ModifyPasswordActivity.this.finish();
                } else {
                    Toast.makeText(ModifyPasswordActivity.this, "Incorrect Pin", Toast.LENGTH_LONG).show();
                }

            }
        });

        modify_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPasswordActivity.this.finish();
            }
        });

    }
}