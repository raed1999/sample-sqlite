package com.example.samplesqliteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "profiles.db";
    private static final String TABLE_NAME = "profiles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";


    /**
     *
     * A constructor that opens the database when called
     *
     */
    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     *
     *  onCreate -> This is a required method when implementing SQLiteOpenHelper Class
     *  This method handles tha creation of your table
     *
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(query);
    }

    /**
     *
     *  onUpgrade -> This is a required method when implementing SQLiteOpenHelper Class
     *  This method drop the old table if it exist
     *  Manages the version of your table
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /**
     *
     * Handles the signUp process of database
     *
     */
    public boolean signUp( String name,String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase(); // Opens the database
        ContentValues values = new ContentValues(); // Used to input data in our database
        values.put(COLUMN_NAME, name); // Inserts the name from argument into an array
        values.put(COLUMN_USERNAME, username); // Inserts the username from argument into an array
        values.put(COLUMN_PASSWORD, password); // Inserts the password from argument into an array
        long result = db.insert(TABLE_NAME, null, values); // returns 0(failed) or 1(successfull)
        db.close(); //close after used
        return result != -1; // returns the result if not -1 (-1 means theres an error occured)
    }


    /**
     *
     * Handles the login process of database
     *
     */
    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase(); // Opens the database
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + // this 3 lines of query, manipulates
                COLUMN_USERNAME + " = '" + username + "' AND " +   // data  you want to get from the
                COLUMN_PASSWORD + " = '" + password + "'";         // database

        Cursor cursor = db.rawQuery(query, null); // The cursor class catch the returned by the database

        boolean result = cursor.moveToFirst(); // Default value is false when no data. If it moveToFirst (0 index), there is a data.
        cursor.close(); // close the cursor
        db.close(); // close the database
        return result; // returns either -1 or 0;
    }

    /**
     *
     * Checks weather the username to be inserted
     * is present or not to ensure uniqueness
     */
    public boolean checkUserNameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase(); // Opens the database
        Cursor cursor = db.rawQuery("SELECT username FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username}); // Will return the data to cursor
        boolean exists = false;
        if(cursor.getCount() > 0){ // if the data set (array) is not empty
            exists = true; // return true
       }
        cursor.close(); // close cursor
        db.close(); // close database
        return exists; // returns true or false
    }

    /**
     *
     * Gets the profile based on the username (unique)
     * Then returns it as cursor object
     */
    public Cursor getProfile(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        return cursor;
    }


}
