package com.noteinapocket.noteinapocket.db;

public class Constants {
    public static final String TABLE_NAME = "noteTable";
    public static final String _ID = "_id";
    public static final String NOTE_COLUMN = "note";
    public static final String DATE_COLUMN = "date";
    public static final String DB_NAME = "note_db";
    public static final int DB_VERSION= 2;
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS "+
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + NOTE_COLUMN  + " TEXT," + DATE_COLUMN + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
