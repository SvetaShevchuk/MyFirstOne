package com.myDictionaryForLearning.dictionary;

import static com.myDictionaryForLearning.dictionary.AdapterDict.myArray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AdapterRussian extends RecyclerView.Adapter<AdapterRussian.ViewHolderRussian> {
    private Context context;

    public AdapterRussian(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderRussian onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_russian, parent, false);
        return new ViewHolderRussian(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRussian holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.tvRussian.setText(myArray.get(position).getRussian());
        holder.button_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.button_translate.setVisibility(View.GONE);
                holder.tvRussian.setVisibility(View.GONE);
                holder.tv_russian_gone.setText(myArray.get(position).getEnglish());
                holder.tv_russian_gone.setVisibility(View.VISIBLE);
                holder.button_translate_russianGone.setVisibility(View.VISIBLE);
            }
        });
        holder.button_translate_russianGone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvRussian.setVisibility(View.VISIBLE);
                holder.tvRussian.setText(myArray.get(position).getRussian());
                holder.button_translate.setVisibility(View.VISIBLE);
                holder.tv_russian_gone.setVisibility(View.GONE);
                holder.button_translate_russianGone.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public class ViewHolderRussian extends RecyclerView.ViewHolder{
        Button button_translate, button_translate_russianGone;
        TextView tvRussian, tv_russian_gone;
        public ViewHolderRussian(@NonNull View itemView) {
            super(itemView);
            tvRussian = itemView.findViewById(R.id.tv_russian);
            tv_russian_gone = itemView.findViewById(R.id.tv_russian_gone);
            button_translate = itemView.findViewById(R.id.button_translate);
            button_translate_russianGone = itemView.findViewById(R.id.button_translate_russianGone);
        }
    }

}
