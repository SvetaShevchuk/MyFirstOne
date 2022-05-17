package com.myDictionaryForLearning.dictionary.grey;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myDictionaryForLearning.dictionary.ListItem;
import com.myDictionaryForLearning.dictionary.R;

import java.util.ArrayList;

public class AdapterRussianGrey extends RecyclerView.Adapter<AdapterRussianGrey.ViewHolderRussian> {
    private Context context;

    public AdapterRussianGrey(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRussianGrey.ViewHolderRussian onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_russian_grey, parent, false);
        return new AdapterRussianGrey.ViewHolderRussian(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRussianGrey.ViewHolderRussian holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.tvRussian.setText(AdapterGrey.myArray.get(position).getRussian());
        holder.button_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.button_translate.setVisibility(View.GONE);
                holder.tvRussian.setVisibility(View.GONE);
                holder.tv_russian_gone.setText(AdapterGrey.myArray.get(position).getEnglish());
                holder.tv_russian_gone.setVisibility(View.VISIBLE);
                holder.button_translate_russianGone.setVisibility(View.VISIBLE);
            }
        });
        holder.button_translate_russianGone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvRussian.setVisibility(View.VISIBLE);
                holder.tvRussian.setText(AdapterGrey.myArray.get(position).getRussian());
                holder.button_translate.setVisibility(View.VISIBLE);
                holder.tv_russian_gone.setVisibility(View.GONE);
                holder.button_translate_russianGone.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AdapterGrey.myArray.size();
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

    public void updateAdapterRussian(ArrayList<ListItem> newList){
        AdapterGrey.myArray.clear();
        AdapterGrey.myArray.addAll(newList);
        notifyDataSetChanged();
    }
}
