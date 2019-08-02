package com.example.moneytracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    private ArrayList<ListItems> mListItems;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView, mDeleteImage, mEditImage;
        public TextView amountTextList;
        public TextView type_text_list;
        public TextView date_text_list;
        public TextView category_text_list;
        public TextView payMet_text_list;
        public TextView duration_text_list;
        public TextView note_text_list;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mDeleteImage = itemView.findViewById(R.id.ivRowDelete_list);
            mEditImage = itemView.findViewById(R.id.ivRowEdit);

            amountTextList = itemView.findViewById(R.id.amount_text_list);
            type_text_list = itemView.findViewById(R.id.type_text_list);
            date_text_list = itemView.findViewById(R.id.date_text_list);
            category_text_list = itemView.findViewById(R.id.category_text_list);
            payMet_text_list = itemView.findViewById(R.id.payment_method_text_list);
            duration_text_list = itemView.findViewById(R.id.duration_text_list);
            note_text_list = itemView.findViewById(R.id.note_text_list);


            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }

    }

    public ListAdapter(ArrayList<ListItems> listItems) {
        mListItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ListItems listItems = mListItems.get(i);

        holder.mImageView.setImageResource(listItems.getImageResource());
        holder.amountTextList.setText(listItems.getAmount());
        if(listItems.getType()){holder.type_text_list.setText("+");
        } else {holder.type_text_list.setText("-");}
        holder.date_text_list.setText(listItems.getDate());
        holder.category_text_list.setText(listItems.getCategory());
        holder.payMet_text_list.setText(listItems.getPaymentMethod());
        holder.duration_text_list.setText(listItems.getDuration());
        holder.note_text_list.setText(listItems.getNote());
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }
}