package com.myDictionaryForLearning.dictionary.green;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myDictionaryForLearning.dictionary.R;

public class RussianActivityGreen extends AppCompatActivity {

    Button button_russian_back, button_russian_grey;
    RecyclerView recyclerViewRussianGreen;
    private AdapterRussianGreen adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_russian_green);
        init();

        button_russian_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RussianActivityGreen.this, MainActivityGreen.class);
                startActivity(intent);
            }
        });

        button_russian_grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RussianActivityGreen.this, EnglishActivityGreen.class);
                startActivity(intent);
            }
        });
    }

    void init(){
        button_russian_back = findViewById(R.id.button_russian_back_green);
        button_russian_grey = findViewById(R.id.button_russian_green);
        recyclerViewRussianGreen = findViewById(R.id.rv_view_russian_green);
        adapter = new AdapterRussianGreen(this);
        recyclerViewRussianGreen.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRussianGreen.setAdapter(adapter);
    }
}