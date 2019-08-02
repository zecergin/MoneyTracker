package com.example.moneytracker.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Category {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "category_name")
    private String categoryName;

    @ColumnInfo(name = "icon")
    private int icon;

    public int getCid() {return cid; }

    public void setCid(int cid) {this.cid = cid; }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Category(){

    }

    public Category( int cid, String categoryName, int icon){
        this.cid = cid;
        this.categoryName = categoryName;
        this.icon = icon;
    }

}