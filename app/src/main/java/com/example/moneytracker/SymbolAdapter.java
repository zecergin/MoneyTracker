package com.example.moneytracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class SymbolAdapter extends ArrayAdapter <SymbolItem>{
    public SymbolAdapter(Context context, ArrayList<SymbolItem> symbolList){
        super(context, 0,symbolList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView,ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.symbol,parent,false);
        }
        ImageView imageViewSymbol = convertView.findViewById(R.id.symbol);

        SymbolItem currentItem =getItem(position);

        if(currentItem!=null) {

            imageViewSymbol.setImageResource(currentItem.getmSymbolIcon());
        }
        return convertView;


    }
}