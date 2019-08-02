package com.example.moneytracker;

import java.io.Serializable;

public class ListItems implements Serializable{
    private int mImageResource;
    private String  mCategory, mPaymentMethod, mAmount, mDate, mDuration, mNote, mId;
    private Boolean mType;

    public ListItems(int imageResource, Boolean type, String category, String paymentMethod, String amount, String date,
                     String duration, String note, String Id){
        mImageResource = imageResource;
        mType = type;
        mAmount = amount;
        mCategory = category;
        mPaymentMethod = paymentMethod;
        mDate = date;
        mDuration = duration;
        mNote = note;
        mId = Id;
    }

    public String getPaymentMethod() {
        return mPaymentMethod;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getDate() {
        return mDate;
    }

    public String getDuration() {
        return mDuration;
    }

    public String getNote() {
        return mNote;
    }

    public boolean getType() {
        return mType;
    }

    public String getCategory() {
        return mCategory;
    }

    public int getImageResource() {
        return mImageResource;
    }

    @Override
    public String toString() {
        return "ListItems{" +
                "mImageResource=" + mImageResource +
                ", mType='" + mType + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mPaymentMethod='" + mPaymentMethod + '\'' +
                ", mAmount='" + mAmount + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mDuration='" + mDuration + '\'' +
                ", mNote='" + mNote + '\'' +
                ", mId='" + mId + '\'' +
                '}';
    }
}