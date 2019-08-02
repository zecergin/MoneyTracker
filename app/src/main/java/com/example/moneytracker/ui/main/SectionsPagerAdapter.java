package com.example.moneytracker.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moneytracker.Database.Transactions;
import com.example.moneytracker.GraphFragment;
import com.example.moneytracker.ListFragment;
import com.example.moneytracker.ListItems;
import com.example.moneytracker.PieGraphFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private ArrayList<ListItems> mListItems;
    private String[] datData;
    private String[] catData;
    private String currData;
    private float total;
    private List<Transactions> transactions;

    /*@StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_bar_graph, R.string.tab_pie_graph, R.string.tab_list};
    private final Context mContext;
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }*/

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<ListItems> listItems, String dateData, String categories,
                                String currData, float totalSum, List<Transactions> transactions) {
        super(fm);
        mListItems = listItems;
        datData = dateData.split(",");
        catData = categories.split(",");
        this.currData = currData;
        this.total = totalSum;
        this.transactions = transactions;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                GraphFragment barGraphFragment = new GraphFragment();
                barGraphFragment.dateData = this.datData;
                barGraphFragment.currData = this.currData;
                barGraphFragment.setTransactionList(transactions);
                return barGraphFragment;
            case 1:
                PieGraphFragment pieGraphFragment = new PieGraphFragment();
                pieGraphFragment.dateData = this.datData;
                pieGraphFragment.catData = this.catData;
                pieGraphFragment.currData = this.currData;
                pieGraphFragment.setTransactionList(transactions);
                return pieGraphFragment;
            case 2:
                ListFragment listFragment = new ListFragment();
                listFragment.setTotal(total);
                listFragment.setListItems(mListItems);
                listFragment.setCurrency(currData);
                return listFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "BAR GRAPH";
            case 1:
                return "PIE GRAPH";
            case 2:
                return "LIST";
        }
        return null;
    }

    /*@Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }*/

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}