/*****************************************************************************************
*
*     This is a custom class to store CRUD operations for tasks database
*     Note we've  added an open() method to instantiate the helper class, and make a call to 
*     use the database (getWriteableDatabase).  
*     Oct 2022
*
*******************************************************************************************/
package com.aidand.musicmap.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.aidand.musicmap.models.Listen;

import static com.aidand.musicmap.database.ListensDatabaseHelper.KEY_DESCRIPTION;
import static com.aidand.musicmap.database.ListensDatabaseHelper.KEY_ID;
import static com.aidand.musicmap.database.ListensDatabaseHelper.KEY_NAME;
import static com.aidand.musicmap.database.ListensDatabaseHelper.KEY_STATUS;
import static com.aidand.musicmap.database.ListensDatabaseHelper.TABLE_CONTACTS;


public class TasksDatabaseManager
{
    Context context;
    private ListensDatabaseHelper TaskdbHelper;
    private SQLiteDatabase database;


    public TasksDatabaseManager(Context context)
    {
        this.context = context;

    }

    public TasksDatabaseManager open() throws SQLException {
        TaskdbHelper = new ListensDatabaseHelper(context);
        database = TaskdbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        TaskdbHelper.close();
    }

    // add the new listen
    void addTask(Listen listen) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, listen.getName()); // Listen Name
        values.put(KEY_DESCRIPTION, listen.getDescription()); // Listen Phone
        values.put(KEY_STATUS, listen.getStatus()); // Listen Name

        // Inserting Row
        database.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack

    }


    Listen getTask(int id) {

        Cursor cursor = database.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION, KEY_STATUS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Listen listen = new Listen(
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return listen
        return listen;
    }

    // code to get all tasks in a list view  
    public Cursor getAllTasks() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
  
        Cursor taskList = database.rawQuery(selectQuery, null);
  
        return taskList;
    }  
  
    // code to update the single listen
    public int updateTask(Listen listen) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, listen.getName());
        values.put(KEY_DESCRIPTION, listen.getDescription());
  
        // updating row  
        return database.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(listen.getID()) });
    }  
  
    // Deleting single listen
    public void deleteTask(Listen listen) {

        database.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(listen.getID()) });
        database.close();
    }  
  
    // Getting tasks Count  
    public int getTasksCount() {  
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;  
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();  
  
        // return count  
        return cursor.getCount();  
    }  


}