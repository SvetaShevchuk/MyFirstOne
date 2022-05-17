package com.noteinapocket.noteinapocket.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb(){
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(String note, String date){
        ContentValues cv = new ContentValues();
        cv.put(Constants.NOTE_COLUMN, note);
        cv.put(Constants.DATE_COLUMN, date);
        db.insert(Constants.TABLE_NAME, null, cv);
    }

    public ArrayList<ListItem> getFromDb(){
        ArrayList<ListItem> tempList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_NAME, null, null,null,
                null, null, null);
        while(cursor.moveToNext()){
            ListItem item = new ListItem();
            @SuppressLint("Range") int _id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            @SuppressLint("Range") String myNote =
                    cursor.getString(cursor.getColumnIndex(Constants.NOTE_COLUMN));
            @SuppressLint("Range") String myDate =
                    cursor.getString(cursor.getColumnIndex(Constants.DATE_COLUMN));
            item.set_id(_id);
            item.setNote(myNote);
            item.setDate(myDate);
            tempList.add(item);

        }
        cursor.close();
        return  tempList;
    }

    public void deleteFromDb(int id){
        String selection = Constants._ID + "=" + id;
        db.delete(Constants.TABLE_NAME,  selection, null);
    }

    public void updateDb(int id, String noteText, String date){
        String selection = Constants._ID + "=" + id;
        ContentValues cv = new ContentValues();
        cv.put(Constants.NOTE_COLUMN, noteText);
        cv.put(Constants.DATE_COLUMN, date);
        db.update(Constants.TABLE_NAME, cv, selection, null);
    }

    public void closeDb(){
        dbHelper.close();
    }
}
