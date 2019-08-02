package com.example.moneytracker.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TrnDaoAccess {

    @Insert
    void insert(Transactions transactions);

    @Update
    void updateTask(Transactions transactions);

    @Delete
    void deleteTask(Transactions transactions);


    @Query("select * from Transactions")
    public List<Transactions> getAllTransactions();

    @Query("select * from Transactions where payment_method = :paymentMethodC ")
    List<Transactions>getTransactionPaymentMethod(String paymentMethodC);

    @Query("select * from Transactions where category = :categoryC ")
    List<Transactions>getTransactionCategory(String categoryC);

    @Query("select * from Transactions where created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo ")
    List<Transactions>getTransactionDate(int dayFrom, int monthFrom, int yearFrom, int dayTo, int monthTo, int yearTo);

    @Query("select * from Transactions where created_day >= :dayFrom and created_month >= :monthFrom and created_year >= :yearFrom ")
    List<Transactions>getTransactionDateFrom(int dayFrom, int monthFrom, int yearFrom);

    @Query("select * from Transactions where created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo ")
    List<Transactions>getTransactionDateTo(int dayTo, int monthTo, int yearTo);

    @Query("select * from Transactions where amount between :minAmount and :maxAmount ")
    List<Transactions>getTransactionAmount(int minAmount, int maxAmount);

    @Query("select * from Transactions where amount >= :minAmount ")
    List<Transactions>getTransactionMinAmount(int minAmount);

    @Query("select * from Transactions where amount <= :maxAmount ")
    List<Transactions>getTransactionMaxAmount(int maxAmount);

    @Query("select * from Transactions where category = :categoryC and payment_method=:paymentMethodC")
    List<Transactions>getTransactionCategoryPaymentMethod(String categoryC, String paymentMethodC);

    @Query("select * from Transactions where category=:categoryC and created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo ")
    List<Transactions>getTransactionCategoryDate(String categoryC, int dayFrom, int monthFrom, int yearFrom, int dayTo, int monthTo, int yearTo);

    @Query("select * from Transactions where category=:categoryC and created_day >= :dayFrom and created_month >= :monthFrom and created_year >= :yearFrom ")
    List<Transactions>getTransactionCategoryDateFrom(String categoryC, int dayFrom, int monthFrom, int yearFrom);

    @Query("select * from Transactions where category=:categoryC and created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo ")
    List<Transactions>getTransactionCategoryDateTo(String categoryC, int dayTo, int monthTo, int yearTo);

    @Query("select * from Transactions where category=:categoryC and amount between :minAmount and :maxAmount ")
    List<Transactions>getTransactionCategoryAmount(String categoryC, int minAmount, int maxAmount);

    @Query("select * from Transactions where category=:categoryC and amount >= :minAmount ")
    List<Transactions>getTransactionCategoryMinAmount(String categoryC, int minAmount);

    @Query("select * from Transactions where category=:categoryC and amount <= :maxAmount ")
    List<Transactions>getTransactionCategoryMaxAmount(String categoryC, int maxAmount);

    @Query("select * from Transactions where payment_method=:paymentMethodC and created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo ")
    List<Transactions>getTransactionPaymentMethodDate(String paymentMethodC, int dayFrom, int monthFrom, int yearFrom, int dayTo, int monthTo, int yearTo);

    @Query("select * from Transactions where payment_method=:paymentMethodC and created_day >= :dayFrom and created_month >= :monthFrom and created_year >= :yearFrom ")
    List<Transactions>getTransactionPaymentMethodDateFrom(String paymentMethodC, int dayFrom, int monthFrom, int yearFrom);

    @Query("select * from Transactions where payment_method=:paymentMethodC and created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo ")
    List<Transactions>getTransactionPaymentMethodDateTo(String paymentMethodC, int dayTo, int monthTo, int yearTo);

    @Query("select * from Transactions where payment_method=:paymentMethodC and amount between :minAmount and :maxAmount")
    List<Transactions>getTransactionPaymentMethodAmount(String paymentMethodC, int minAmount, int maxAmount);

    @Query("select * from Transactions where payment_method=:paymentMethodC and amount >= :minAmount ")
    List<Transactions>getTransactionPaymentMethodMinAmount(String paymentMethodC, int minAmount);

    @Query("select * from Transactions where payment_method=:paymentMethodC and amount <= :maxAmount ")
    List<Transactions>getTransactionPaymentMethodMaxAmount(String paymentMethodC, int maxAmount);

    @Query("select * from Transactions where created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo  and amount between :minAmount and :maxAmount")
    List<Transactions>getTransactionDateAmount(int dayFrom, int monthFrom, int yearFrom, int dayTo, int monthTo, int yearTo, int minAmount, int maxAmount);

    @Query("select * from Transactions where created_day >= :dayFrom  and created_month >= :monthFrom and created_year >= :yearFrom  and amount between :minAmount and :maxAmount")
    List<Transactions>getTransactionDateFromAmount(int dayFrom, int monthFrom,
                                                   int yearFrom, int minAmount, int maxAmount);

    @Query("select * from Transactions where created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo  and amount between :minAmount and :maxAmount")
    List<Transactions>getTransactionDateToAmount( int dayTo, int monthTo, int yearTo, int minAmount, int maxAmount);

    @Query("select * from Transactions where created_day >= :dayFrom  and created_month >= :monthFrom and created_year >= :yearFrom  and amount >= :minAmount ")
    List<Transactions>getTransactionDateFromMinAmount(int dayFrom, int monthFrom,
                                                      int yearFrom, int minAmount);

    @Query("select * from Transactions where created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo  and amount >= :minAmount ")
    List<Transactions>getTransactionDateToMinAmount(int dayTo, int monthTo, int yearTo, int minAmount);

    @Query("select * from Transactions where created_day >= :dayFrom  and created_month >= :monthFrom and created_year >= :yearFrom  and amount <= :maxAmount")
    List<Transactions>getTransactionDateFromMaxAmount(int dayFrom, int monthFrom,
                                                      int yearFrom, int maxAmount);

    @Query("select * from Transactions where created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo  and amount >= :maxAmount ")
    List<Transactions>getTransactionDateToMaxAmount( int dayTo, int monthTo, int yearTo, int maxAmount);

    @Query("select * from Transactions where created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo  and amount >= :minAmount ")
    List<Transactions>getTransactionDateMinAmount(int dayFrom, int monthFrom,
                                                  int yearFrom, int dayTo, int monthTo, int yearTo, int minAmount);

    @Query("select * from Transactions where created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo  and amount <= :maxAmount ")
    List<Transactions>getTransactionDateMaxAmount(int dayFrom, int monthFrom,
                                                  int yearFrom, int dayTo, int monthTo, int yearTo, int maxAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day >= :dayFrom  and created_month >= :monthFrom and created_year >= :yearFrom  and amount between :minAmount and :maxAmount")
    List<Transactions>getTransactionAllDateFromAmount(String categoryC, String paymentMethodC, int dayFrom,int  monthFrom,
                                                      int yearFrom, int minAmount, int maxAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo  and amount >= :minAmount ")
    List<Transactions>getTransactionAllDateMinAmount(String categoryC, String paymentMethodC, int dayFrom,int  monthFrom,
                                                     int yearFrom, int dayTo, int monthTo, int yearTo, int minAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo  and amount <= :maxAmount ")
    List<Transactions>getTransactionAllDateMaxAmount(String categoryC, String paymentMethodC, int dayFrom,int  monthFrom,
                                                     int yearFrom, int dayTo, int monthTo, int yearTo, int maxAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day  <= :dayTo and created_month <= :monthTo and created_year <= :yearTo  and amount between :minAmount and :maxAmount")
    List<Transactions>getTransactionAllDateToAmount(String categoryC, String paymentMethodC, int dayTo, int monthTo, int yearTo,int minAmount,int maxAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo  and amount >= :minAmount ")
    List<Transactions>getTransactionAllDateToMinAmount(String categoryC, String paymentMethodC, int dayTo, int monthTo, int yearTo,int minAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day <= :dayTo and created_month <= :monthTo and created_year <= :yearTo  and amount <= :maxAmount ")
    List<Transactions>getTransactionAllDateToMaxAmount(String categoryC, String paymentMethodC, int dayTo, int monthTo, int yearTo,int maxAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day >= :dayFrom  and created_month >= :monthFrom and created_year >= :yearFrom  and amount <= :maxAmount")
    List<Transactions>getTransactionAllDateFromMaxAmount(String categoryC, String paymentMethodC, int dayFrom,int monthFrom,
                                                         int yearFrom, int maxAmount);

    @Query("select * from Transactions where category=:categoryC and payment_method=:paymentMethodC and created_day >= :dayFrom  and created_month >= :monthFrom and created_year >= :yearFrom  and amount >= :minAmount")
    List<Transactions>getTransactionAllDateFromMinAmount(String categoryC, String paymentMethodC, int dayFrom,int monthFrom,
                                                         int yearFrom, int  minAmount);

    @Query("select * from Transactions where category = :categoryC and payment_method=:paymentMethodC and created_day between :dayFrom and :dayTo and created_month between :monthFrom and :monthTo and created_year between :yearFrom and :yearTo  and amount between :minAmount and :maxAmount")
    List<Transactions>getTransactionAll(String categoryC, String paymentMethodC, int dayFrom, int monthFrom, int yearFrom, int dayTo, int monthTo, int yearTo, int minAmount, int maxAmount);

    @Query("SELECT * FROM Transactions WHERE uid =:taskId")
    Transactions getTransactions(String taskId);

    @Query("SELECT * FROM Transactions WHERE type=:mType")
    List<Transactions> getTransaction(Boolean mType);

}
