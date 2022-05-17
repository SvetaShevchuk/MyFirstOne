package com.noteinapocket.noteinapocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.noteinapocket.noteinapocket.db.AdapterNoteGrey;
import com.noteinapocket.noteinapocket.db.DbManager;

public class MainActivityGrey extends AppCompatActivity {

    ImageButton image_create;
    TextView tv_create;
    RecyclerView recyclerView;
    private AdapterNoteGrey adapter;
    DbManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grey);
        init();
        dbManager.openDb();
        image_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityGrey.this, EditActivityGrey.class);
                startActivity(intent);
            }
        });

    }

    void init(){
        image_create = findViewById(R.id.image_create_grey);
        tv_create = findViewById(R.id.tv_create_grey);
        recyclerView = findViewById(R.id.recycler_view_grey);
        dbManager = new DbManager(this);
        adapter = new AdapterNoteGrey(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
        adapter.update(dbManager.getFromDb());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeItem(viewHolder.getAdapterPosition(), dbManager);
            }
        });
    }

}