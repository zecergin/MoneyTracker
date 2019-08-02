package com.example.moneytracker.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity
public class Transactions implements Serializable {

    @NonNull
    @PrimaryKey
    private String uid;

    @ColumnInfo(name = "amount")
    private int amount;

    //@ColumnInfo(name = "currency")
    //private String currency;

    @ColumnInfo(name = "created_day")
    private int createdDay;

    @ColumnInfo(name = "created_month")
    private int createdMonth;

    @ColumnInfo(name = "created_year")
    private int createdYear;

    @ColumnInfo(name = "payment_method")
    private String paymentMethod;

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "type")
    private Boolean type;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "repetitive")
    private Boolean repetitive;

    public Transactions() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public int getAmount() {  return amount;  }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    //public String getCurrency() {return currency; }

    //public void setCurrency(String currency) {this.currency = currency;}


    public int getCreatedDay() {
        return createdDay;
    }

    public void setCreatedDay(int createdDay) {
        this.createdDay = createdDay;
    }

    public int getCreatedMonth() {return createdMonth; }

    public void setCreatedMonth(int createdMonth) {this.createdMonth = createdMonth; }

    public int getCreatedYear() {return createdYear; }

    public void setCreatedYear(int createdYear) {this.createdYear = createdYear; }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getType() {return type;}

    public void setType(Boolean type) {this.type = type; }

    public String getCategory() {return category; }

    public void setCategory(String category) { this.category = category; }

    public String getNote() { return note; }

    public void setNote(String note) {this.note = note;  }

    public Boolean getRepetitive() { return repetitive;  }

    public void setRepetitive(Boolean repetitive) {this.repetitive = repetitive;  }


    public Transactions(String uid, int amount, int createdDay, int createdMonth, int createdYear, String paymentMethod,
                        String duration, Boolean type, String category, String note, Boolean repetitive){
        this.uid = uid;
        this.amount = amount;
        this.createdDay = createdDay;
        this.createdMonth = createdMonth;
        this.createdYear = createdYear;
        this.paymentMethod = paymentMethod;
        this.duration = duration;
        this.type = type;
        this.category = category;
        this.note = note;
        this.repetitive = repetitive;
    }

}
