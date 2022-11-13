/***********************************************
*
*    This is the helper class (note that it extends SQLliteopenhelper
*    It is responsible for creating the database
*    Note that there is no direct call to the "onCreate" method
*    that contains the dB creation code.  This method is run by the system
*    when a request is made to access the database, and if the database doesn'table
*    exist yet.
*    OCt 2022
*
*************************************************/

package com.aidand.musicmap.database;
  
import android.content.ContentValues;  
import android.content.Context;  
import android.database.Cursor;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;  
import java.util.ArrayList;  
import java.util.List;  
  
  
public class ListensDatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasksDb";
    public static final String TABLE_CONTACTS = "tasks";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_STATUS = "status";

    public ListensDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
        //3rd argument to be passed is CursorFactory instance  
    }  
  
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("  
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_STATUS + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);  
    }  
  
    // update database structure
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        // Drop older table if existed  
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);  
  
        // Create tables again  
        onCreate(db);  
    }  
}