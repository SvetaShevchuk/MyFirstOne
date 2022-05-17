package com.noteinapocket.noteinapocket;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class StartActivity extends AppCompatActivity  {
    TextView tv_text_choose, tv_text_green, tv_text_blue, tv_text_start, tv_text_red, tv_text_yellow,
            tv_text_grey;
    ImageView image_green, image_blue, image_red, image_yellow, image_grey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initViews();



        image_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intentGreen = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intentGreen);

            }
        });
        image_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intentGreen = new Intent(StartActivity.this, MainActivityBlue.class);
                    startActivity(intentGreen);

            }
        });
        image_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intentGreen = new Intent(StartActivity.this, MainActivityRed.class);
                    startActivity(intentGreen);

            }
        });
        image_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intentGreen = new Intent(StartActivity.this, MainActivityYellow.class);
                    startActivity(intentGreen);

            }
        });
        image_grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intentGreen = new Intent(StartActivity.this, MainActivityGrey.class);
                    startActivity(intentGreen);

            }
        });
    }



    private void initViews() {
        tv_text_choose = findViewById(R.id.tv_text_choose);
        image_green = findViewById(R.id.image_green);
        image_blue = findViewById(R.id.image_blue);
        tv_text_green = findViewById(R.id.tv_text_green);
        tv_text_blue = findViewById(R.id.tv_text_blue);
        tv_text_start = findViewById(R.id.tv_text_start);
        image_red = findViewById(R.id.image_red);
        tv_text_red = findViewById(R.id.tv_text_red);
        tv_text_yellow = findViewById(R.id.tv_text_yellow);
        image_yellow = findViewById(R.id.image_yellow);
        tv_text_grey = findViewById(R.id.tv_text_grey);
        image_grey = findViewById(R.id.image_grey);

    }


}