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
    private static final String DATABASE_NAME = "TapAt.db";
    private static final int DATABASE_VERSION = 1;

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
                + Lecturer.COL_3 + " TEXT  NOT NULL"
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
                + AttendanceStudents.COL_3 + " TEXT NOT NULL"
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
        onCreate(db);
    }



    /***********************************************************************************************
     ********************************     DB METHODS     ******************************************
     ***********************************************************************************************
     */
    /**
     *  Inserts Admin Account into DB
     *
     */
    public void insertAdmin() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Admin.COL_1,"A0001");
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



    public String userAuthControl(String table,String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM ? " +
                                "where email = ? and password = ?",new String[] {table,email,password});

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
