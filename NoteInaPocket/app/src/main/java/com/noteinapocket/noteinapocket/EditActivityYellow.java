package com.noteinapocket.noteinapocket;

import static com.noteinapocket.noteinapocket.R.string.no_text_to_save;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.noteinapocket.noteinapocket.db.DbManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivityYellow extends AppCompatActivity {

    EditText ed_note;
    Button save_button;
    private DbManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_yellow);
        initViews();
        dbManager.openDb();
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_note.getText().toString().equals("")){
                    Toast.makeText(EditActivityYellow.this, no_text_to_save, Toast.LENGTH_SHORT).show();
                }else {
                    dbManager.insertToDb(ed_note.getText().toString(), date);
                    dbManager.closeDb();
                    finish();
                }
            }
        });
    }

    void initViews(){
        ed_note = findViewById(R.id.ed_note_yellow);
        save_button = findViewById(R.id.save_button_yellow);
        dbManager = new DbManager(this);
    }


}