package com.myDictionaryForLearning.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class RussianActivity extends AppCompatActivity {

    Button button_russian_back, button_english_russian;
    RecyclerView recyclerViewRussian;
    private AdapterRussian adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_russian);
        init();
        button_russian_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RussianActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        button_english_russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RussianActivity.this, EnglishActivity.class);
                startActivity(intent);
            }
        });
    }

    void init(){
        button_russian_back = findViewById(R.id.button_russian_back);
        button_english_russian = findViewById(R.id.button_english_russian);
        recyclerViewRussian = findViewById(R.id.rv_view_russian);
        adapter = new AdapterRussian(this);
        recyclerViewRussian.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRussian.setAdapter(adapter);
    }
}