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

    class Admin{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "admins";
        private static final String COL_1 = "admin_ID";
        private static final String COL_2 = "admin_email";
        private static final String COL_3 = "admin_password";
    }

    class Lecturer{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "lecturers";
        private static final String COL_1 = "lecturer_ID";
        private static final String COL_2 = "lecturer_email";
        private static final String COL_3 = "lecturer_password";
        private static final String COL_4 = "lecturer_name";
        private static final String COL_5 = "course_list";
    }

    class Student{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "students";
        private static final String COL_1 = "student_ID";
        private static final String COL_2 = "student_name";
        private static final String COL_3 = "programme_ID";
        private static final String COL_4 = "programme_name";
    }

    class Course{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "courses";
        private static final String COL_1 = "course_ID";
        private static final String COL_4 = "lecturer_ID";
        private static final String COL_2 = "course_name";
        private static final String COL_3 = "student_list";
    }

    public dbHelper(Context context) {
        super(context, Admin.TABLE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Admin.TABLE_NAME+ " ( "
                + Admin.COL_1 + " int PRIMARY KEY NOT NULL, "
                + Admin.COL_2 + " varchar(255) NOT NULL UNIQUE, "
                + Admin.COL_3 + " varchar(255) NOT NULL"
                + " )");
        db.execSQL("CREATE TABLE " + Lecturer.TABLE_NAME+ " ( "
                + Lecturer.COL_1 + " int PRIMARY KEY NOT NULL, "
                + Lecturer.COL_2 + " varchar(255) NOT NULL UNIQUE, "
                + Lecturer.COL_3 + " varchar(255) NOT NULL"
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
        cv.put("adminID","0001");
        cv.put("adminEmail", "admin1");
        cv.put("adminPassword","admin");
        db.insert(Admin.TABLE_NAME,null,cv);
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

        cursor = db.rawQuery("SELECT * FROM admins where admin_email = ? AND admin_password = ?",new String[] {e,p});
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
        Cursor cursor = db.rawQuery("Select email from admins where email = ?", new String[] {email});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public String userAuthControl(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String role = "";
        int roleCursor = 0;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM (SELECT CONCAT(admin_email, lecturer_email) as email, CONCAT(admin_password, lecturer_password) as password, 'admin' as role FROM admins " +
                                "UNION ALL " +
                                "SELECT CONCAT(lecturer_email, admin_email) as email, CONCAT(lecturer_password, admin_password) as password, 'lecturer' as role FROM lecturers) " +
                                "where email = ? AND password = ?",new String[] {email,password});

        if(cursor!=null && cursor.moveToFirst()) {
            roleCursor = cursor.getColumnIndex("role");
            role = cursor.getString(roleCursor);
            return role;
        }else{
            return "";
        }
    }

}
