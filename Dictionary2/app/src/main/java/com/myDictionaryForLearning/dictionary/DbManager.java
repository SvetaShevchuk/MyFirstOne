package com.myDictionaryForLearning.dictionary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DbManager {
    Context context;
    DbHelper dbHelper;
    private SQLiteDatabase  db;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb(){
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(String english, String russian){
        ContentValues cv = new ContentValues();
        cv.put(Constants.ENGLISH_COLUMN, english);
        cv.put(Constants.RUSSIAN_COLUMN, russian);
        db.insert(Constants.TABLE_NAME, null, cv);
    }

    public void getFromDb(String englishWord, OnDataReceived onDataReceived){
        ArrayList<ListItem> tempList = new ArrayList<>();
        String selection = Constants.ENGLISH_COLUMN + " like ?";
        Cursor cursor = db.query(Constants.TABLE_NAME, null, selection,
                new String[]{"%" + englishWord + "%"}, null, null, null);
        while(cursor.moveToNext()){
            ListItem item = new ListItem();
            @SuppressLint("Range") String english =
                    cursor.getString(cursor.getColumnIndex(Constants.ENGLISH_COLUMN));
            @SuppressLint("Range") String russian =
                    cursor.getString(cursor.getColumnIndex(Constants.RUSSIAN_COLUMN));
            @SuppressLint("Range") int id =
                    cursor.getInt(cursor.getColumnIndex(Constants._ID));
            item.setEnglish(english);
            item.setRussian(russian);
            item.setId(id);
            tempList.add(item);
        }
        cursor.close();
        onDataReceived.onReceived(tempList);
    }

    public void deleteFile(int id){
        String selection = Constants._ID + "=" + id;
        db.delete(Constants.TABLE_NAME, selection, null);
    }

    public void closeDb(){
        dbHelper.close();
    }
}
