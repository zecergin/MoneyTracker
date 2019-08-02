package com.example.moneytracker;

import android.content.Intent;
import android.icu.util.Currency;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.moneytracker.Database.Transactions;
import com.example.moneytracker.Database.TrnDatabase;

import java.util.List;

public class SendEmail extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //declare views
    EditText mRecipientEt, mSubjectEt, mMessageEt;
    Button mSendEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        //initializing views with activity_send_email.xml

        mRecipientEt = findViewById(R.id.recipientEt);
        mMessageEt = findViewById(R.id.messageEt);
        mSubjectEt = findViewById(R.id.subjectEt);
        mSendEmailBtn = findViewById(R.id.sendEmailBtn);

        //button click to get i/p and call sendEmail method

        mSendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get i/p from EditTexts and save in variables
                String recipient = mRecipientEt.getText().toString().trim();
                String subject = mSubjectEt.getText().toString().trim();
                String message = mMessageEt.getText().toString().trim();
                String mType = "Expense";
                float mAmount=0;
                List<Transactions> transType;
                mAmount=0;
                transType = MainActivity.appDB.daoAccess().getAllTransactions();
                String Info="";
                float sumExpenses = 0;
                for(Transactions transactions:transType){
                    String id = transactions.getUid();
                    String method = transactions.getPaymentMethod();
                    String categ = transactions.getCategory();
                    int day = transactions.getCreatedDay();
                    int month = transactions.getCreatedMonth();
                    int year = transactions.getCreatedYear();
                    String comment = transactions.getNote();
                    Boolean type = transactions.getType();
                    float amount = transactions.getAmount();
                    String duration = transactions.getDuration();
                    Boolean repetitive = transactions.getRepetitive();
                    String Type_S;
                    String Repetetive_B;

                    sumExpenses += amount;
                    if(type == true) { Type_S ="\tIncome";}  else {Type_S ="\tExpense";}
                    if(repetitive == true) {Repetetive_B ="\tYes";} else {Repetetive_B="\tNo";}
                    Info += "\n\n" + "Id: " + id + "\n Type: " + Type_S + "\n Category: " + categ + "\n Date: " + day + "." + month + "." + year +
                            "\n Method: " + method + "\n Comment: " + comment + "\n Amount: " + amount + "\n Repetitive:" + Repetetive_B  + "\n Duration:" + duration  ;
                    //currency


                }

                List<Transactions> income = MainActivity.appDB.daoAccess().getTransaction(true);

                float sumIncome = 0, total;

                for (Transactions transactions: income){
                    sumIncome+= transactions.getAmount();
                }

                total = sumIncome-sumExpenses;

                Info+="\n Initial Balance:" + sumIncome ;
                Info += "\nTOTAL EXPENSES: " + sumExpenses;
                Info += "\nCURRENT BALANCE: " + total;
                message+=Info;
                //method call fro email intent with the inputs as parameters

                sendEmail(recipient,subject,message,Info);
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

                    Intent intent_LogExpense = new Intent(SendEmail.this, NewExpense.class);
                    startActivity(intent_LogExpense);
                }
                else if(id == R.id.reports){

                    Intent intent_Reports = new Intent(SendEmail.this, ReportFilter.class);
                    startActivity(intent_Reports);
                }
                else if(id == R.id.settings){

                    Intent intent_Settings = new Intent(SendEmail.this, SettingsActivity.class);
                    startActivity(intent_Settings);
                }

                else if(id == R.id.sendEmail){

                    Intent intent_sendEmail = new Intent(SendEmail.this, SendEmail.class);
                    startActivity(intent_sendEmail);
                }

                return true;

            }
        });


    }

    private void sendEmail(String recipient, String subject, String message, String info) {
        /*Action_Send action to launch an email client installed on your Android device*/
        Intent mEmailIntent = new Intent(Intent.ACTION_SEND);
        mEmailIntent.setData(Uri.parse("mailto:"));
        mEmailIntent.setType("text/plain");
        //put recipient//
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        // put subject of email
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        //put message of email
        mEmailIntent.putExtra(Intent.EXTRA_TEXT,message);

        try{
            //no error
            startActivity(Intent.createChooser(mEmailIntent,"Choose an Email CLient"));
        }
        catch (Exception e){

            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}