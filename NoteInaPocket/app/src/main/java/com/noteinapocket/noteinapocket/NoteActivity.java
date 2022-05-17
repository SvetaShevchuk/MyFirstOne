package com.noteinapocket.noteinapocket;

import static com.noteinapocket.noteinapocket.R.string.no_save;
import static com.noteinapocket.noteinapocket.R.string.note_is_saved;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noteinapocket.noteinapocket.db.DbManager;
import com.noteinapocket.noteinapocket.db.ListItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    EditText tv_text_note;
    Button button_back;
    DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
        dbManager.openDb();
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        ListItem item = (ListItem)getIntent().getSerializableExtra("note");
        tv_text_note.setText(item.getNote());
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_text_note.getText().toString().equals("")){
                    Toast.makeText(NoteActivity.this, no_save, Toast.LENGTH_SHORT).show();
                }else{
                    dbManager.updateDb( item.get_id(),tv_text_note.getText().toString(), date);
                    finish();
                    dbManager.closeDb();

                }
            }
        });
    }

    private void init(){
        tv_text_note = findViewById(R.id.tv_text_note);
        button_back = findViewById(R.id.button_save_note);
        dbManager = new DbManager(this);
    }
}