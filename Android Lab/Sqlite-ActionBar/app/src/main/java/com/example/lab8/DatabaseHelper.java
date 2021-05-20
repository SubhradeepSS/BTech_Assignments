package com.example.lab8;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ProductManager.db";

    // User table name
    private static final String TABLE_PRODUCT = "product";

    // User Table Columns names
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRODUCT_MRP = "product_mrp";
    private static final String COLUMN_PRODUCT_PRICE = "product_price";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table sql query
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_PRODUCT_ID + " TEXT," + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRODUCT_MRP + " TEXT,"+ COLUMN_PRODUCT_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_ID, product.getId());
        values.put(COLUMN_PRODUCT_MRP, product.getMrp());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public void updateProduct(String id,String name,String mrp,String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_PRODUCT + " SET " + COLUMN_PRODUCT_NAME + " = \"" + name +
                "\" , " + COLUMN_PRODUCT_PRICE + " = \"" + price +
                "\" , " + COLUMN_PRODUCT_MRP + " = \"" + mrp +
                "\"" + " WHERE " + COLUMN_PRODUCT_ID + " = \"" + id + "\"";
        db.execSQL(query);
    }
    public List<Product> getAllProduct() {
        String[] columns = {
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_MRP,
                COLUMN_PRODUCT_PRICE
        };
        String sortOrder = COLUMN_PRODUCT_NAME + " ASC";
        List<Product> productList = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME)));
                product.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_MRP)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE)));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public boolean checkProduct(String id) {

        String[] columns = {
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_MRP,
                COLUMN_PRODUCT_PRICE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public Product retProduct(String id) {

        String[] columns = {
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_MRP,
                COLUMN_PRODUCT_PRICE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        Product product = new Product();
        cursor.moveToFirst();
        product.setId(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
        product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME)));
        product.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_MRP)));
        product.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE)));
        cursor.close();
        db.close();
        return product;
    }

}