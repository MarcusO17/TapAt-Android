package com.example.tapat.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    /***********************************************************************************************
     **********************************     TABLES!       ******************************************
     ***********************************************************************************************
     */

    static class Admin{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "admins";
        private static final String COL_1 = "admin_ID";
        private static final String COL_2 = "admin_email";
        private static final String COL_3 = "admin_password";
    }

    static class Lecturer{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "lecturers";
        private static final String COL_1 = "lecturer_ID";
        private static final String COL_2 = "lecturer_email";
        private static final String COL_3 = "lecturer_password";
        private static final String COL_4 = "lecturer_name";
        private static final String COL_5 = "course_list";
    }

    static class Student{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "students";
        private static final String COL_1 = "student_ID";
        private static final String COL_2 = "student_name";
        private static final String COL_3 = "programme_ID";
        private static final String COL_4 = "programme_name";
    }

    static class Course{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "courses";
        private static final String COL_1 = "course_ID";
        private static final String COL_4 = "lecturer_ID";
        private static final String COL_2 = "course_name";
        private static final String COL_3 = "student_list";
    }

    static class Users{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "users";
        private static final String COL_1 = "ID";
        private static final String COL_2 = "email";
        private static final String COL_3 = "password";
        private static final String COL_4 = "role";
    }



    public dbHelper(Context context) {
        super(context, Admin.DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Admin.TABLE_NAME+ " ( "
                + Admin.COL_1 + " int PRIMARY KEY NOT NULL, "
                + Admin.COL_2 + " varchar(255) NOT NULL,"
                + Admin.COL_3 + " varchar(255) NOT NULL"
                + " )");
        db.execSQL("CREATE TABLE " + Lecturer.TABLE_NAME+ " ( "
                + Lecturer.COL_1 + " int PRIMARY KEY NOT NULL, "
                + Lecturer.COL_2 + " varchar(255) NOT NULL,"
                + Lecturer.COL_3 + " varchar(255) NOT NULL"
                + " )");
        db.execSQL("CREATE TABLE " + Users.TABLE_NAME+ " ( "
                + Users.COL_1 + " int PRIMARY KEY NOT NULL, "
                + Users.COL_2 + " varchar(255) NOT NULL,"
                + Users.COL_3 + " varchar(255) NOT NULL,"
                + Users.COL_4 + " varchar(255) NOT NULL"
                + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Admin.TABLE_NAME);
    }


    /**
     *  Inserts Admin Account into DB
     *
     */
    public void insertAdmin() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Admin.COL_1,"0001");
        cv.put(Admin.COL_2, "admin1");
        cv.put(Admin.COL_3,"admin");
        db.insert(Admin.TABLE_NAME,null,cv);
    }

    public void userTableUpdate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO users (ID, email, password, role) " +
                "SELECT admin_ID, admin_email, admin_password, 'admin' as role FROM admins ");
        db.execSQL("INSERT INTO users (ID, email, password, role) " +
                "SELECT lecturer_ID, lecturer_email, lecturer_password, 'lecturer' AS role FROM lecturers");


    }



    public String userAuthControl(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM users " +
                                "where email = ? and password = ?",new String[] {email,password});

        if(cursor!=null && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("role"));
        }else{
            return "";
        }
    }

    public String getIDfromEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT ID FROM users WHERE email = ?", new String[]{email});
        if(cursor!=null && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("ID"));
        }else{
            return "";
        }
    }

}
