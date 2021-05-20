package com.example.modellab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="user.db";
    public static final String TABLE_NAME="User";
    public static final String COL_1="USERNAME";
    public static final String COL_2="PASSWORD";
    public static final String COL_3="NAME";
    public static final String COL_4="AGE";
    public static final String COL_5="GENDER";
    public static final String COL_6="DOB";
    public static final String COL_7="BGROUP";
    public static final String COL_8="MOBILE";
    public static final String COL_9="EMAIL";

    public static final String table_name="Request";
    public static final String col_1="BGROUP";
    public static final String col_2="ADDRESS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT,NAME TEXT, AGE TEXT, GENDER TEXT, DOB TEXT, BGROUP TEXT, MOBILE TEXT, EMAIL TEXT)");
        db.execSQL("create table " + table_name + " (BGROUP TEXT, ADDRESS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate(db);
    }

    public boolean insertData(String username, String password, String name, String age, String gender, String dob, String bgroup, String mobile, String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, age);
        contentValues.put(COL_5, gender);
        contentValues.put(COL_6, dob);
        contentValues.put(COL_7, bgroup);
        contentValues.put(COL_8, mobile);
        contentValues.put(COL_9, email);
        long results=db.insert(TABLE_NAME,null, contentValues);
        return results != -1;
    }

    public boolean insertDataRequest(String bgroup,String address)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_1, bgroup);
        contentValues.put(col_2, address);

        long results=db.insert(table_name,null, contentValues);
        return results != -1;
    }

    public boolean checkUsername(String username) {

        // array of columns to fetch
        String[] columns = {
                COL_1
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COL_1 + " = ?";

        // selection argument
        String[] selectionArgs = {username};

        // query user table with condition

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public String getBg(String username) {
        String[] columns = {
                COL_7
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COL_1 + " = ?" ;

        // selection arguments
        String[] selectionArgs = {username};

        // query user table with conditions

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        cursor.moveToFirst();
        String res = cursor.getString(cursor.getColumnIndex(COL_7));
        cursor.close();
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password) {

        // array of columns to fetch
        String[] columns = {
                COL_1
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COL_1 + " = ?" + " AND " + COL_2 + " = ?";

        // selection arguments
        String[] selectionArgs = {username, password};

        // query user table with conditions

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        return cursorCount > 0;
    }


    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_NAME,null);
    }

    public Cursor getAllDataRequest()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("select * from "+table_name,null);
    }

    public String getRequest(String bg) {
        String[] columns = {
                col_1, col_2
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = col_1 + " = ?" ;

        // selection arguments
        String[] selectionArgs = {bg};

        // query user table with conditions

        Cursor cursor = db.query(table_name, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        StringBuilder ans= new StringBuilder();

        while (cursor.moveToNext()) {
            ans.append("Blood Group:").append(cursor.getString(0)).append("\n");
            ans.append("Address:").append(cursor.getString(1)).append("\n\n\n");
        }

        cursor.close();
        db.close();
        return ans.toString();
    }

}
