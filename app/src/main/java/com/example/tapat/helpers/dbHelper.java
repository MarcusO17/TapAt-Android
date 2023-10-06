package com.example.tapat.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    /***********************************************************************************************
     **********************************     TABLES!       ******************************************
     ***********************************************************************************************
     */
    private static final String DATABASE_NAME = "TapAt.db";
    private static final int DATABASE_VERSION = 6;

    static class Admin{
        private static final String TABLE_NAME = "admins";
        private static final String COL_1 = "admin_ID";
        private static final String COL_2 = "admin_email";
        private static final String COL_3 = "admin_password";
    }

    static class Lecturer{
        private static final String TABLE_NAME = "lecturers";
        private static final String COL_1 = "lecturer_ID";
        private static final String COL_2 = "lecturer_name";
        private static final String COL_3 = "lecturer_email";
        private static final String COL_4 = "lecturer_password";
    }

    static class Student{
        private static final String TABLE_NAME = "students";
        private static final String COL_1 = "student_ID";
        private static final String COL_2 = "student_name";
        private static final String COL_3 = "programme_code";
    }

    static class Course{
        private static final String TABLE_NAME = "courses";
        private static final String COL_1 = "course_ID";
        private static final String COL_2 = "lecturer_ID";
        private static final String COL_3 = "course_name";
        private static final String COL_4 = "programme_code";
    }

    static class Attendance{
        private static final String TABLE_NAME = "attendance";
        private static final String COL_1 = "attendance_ID";
        private static final String COL_2 = "course_ID";
        private static final String COL_3 = "datetime";

    }
    static class AttendanceStudents{
        private static final String TABLE_NAME = "attendance_students";
        private static final String COL_1 = "attendance_ID";
        private static final String COL_2= "course_ID";
        private static final String COL_3 = "attendance_status";
        private static final String COL_4 = "reason";
    }

    static class CourseStudents{
        private static final String TABLE_NAME = "course_students";
        private static final String COL_1= "course_ID";
        private static final String COL_2 = "student_ID";


    }


    /***********************************************************************************************
     ****************************     DB INITIALISATION       **************************************
     ***********************************************************************************************
     */



    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating Admin Table
        db.execSQL("CREATE TABLE " + Admin.TABLE_NAME+ " ( "
                + Admin.COL_1 + " TEXT PRIMARY KEY, "
                + Admin.COL_2 + " TEXT  NOT NULL,"
                + Admin.COL_3 + " TEXT  NOT NULL"
                + " )");

        //Creating Lecturer Table
        db.execSQL("CREATE TABLE " + Lecturer.TABLE_NAME+ " ( "
                + Lecturer.COL_1 + " TEXT PRIMARY KEY, "
                + Lecturer.COL_2 + " TEXT NOT NULL,"
                + Lecturer.COL_3 + " TEXT  NOT NULL,"
                + Lecturer.COL_4 + " TEXT  NOT NULL"
                + " )");

        //Creating Student Table
        db.execSQL("CREATE TABLE " + Student.TABLE_NAME+ " ( "
                + Student.COL_1 + " TEXT PRIMARY KEY NOT NULL, "
                + Student.COL_2 + " TEXT NOT NULL,"
                + Student.COL_3 + " TEXT NOT NULL"
                + " )");

        //Creating Course Table
        db.execSQL("CREATE TABLE " + Course.TABLE_NAME+ " ( "
                + Course.COL_1 + " TEXT PRIMARY KEY NOT NULL, "
                + Course.COL_2 + " TEXT  NOT NULL,"
                + Course.COL_3 + " TEXT NOT NULL,"
                + Course.COL_4 + " TEXT NOT NULL"
                + " )");

        //Creating Attendance Table
        db.execSQL("CREATE TABLE " + Attendance.TABLE_NAME+ " ( "
                + Attendance.COL_1 + " TEXT PRIMARY KEY NOT NULL, "
                + Attendance.COL_2 + " TEXT NOT NULL,"
                + Attendance.COL_3 + " DATETIME NOT NULL"
                + " )");

        //Creating AttendanceStudents Table
        db.execSQL("CREATE TABLE " + AttendanceStudents.TABLE_NAME+ " ( "
                + AttendanceStudents.COL_1 + " TEXT PRIMARY KEY NOT NULL, "
                + AttendanceStudents.COL_2 + " TEXT NOT NULL,"
                + AttendanceStudents.COL_3 + " TEXT NOT NULL,"
                + AttendanceStudents.COL_4 + " TEXT NOT NULL"
                + " )");

        //Creating CourseStudents Table
        db.execSQL("CREATE TABLE " + CourseStudents.TABLE_NAME+ " ( "
                + CourseStudents.COL_1 + " TEXT PRIMARY KEY NOT NULL, "
                + CourseStudents.COL_2 + " TEXT NOT NULL"
                + " )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Admin.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Lecturer.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Attendance.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AttendanceStudents.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CourseStudents.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
        insertAdmin(db);
    }



    /***********************************************************************************************
     ********************************     DB METHODS     ******************************************
     ***********************************************************************************************
     */
    /**
     *  Inserts Admin Account into DB
     *
     */
    public void insertAdmin(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(Admin.COL_1,"A0001");
        cv.put(Admin.COL_2, "admin1");
        cv.put(Admin.COL_3,"admin");
        db.insert(Admin.TABLE_NAME,null,cv);
    }



    public boolean userAuthControl(String table,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM "+ table + " "+
                                "where email = ? and password = ?",new String[] {email,password});

        if(cursor!=null && cursor.getCount()>0) {
            return true;
        }else{
            return false;
        }
    }

    public String getIDfromEmail(String table,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT ID FROM "+ table + " WHERE email = ?", new String[]{email});
        if(cursor!=null && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("ID"));
        }else{
            return "";
        }
    }

    public String userAuthorization(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String validSessionID = "";
        try {
            db.execSQL("CREATE VIEW users AS SELECT admin_ID as ID, admin_email as email, admin_password as password, 'admin' as role FROM admins " +
                    "UNION ALL " +
                    "SELECT lecturer_ID as ID,lecturer_email as email, lecturer_password as password, 'lecturer' as role FROM lecturers");
        }catch (SQLiteException e){}
        if(userAuthControl("users",email,password)){
            validSessionID = getIDfromEmail("users",email);
            db.execSQL("DROP VIEW users");
            return validSessionID;
        }else{
            return "";
        }


    }



}
