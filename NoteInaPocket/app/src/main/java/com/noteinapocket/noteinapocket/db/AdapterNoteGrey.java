package com.noteinapocket.noteinapocket.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noteinapocket.noteinapocket.NoteActivity;
import com.noteinapocket.noteinapocket.NoteActivityGrey;
import com.noteinapocket.noteinapocket.R;

import java.util.ArrayList;

public class AdapterNoteGrey extends RecyclerView.Adapter<AdapterNoteGrey.NoteViewHolder> {
    Context context;
    public static ArrayList<ListItem> noteArray;

    public AdapterNoteGrey(Context context) {
        this.context = context;
        noteArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_note.setText(noteArray.get(position).getNote());
        holder.tv_note2.setText(noteArray.get(position).getDate());
        holder.tv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoteActivityGrey.class);
                intent.putExtra("note", noteArray.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteArray.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView tv_note, tv_note2;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_note = itemView.findViewById(R.id.tv_note);
            tv_note2 = itemView.findViewById(R.id.tv_note_date);
        }
    }

    public void update(ArrayList<ListItem> newList){
        noteArray.clear();
        noteArray.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeItem(int position, DbManager dbManager){
        dbManager.deleteFromDb(noteArray.get(position).get_id());
        noteArray.remove(position);
        notifyItemRangeChanged(0, noteArray.size());
        notifyItemRemoved(position);
    }
}
