package com.example.moneytracker.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Transactions.class}, version = 30)
public abstract class TrnDatabase extends RoomDatabase {

    public abstract TrnDaoAccess daoAccess();

}