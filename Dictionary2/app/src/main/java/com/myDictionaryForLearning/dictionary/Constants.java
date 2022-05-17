package com.myDictionaryForLearning.dictionary;

public class Constants {
    public static final String TABLE_NAME = "dictTable";
    public static final String _ID = "_id";
    public static final String ENGLISH_COLUMN = "englishColumn";
    public static final String RUSSIAN_COLUMN = "russianColumn";
    public static final String DB_NAME = "dict_db";
    public static final int DB_VERSION= 1;
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS "+
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + ENGLISH_COLUMN
            + " TEXT," + RUSSIAN_COLUMN + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
