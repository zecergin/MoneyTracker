package com.example.moneytracker.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Category.class}, version = 20)
public abstract class CatDatabase extends RoomDatabase {

    public abstract CatDaoAccess daoAccess();

}