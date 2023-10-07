package com.example.tapat.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

public class dbHelper extends SQLiteOpenHelper {

    /***********************************************************************************************
     **********************************     TABLES!       ******************************************
     ***********************************************************************************************
     */
    private static final String DATABASE_NAME = "TapAt.db";
    private static final int DATABASE_VERSION = 7;

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
                + Student.COL_1 + " TEXT PRIMARY KEY, "
                + Student.COL_2 + " TEXT NOT NULL,"
                + Student.COL_3 + " TEXT NOT NULL"
                + " )");

        //Creating Course Table
        db.execSQL("CREATE TABLE " + Course.TABLE_NAME+ " ( "
                + Course.COL_1 + " TEXT PRIMARY KEY, "
                + Course.COL_2 + " TEXT  NOT NULL,"
                + Course.COL_3 + " TEXT NOT NULL,"
                + Course.COL_4 + " TEXT NOT NULL,"
                + " FOREIGN KEY (lecturer_ID) REFERENCES lecturers(lecturer_ID)"
                + " )");

        //Creating Attendance Table
        db.execSQL("CREATE TABLE " + Attendance.TABLE_NAME+ " ( "
                + Attendance.COL_1 + " TEXT PRIMARY KEY, "
                + Attendance.COL_2 + " TEXT NOT NULL,"
                + Attendance.COL_3 + " DATETIME NOT NULL,"
                + " FOREIGN KEY (course_ID) REFERENCES courses(course_ID)"
                + " )");

        //Creating AttendanceStudents Table
        db.execSQL("CREATE TABLE " + AttendanceStudents.TABLE_NAME+ " ( "
                + AttendanceStudents.COL_1 + " TEXT NOT NULL, "
                + AttendanceStudents.COL_2 + " TEXT NOT NULL,"
                + AttendanceStudents.COL_3 + " TEXT NOT NULL,"
                + AttendanceStudents.COL_4 + " TEXT NOT NULL,"
                + " FOREIGN KEY (attendance_ID) REFERENCES attendance(attendance_ID),"
                + " FOREIGN KEY (course_ID) REFERENCES courses(course_ID)"
                + " )");

        //Creating CourseStudents Table
        db.execSQL("CREATE TABLE " + CourseStudents.TABLE_NAME+ " ( "
                + CourseStudents.COL_1 + " TEXT NOT NULL, "
                + CourseStudents.COL_2 + " TEXT NOT NULL,"
                + " FOREIGN KEY (course_ID) REFERENCES courses(course_ID),"
                + " FOREIGN KEY (student_ID) REFERENCES students(student_ID)"
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

    public String userAuthorization(String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String validSessionID = "";
        try {
            db.execSQL("CREATE VIEW users AS SELECT admin_ID as ID, admin_email as email, admin_password as password, 'admin' as role FROM admins " +
                    "UNION ALL " +
                    "SELECT lecturer_ID as ID,lecturer_email as email, lecturer_password as password, 'lecturer' as role FROM lecturers");
        } catch (SQLiteException e) {
        }
        if (userAuthControl("users", email, password)) {
            validSessionID = getIDfromEmail("users", email);
            db.execSQL("DROP VIEW users");
            return validSessionID;
        } else {
            return "";
        }


    }

    /**
     * Get Student Names
     * @return List of Students (Array)
     */
    public String[] getNames(String className){
        List<String> namesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(className == "Students") {
            cursor = db.rawQuery("SELECT student_name FROM students", null);
        } else if (className == "Lecturers") {
            cursor = db.rawQuery("SELECT lecturer_name FROM lecturers", null);
        } else if (className == "Courses") {
            cursor = db.rawQuery("SELECT course_ID,course_name FROM courses", null);
        }else{
            return namesList.toArray(new String[]{});
        }
        if(cursor != null){
                switch (className) {
                    case "Students":
                        while (cursor.moveToNext()) {
                            namesList.add(cursor.getString(cursor.getColumnIndex("student_name")));
                        }
                        break;
                    case "Lecturers":
                        while (cursor.moveToNext()) {
                            namesList.add(cursor.getString(cursor.getColumnIndex("lecturer_name")));
                        }
                        break;
                    case "Courses":
                        while (cursor.moveToNext()) {
                            String courseID = cursor.getString(cursor.getColumnIndex("course_ID"));
                            String courseName = cursor.getString(cursor.getColumnIndex("course_name"));
                            namesList.add(courseID + " : " + courseName);
                        }
                        break;
                }
            cursor.close();
            }
        //if cursor gets results
        return namesList.toArray(new String[]{});
    }

}





