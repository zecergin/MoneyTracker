package com.example.moneytracker.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CatDaoAccess {

    @Insert
    void insert(Category category);

    @Update
    void updateTask(Category category);

    @Delete
    void deleteTask(Category category);

    @Query("SELECT * FROM Category ")
    List<Category> getAllCategories();

    @Query("SELECT category_name FROM Category ")
    List<String> getCategoriesWithName();

    @Query("select icon from Category where category_name = :catName")
    int getIcon(String catName);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void addCategory(Category categories);




}
