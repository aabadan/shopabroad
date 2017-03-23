package com.alicanabadan.shopabroad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alican on 3/15/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "dataManager";
    // Cities Table Columns names
    private static final String TABLE_CITIES = "cities";
    private static final String CITIES_KEY_ID = "id";
    private static final String CITIES_KEY_NAME = "name";
    private static final String CITIES_KEY_CODE = "code";
    // Items Table Columns names
    private static final String TABLE_ITEMS = "items";
    private static final String ITEMS_KEY_ID = "id";
    private static final String ITEMS_KEY_NAME = "name";
    private static final String ITEMS_KEY_FROM = "fromPort";
    private static final String ITEMS_KEY_TO = "toPort";
    private static final String ITEMS_KEY_PRICE = "price";
    private static final String ITEMS_KEY_DESCRIPTION = "desc";
    private static final String ITEMS_KEY_IMAGE = "image";
    private static final String ITEMS_IS_GRANTED = "is_granted";
    private static final String ITEMS_GRANTED_USER = "granted_user";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CITIES + "("
                + CITIES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CITIES_KEY_NAME + " TEXT,"
                + CITIES_KEY_CODE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + ITEMS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEMS_KEY_NAME + " TEXT,"
                + ITEMS_KEY_FROM + " TEXT," + ITEMS_KEY_TO + " TEXT,"+ ITEMS_KEY_PRICE + " DOUBLE,"
                + ITEMS_KEY_DESCRIPTION + " TEXT,"+ ITEMS_KEY_IMAGE + " BLOB,"+ ITEMS_IS_GRANTED + " TEXT,"
                + ITEMS_GRANTED_USER + " TEXT" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new city
    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CITIES_KEY_NAME, city.getName()); // city Name
        values.put(CITIES_KEY_CODE, city.getCode()); // city Code

        // Inserting Row
        db.insert(TABLE_CITIES, null, values);
        db.close(); // Closing database connection
    }

    // Getting All City Names
    public List<String> getCityNames() {
        List<String> cityList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT NAME,CODE FROM " + TABLE_CITIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding city to list
                cityList.add(cursor.getString(0) + "(" + cursor.getString(1) + ")");
            } while (cursor.moveToNext());
        }

        // return city list
        return cityList;
    }

    // Adding new item
    public void addItem(OrderItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEMS_KEY_NAME, item.getName());
        values.put(ITEMS_KEY_FROM, item.getFromPort());
        values.put(ITEMS_KEY_TO, item.getToPort());
        values.put(ITEMS_KEY_PRICE, item.getPrice());
        values.put(ITEMS_KEY_DESCRIPTION, item.getDescription());
        values.put(ITEMS_KEY_IMAGE, item.getImage());
        values.put(ITEMS_IS_GRANTED, "F");
        values.put(ITEMS_GRANTED_USER, "");

        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Order Items which are not granted
    public List<OrderItem> getOrders(boolean isGranted) {
        List<OrderItem> orderList = new ArrayList<OrderItem>();
        String isGrantedStr = "F";
        if(isGranted){
            isGrantedStr = "T";
        }
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + ITEMS_IS_GRANTED + " = '" + isGrantedStr + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding order to list
                OrderItem ord = new OrderItem();
                ord.setId(cursor.getInt(0));
                ord.setName(cursor.getString(1));
                ord.setFromPort(cursor.getString(2));
                ord.setToPort(cursor.getString(3));
                ord.setPrice(cursor.getDouble(4));
                ord.setDescription(cursor.getString(5));
                ord.setImage(cursor.getBlob(6));
                ord.setIsGranted(cursor.getString(7));
                ord.setGrantedUser(cursor.getString(8));
                orderList.add(ord);
            } while (cursor.moveToNext());
        }

        // return order list
        return orderList;
    }

    public void grantItem(int orderId, String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_ITEMS + " SET IS_GRANTED ='T' , GRANTED_USER ='" + userName + "'" +
                " WHERE " + ITEMS_KEY_ID + "='" + orderId + "'";
        db.execSQL(updateQuery);
    }

}
