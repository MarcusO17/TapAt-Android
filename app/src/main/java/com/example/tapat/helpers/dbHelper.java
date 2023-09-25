package com.example.tapat.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    //Inline class to hold table variable, neater.
    class TABLE_1{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "admin";
        private static final String COL_1 = "adminID";
        private static final String COL_2 = "adminEmail";
        private static final String COL_3 = "adminPassword";
    }


    public dbHelper(Context context) {
        super(context, TABLE_1.TABLE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_1.TABLE_NAME+ " ( "
                + TABLE_1.COL_1 + " int PRIMARY KEY NOT NULL, "
                + TABLE_1.COL_2 + " varchar(255) NOT NULL UNIQUE, "
                + TABLE_1.COL_3 + " varchar(255) NOT NULL"
                + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1.TABLE_NAME);
    }


    /**
     *  Inserts Admin Account into DB
     *
     */
    public void insertAdmin() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("adminID","0001");
        cv.put("adminEmail", "admin1");
        cv.put("adminPassword","admin");
        db.insert(TABLE_1.TABLE_NAME,null,cv);
    }


    /**
     *  To check login details in DB.
     * @param e : Email
     * @param p : Password
     * @return if login is valid/not
     */
    public boolean authLogin(String e, String p){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM admin where adminEmail = ? AND adminPassword = ?",new String[] {e,p});
        if(cursor == null){
            return false;
        }
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }



    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select email from users where email = ?", new String[] {email});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

}
