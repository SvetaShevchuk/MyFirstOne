package com.myDictionaryForLearning.dictionary.grey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myDictionaryForLearning.dictionary.R;


public class RussianActivityGrey extends AppCompatActivity {

    Button button_russian_back, button_russian_grey;
    RecyclerView recyclerViewRussianGrey;
    private AdapterRussianGrey adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_russian_grey);
        init();
        button_russian_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RussianActivityGrey.this, MainActivityGrey.class);
                startActivity(intent);
            }
        });

        button_russian_grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RussianActivityGrey.this, EnglishActivityGrey.class);
                startActivity(intent);
            }
        });


    }


    void init(){
        button_russian_back = findViewById(R.id.button_russian_back_grey);
        button_russian_grey = findViewById(R.id.button_russian_grey);
        recyclerViewRussianGrey = findViewById(R.id.rv_view_russian_grey);
        adapter = new AdapterRussianGrey(this);
        recyclerViewRussianGrey.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRussianGrey.setAdapter(adapter);
    }
}