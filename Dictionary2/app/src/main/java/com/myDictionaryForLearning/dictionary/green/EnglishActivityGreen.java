package com.myDictionaryForLearning.dictionary.green;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myDictionaryForLearning.dictionary.DbManager;
import com.myDictionaryForLearning.dictionary.R;


public class EnglishActivityGreen extends AppCompatActivity {

    Button button_english_back, button_english_russian;
    RecyclerView recyclerViewEnglish;
    private DbManager dbManager;
    private AdapterEnglishGreen adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_green);
        init();

        button_english_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnglishActivityGreen.this, MainActivityGreen.class);
                startActivity(intent);
            }
        });
        button_english_russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnglishActivityGreen.this, RussianActivityGreen.class);
                startActivity(intent);
            }
        });
    }


    private void init(){
        button_english_back = findViewById(R.id.button_english_back_green);
        button_english_russian = findViewById(R.id.button_english_russian_green);
        recyclerViewEnglish = findViewById(R.id.rv_view_english_green);
        dbManager = new DbManager(this);
        adapter = new AdapterEnglishGreen(this);
        recyclerViewEnglish.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEnglish.setAdapter(adapter);
    }

}