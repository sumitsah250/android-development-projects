package com.boss.To_Do_List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and table information
    private static final String DATABASE_NAME = "1intDatabase.db";
    private static final String TABLE_NAME = "intTable";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_VALUE = "intValue";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_VALUE + " INTEGER)";
        db.execSQL(createTable);
    }

    // Update table if needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert or update the integer value
    public boolean insertOrUpdateValue(int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, 1); // ID is fixed to 1 to store only one value
        contentValues.put(COLUMN_VALUE, value);

        long result = db.replace(TABLE_NAME, null, contentValues);
        return result != -1; // Returns true if successful
    }

    // Get the stored integer value
    public int getValue() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_VALUE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0; // Default value if not found
    }
}
