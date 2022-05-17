package com.myDictionaryForLearning.dictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDict extends RecyclerView.Adapter<AdapterDict.ViewHolderDict> {

    private Context context;
    public static ArrayList<ListItem> myArray;
    DbManager dbManager;


    public AdapterDict(Context context) {
        this.context = context;
        myArray = new ArrayList<>();
        dbManager = new DbManager(context);
        dbManager.openDb();
    }

    @NonNull
    @Override
    public ViewHolderDict onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent,
                false);
        return new ViewHolderDict(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDict holder,
                                 @SuppressLint("RecyclerView")int position) {
        holder.tv_left.setText(myArray.get(position).getEnglish());
        holder.tv_right.setText(myArray.get(position).getRussian());
        holder.image_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_more, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        removeItem(position, dbManager);
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public class ViewHolderDict extends RecyclerView.ViewHolder{
        TextView tv_left, tv_right;
        ImageView image_more;
        public ViewHolderDict(@NonNull View itemView) {
            super(itemView);
            tv_left = itemView.findViewById(R.id.tv_left);
            tv_right = itemView.findViewById(R.id.tv_right);
            image_more = itemView.findViewById(R.id.image_more);
        }
    }

    public void updateAdapter(ArrayList<ListItem> newList){
         myArray.clear();
         myArray.addAll(newList);
         notifyDataSetChanged();
    }

    public void removeItem(int position, DbManager dbManager){
        dbManager.deleteFile(myArray.get(position).getId());
        myArray.remove(position);
        notifyItemRangeChanged(0, myArray.size());
        notifyItemRemoved(position);
    }
}
