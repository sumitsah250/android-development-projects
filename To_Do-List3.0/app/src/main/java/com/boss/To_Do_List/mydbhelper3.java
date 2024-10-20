package com.boss.To_Do_List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class mydbhelper3 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Contacts9DB";//static used to pre allocate memory and final is used to avoide reassigning value
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACT = "Contacts";
    private static  final String TABLE_COMPLETED="completed";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_TIME = "Phone_no";
    private static final String KEY_Date = "date";
    private static final String KEY_STATUS = "status";
    private Context context;

    public mydbhelper3(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CREATE TABLE  contacts (id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , phone_no TEXT);


        sqLiteDatabase.execSQL(" create table " + TABLE_CONTACT + "(" + KEY_ID + " integer primary key autoincrement,  " + KEY_Date + " text, " + KEY_NAME + " text," + KEY_TIME + " text, " + KEY_STATUS + " text " + ")");
        sqLiteDatabase.execSQL(" create table " + TABLE_COMPLETED + "(" + KEY_ID + " integer primary key autoincrement,  " + KEY_Date + " text, " + KEY_NAME + " text," + KEY_TIME + " text, " + KEY_STATUS + " text " + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_CONTACT);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_COMPLETED);
        onCreate(sqLiteDatabase);

    }

    void addContacts(int a,String Name, String date, String time, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(KEY_ID,new Date().getTime());
        cv.put(KEY_NAME, Name);
        cv.put(KEY_TIME, date);
        cv.put(KEY_Date, time);
        cv.put(KEY_STATUS, status);
         if(a==0){
             long result = db.insert(TABLE_CONTACT, null, cv);
             if (result == -1) {
                 Toast.makeText(context, "failed to insert data", Toast.LENGTH_SHORT).show();
             } else ;

         }else{
             long result = db.insert(TABLE_COMPLETED, null, cv);
             if (result == -1) {
                 Toast.makeText(context, "failed to insert data", Toast.LENGTH_SHORT).show();
             } else; //Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show();
         }

//        db.close();

    }
    void addContacts(int a, dbhelpermodel arrtask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(KEY_ID,new Date().getTime());
        cv.put(KEY_NAME, arrtask.task);
        cv.put(KEY_TIME, arrtask.date);
        cv.put(KEY_Date, arrtask.time);
        cv.put(KEY_STATUS, arrtask.status);
        if(a==0){
            long result = db.insert(TABLE_CONTACT, null, cv);
            if (result == -1) {
                Toast.makeText(context, "failed to insert data", Toast.LENGTH_SHORT).show();
            } else ;//Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show();

        }else{
            long result = db.insert(TABLE_COMPLETED, null, cv);
            if (result == -1) {
                Toast.makeText(context, "failed to insert data", Toast.LENGTH_SHORT).show();
            } else; //Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show();
        }

//        db.close();

    }

    public ArrayList<dbhelpermodel> getcontect() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_CONTACT, null);
        ArrayList<dbhelpermodel> arrcontacts = new ArrayList<>();

        while (cursor.moveToNext()) {
            dbhelpermodel model = new dbhelpermodel();
            model.id = cursor.getInt(0);
            model.date = cursor.getString(1);
            model.task = cursor.getString(2);
            model.time = cursor.getString(3);
            model.status = (cursor.getInt(4))>0;
            arrcontacts.add(model);
        }
        return arrcontacts;
    }
    public ArrayList<dbhelpermodel> getcontect1() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_COMPLETED, null);
        ArrayList<dbhelpermodel> arrcontacts = new ArrayList<>();

        while (cursor.moveToNext()) {
            dbhelpermodel model = new dbhelpermodel();
            model.id = cursor.getInt(0);
            model.date = cursor.getString(1);
            model.task = cursor.getString(2);
            model.time = cursor.getString(3);
            model.status = (cursor.getInt(4))>0;
            arrcontacts.add(model);
        }
        return arrcontacts;
    }

    public void UpdateContact(int a, dbhelpermodel dbhelpermodel) {
        if(a==0){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME, dbhelpermodel.task);
            cv.put(KEY_Date, dbhelpermodel.date);
            cv.put(KEY_TIME, dbhelpermodel.time);
            cv.put(KEY_STATUS, dbhelpermodel.status);
            db.update(TABLE_CONTACT, cv, KEY_ID + " = " + dbhelpermodel.id, null);
        }else{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME, dbhelpermodel.task);
            cv.put(KEY_Date, dbhelpermodel.date);
            cv.put(KEY_TIME, dbhelpermodel.time);
            cv.put(KEY_STATUS, dbhelpermodel.status);
            db.update(TABLE_COMPLETED, cv, KEY_ID + " = " + dbhelpermodel.id, null);

        }

    }

    public void DeleteContact(int a, dbhelpermodel dbhelpermodel) {
        if(a==0){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_CONTACT, KEY_ID + " = ?", new String[]{String.valueOf(dbhelpermodel.id)});
        }else{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_COMPLETED, KEY_ID + " = ?", new String[]{String.valueOf(dbhelpermodel.id)});
        }

    }
}
