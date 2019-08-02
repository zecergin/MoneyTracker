package com.example.moneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Database.Transactions;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ListAdapter mAdapter;
    private TextView TextAmount;
    private float total;
    String currency;

    View view;
    private ArrayList<ListItems> listItems = new ArrayList<>();

    public ListFragment(){
        //Constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        TextAmount = view.findViewById(R.id.totalValue);
        TextAmount.setText("total:  " + String.format("%.2f", total) + " " + currency);
        buildRecyclerView(listItems);
        return view;
    }

    public void setListItems (ArrayList<ListItems> listItems) {
        this.listItems = listItems;
    }

    public void setTotal(float sum) {
        this.total = sum;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void removeItem(int position) {
        Transactions toDelete;
        String delete = listItems.get(position).getmId();
        toDelete = MainActivity.appDB.daoAccess().getTransactions(delete);
        //int tranID = toDelete.getRecurrentJob();
        //cancelJob(tranID);
        MainActivity.appDB.daoAccess().deleteTask(toDelete);
        listItems.remove(position);
        mAdapter.notifyItemRemoved(position);

        Toast.makeText(view.getContext(), "Transaction deleted", Toast.LENGTH_LONG).show();
        //onRestart();
    }

    public void buildRecyclerView(ArrayList<ListItems> reportList) {
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ListAdapter(listItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    /*public void cancelJob(int tranid) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity().getApplicationContext(), RecurrentAlarm.class);
        intent.putExtra("Id", tranid);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), tranid, intent, 0);
        alarmManager.cancel(pendingIntent);
    }*/

}