package com.example.bs_36.cwc1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IIT on 1/30/2015.
 */
public class DataHandler {

    public static final String PRODUCT_NAME ="productName";
    public static final String IMAGE="image";
    public static final String PRICE="price";
    public static final String LOCATION = "location";
    public static final String CONTACT_ADDRESS = "contactAddress";
    public static final String MOBILE = "mobile";
    public static final String PRODUCT_TABLE_NAME="myTable";
//    public static final String PARTNER_TABLE_NAME="partnerTable";
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="myDatabase";
    public static final String PRODUCT_TABLE_CREATE= "create table myTable(productName text not null,image text not null, price text not null, location text not null, contactAddress text not null, mobile text not null);";
//    public static final String PARTNER_TABLE_CREATE= "create table partnerTable(sexSP text not null,religionSP text not null,educationSP text not null,heightEditText text not null,weightEditText text not null,placeSP text not null,zillaSP text not null,charecterET text not null);";

    DataBaseHelper dbHelper;
    Context ctx;
    SQLiteDatabase db;


    public DataHandler(Context ctx)
    {
        this.ctx=ctx;
        dbHelper = new DataBaseHelper(ctx);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper
    {


        public DataBaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null,DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try{
                db.execSQL(PRODUCT_TABLE_CREATE);
//                db.execSQL(PARTNER_TABLE_CREATE);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS myTable");
//            db.execSQL("DROP TABLE IF EXISTS partnerTable");
            onCreate(db);
        }
    }

    public DataHandler open()
    {
        db= dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insertData(String productName, String image, String price ,String location, String contactAddress, String mobile) {
        ContentValues content = new ContentValues();
        content.put(PRODUCT_NAME, productName);
        content.put(IMAGE, image);
        content.put(PRICE, price);
        content.put(LOCATION, location);
        content.put(CONTACT_ADDRESS, contactAddress);
        content.put(MOBILE, mobile);

        return db.insertOrThrow(PRODUCT_TABLE_NAME, null, content);

    }

    public Cursor returnData() {
        return db.query(PRODUCT_TABLE_NAME, new String[]{PRODUCT_NAME, IMAGE, PRICE, LOCATION, CONTACT_ADDRESS, MOBILE}, null, null, null, null, null);
    }
}
