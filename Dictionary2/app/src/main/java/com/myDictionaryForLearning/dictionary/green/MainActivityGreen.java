package com.myDictionaryForLearning.dictionary.green;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.myDictionaryForLearning.dictionary.AppExecutor;
import com.myDictionaryForLearning.dictionary.DbManager;
import com.myDictionaryForLearning.dictionary.ListItem;
import com.myDictionaryForLearning.dictionary.OnDataReceived;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.myDictionaryForLearning.dictionary.R;

import java.util.ArrayList;

public class MainActivityGreen extends AppCompatActivity implements OnDataReceived {

    private EditText edEnglish, edRussian;
    private Button button, button_english, button_russian;
    private DbManager dbManager;
    private RecyclerView recyclerViewGreen;
    private AdapterGreen adapter;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_green);
        init();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        dbManager.openDb();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edEnglish.getText().toString().equals("")||
                        edRussian.getText().toString().equals("")){
                    Toast.makeText(MainActivityGreen.this, R.string.no_save, Toast.LENGTH_SHORT).show();
                }else {

                    dbManager.insertToDb(edEnglish.getText().toString(),
                            edRussian.getText().toString());
                    readFromDb("");
                    edEnglish.setText("");
                    edRussian.setText("");
                }
            }
        });
        button_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEnglish = new Intent(MainActivityGreen.this, EnglishActivityGreen.class);
                startActivity(intentEnglish);
            }
        });
        button_russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRussian = new Intent(MainActivityGreen.this, RussianActivityGreen.class);
                startActivity(intentRussian);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search_id);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                readFromDb(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void init(){
        edEnglish = findViewById(R.id.ed_english_green);
        edRussian = findViewById(R.id.ed_Russian_green);
        button = findViewById(R.id.button_green);
        button_english = findViewById(R.id.button_english_green);
        button_russian = findViewById(R.id.button_russian_green);
        dbManager = new DbManager(this);
        recyclerViewGreen = findViewById(R.id.rv_view_green);
        adapter = new AdapterGreen(this);
        recyclerViewGreen.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(recyclerViewGreen);
        recyclerViewGreen.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
        readFromDb("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeItem(viewHolder.getAdapterPosition(), dbManager);
            }
        });
    }

    @Override
    public void onReceived(ArrayList<ListItem> list) {
        AppExecutor.getInstance().getMainIo().execute(new Runnable() {
            @Override
            public void run() {
                adapter.updateAdapter(list);
            }
        });
    }

    private void readFromDb(final String text){
        AppExecutor.getInstance().getSubIo().execute(new Runnable() {
            @Override
            public void run() {
                dbManager.getFromDb(text, MainActivityGreen.this);
            }
        });
    }
}