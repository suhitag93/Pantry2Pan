package com.amazonaws.mobilehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "Pantry";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "type";
    private static final String COL4 = "quantity";
    private static final String COL5 = "expiry";
    final ArrayList<Data> listData = new ArrayList<>();

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, " + COL3 +" TEXT, " + COL4 +" TEXT, " + COL5 + " TEXT " + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, String type, String quantity, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, type);
        contentValues.put(COL4, quantity);
        contentValues.put(COL5, expiry);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Data> getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Data dataEntity=new Data();
            dataEntity.setName(c.getString(2));
            dataEntity.setType(c.getString(3));
            dataEntity.setQuantity(c.getString(4));
            dataEntity.setExpiry(c.getString(5));
            listData.add(dataEntity);
            c.moveToNext();
        }
        c.close();
        db.close();
        return listData;
    }


    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void updateType(String newType, int id, String oldType){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newType + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldType + "'";
        Log.d(TAG, "updateType: query: " + query);
        Log.d(TAG, "updateType: Setting Type to " + newType);
        db.execSQL(query);
    }

    public void updateQuantity(String newQuantity, int id, String oldQuantity){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newQuantity + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldQuantity + "'";
        Log.d(TAG, "updateQuantity: query: " + query);
        Log.d(TAG, "updateQuantity: Setting Quantity to " + newQuantity);
        db.execSQL(query);
    }
    public void updateExpiry(String newExpiry, int id, String oldExpiry){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 +
                " = '" + newExpiry + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL5 + " = '" + oldExpiry + "'";
        Log.d(TAG, "updateExpiry: query: " + query);
        Log.d(TAG, "updateExpiry: Setting Expiry to " + newExpiry);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public String getType(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        String data = String.valueOf(db.rawQuery(query, null));
        return data;
    }
    public String getQuantity(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL4 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        String data = String.valueOf(db.rawQuery(query, null));
        return data;
    }
    public String getExpiry(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL5 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        String data = String.valueOf(db.rawQuery(query, null));
        return data;
    }



}
























