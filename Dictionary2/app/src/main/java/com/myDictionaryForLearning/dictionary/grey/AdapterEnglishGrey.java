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

import com.myDictionaryForLearning.dictionary.R;

public class AdapterEnglishGrey extends RecyclerView.Adapter<AdapterEnglishGrey.ViewHolderEnglish> {
    private Context context;

    public AdapterEnglishGrey(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEnglishGrey.ViewHolderEnglish onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_english_grey, parent, false);
        return new AdapterEnglishGrey.ViewHolderEnglish(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEnglishGrey.ViewHolderEnglish holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.tv_english.setText(AdapterGrey.myArray.get(position).getEnglish());
        holder.button_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.button_translate.setVisibility(View.GONE);
                holder.tv_english.setVisibility(View.GONE);
                holder.tv_english_gone.setText(AdapterGrey.myArray.get(position).getRussian());
                holder.tv_english_gone.setVisibility(View.VISIBLE);
                holder.button_translate_englishGone.setVisibility(View.VISIBLE);
            }
        });
        holder.button_translate_englishGone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_english.setVisibility(View.VISIBLE);
                holder.tv_english.setText(AdapterGrey.myArray.get(position).getEnglish());
                holder.button_translate.setVisibility(View.VISIBLE);
                holder.tv_english_gone.setVisibility(View.GONE);
                holder.button_translate_englishGone.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AdapterGrey.myArray.size();
    }

    public class ViewHolderEnglish extends RecyclerView.ViewHolder{
        Button button_translate, button_translate_englishGone;
        TextView tv_english, tv_english_gone;
        public ViewHolderEnglish(@NonNull View itemView) {
            super(itemView);
            tv_english = itemView.findViewById(R.id.tv_english);
            tv_english_gone = itemView.findViewById(R.id.tv_english_gone);
            button_translate = itemView.findViewById(R.id.button_translate_english);
            button_translate_englishGone = itemView.findViewById(R.id.button_translate_englishGone);
        }
    }

}

