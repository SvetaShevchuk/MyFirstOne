package com.myDictionaryForLearning.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class EnglishActivity extends AppCompatActivity {
    Button button_english_back, button_english_russian;
    RecyclerView recyclerViewEnglish;
    private DbManager dbManager;
    private AdapterEnglish adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
        init();
        button_english_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnglishActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        button_english_russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnglishActivity.this, RussianActivity.class);
                startActivity(intent);
            }
        });
    }



    private void init(){
        button_english_back = findViewById(R.id.button_english_back);
        button_english_russian = findViewById(R.id.button_english_russian);
        recyclerViewEnglish = findViewById(R.id.rv_view_english);
        dbManager = new DbManager(this);
        adapter = new AdapterEnglish(this);
        recyclerViewEnglish.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEnglish.setAdapter(adapter);
    }



}