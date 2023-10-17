package com.example.tapat.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tapat.model.AttendanceListRowData;
import com.example.tapat.model.ClassListItem;
import com.example.tapat.model.CourseItem;
import com.example.tapat.model.StudentItem;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {

    /***********************************************************************************************
     **********************************     TABLES!       ******************************************
     ***********************************************************************************************
     */
    private static final String DATABASE_NAME = "TapAt.db";
    private static final int DATABASE_VERSION = 9;

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
        private static final String COL_2= "student_ID";
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
                + Admin.COL_1 + " TEXT PRIMARY KEY CHECK (admin_ID like 'A%'),"
                + Admin.COL_2 + " TEXT  NOT NULL UNIQUE,"
                + Admin.COL_3 + " TEXT  NOT NULL"
                + " )");

        //Creating Lecturer Table
        db.execSQL("CREATE TABLE " + Lecturer.TABLE_NAME+ " ( "
                + Lecturer.COL_1 + " TEXT PRIMARY KEY CHECK (lecturer_ID like 'L%') "
                + Lecturer.COL_2 + " TEXT NOT NULL,"
                + Lecturer.COL_3 + " TEXT  NOT NULL UNIQUE,"
                + Lecturer.COL_4 + " TEXT  NOT NULL"
                + " )");

        //Creating Student Table
        db.execSQL("CREATE TABLE " + Student.TABLE_NAME+ " ( "
                + Student.COL_1 + " TEXT PRIMARY KEY CHECK (student_ID like 'P%'),"
                + Student.COL_2 + " TEXT NOT NULL,"
                + Student.COL_3 + " TEXT NOT NULL"
                + " )");

        //Creating Course Table
        db.execSQL("CREATE TABLE " + Course.TABLE_NAME+ " ( "
                + Course.COL_1 + " TEXT PRIMARY KEY CHECK (course_ID like 'C%')"
                + Course.COL_2 + " TEXT  NOT NULL,"
                + Course.COL_3 + " TEXT NOT NULL,"
                + Course.COL_4 + " TEXT NOT NULL,"
                + " FOREIGN KEY (lecturer_ID) REFERENCES lecturers(lecturer_ID)"
                + " )");

        //Creating Attendance Table
        db.execSQL("CREATE TABLE " + Attendance.TABLE_NAME+ " ( "
                + Attendance.COL_1 + " TEXT PRIMARY KEY CHECK (attendance_ID like 'AT%' "
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
                + " FOREIGN KEY (student_ID) REFERENCES students(student_ID)"
                + " )");

        //Creating CourseStudents Table
        db.execSQL("CREATE TABLE " + CourseStudents.TABLE_NAME+ " ( "
                + CourseStudents.COL_1 + " TEXT NOT NULL, "
                + CourseStudents.COL_2 + " TEXT NOT NULL,"
                + " FOREIGN KEY (course_ID) REFERENCES courses(course_ID),"
                + " FOREIGN KEY (student_ID) REFERENCES students(student_ID)"
                + " )");
        insertAdmin(db);

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
        //db.execSQL("DROP TABLE IF EXISTS users");
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
            cursor.close();
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
            cursor = db.rawQuery("SELECT student_ID,student_name FROM students", null);
        } else if (className == "Lecturers") {
            cursor = db.rawQuery("SELECT lecturer_ID,lecturer_name FROM lecturers", null);
        } else if (className == "Courses") {
            cursor = db.rawQuery("SELECT course_ID,course_name FROM courses", null);
        }else{
            return namesList.toArray(new String[]{});
        }
        if(cursor != null){
            switch (className) {
                case "Students":
                    while (cursor.moveToNext()) {
                        String studentID = cursor.getString(cursor.getColumnIndex("student_ID"));
                        String studentName = cursor.getString(cursor.getColumnIndex("student_name"));
                        namesList.add(studentID + " : " + studentName);
                    }
                    break;
                case "Lecturers":
                    while (cursor.moveToNext()) {
                        String lecturerID = cursor.getString(cursor.getColumnIndex("lecturer_ID"));
                        String lecturerName = cursor.getString(cursor.getColumnIndex("lecturer_name"));
                        namesList.add(lecturerID + " : " + lecturerName);
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

    public String[] getID(String className){
        List<String> idList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        switch(className){
            case "Student":{
                cursor = db.rawQuery("SELECT student_ID FROM students", null);
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        idList.add(cursor.getString(cursor.getColumnIndex("student_ID")));
                    }
                }
                break;
            }
            case "Lecturer":{
                cursor = db.rawQuery("SELECT lecturer_ID FROM lecturers", null);
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        idList.add(cursor.getString(cursor.getColumnIndex("lecturer_ID")));
                    }
                }
                break;
            }
            case "Course":{
                cursor = db.rawQuery("SELECT course_ID FROM students", null);
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        idList.add(cursor.getString(cursor.getColumnIndex("course_ID")));
                    }
                }
                break;
            }
            default:{
                return new String[]{};
            }
        }
        return idList.toArray(new String[]{});
    }
    public boolean insertStudentData(String[] student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Student.COL_1,student[0]); //Insert Student ID
        cv.put(Student.COL_2,student[1]); //Insert Student Name
        cv.put(Student.COL_3,student[2]); //Insert Student Programme
        try{
            long result = db.insert(Student.TABLE_NAME,null,cv);
            return result != -1;
        }catch(SQLiteException e){
            return false;
        }
    }

    public boolean insertLecturerData(String[] lecturer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Lecturer.COL_1,lecturer[0]); //Insert Lecturer ID
        cv.put(Lecturer.COL_2,lecturer[1]); //Insert Lecturer Name
        cv.put(Lecturer.COL_3,lecturer[2]); //Insert Lecturer Email
        cv.put(Lecturer.COL_4,lecturer[3]);//Insert Lecturer Password
        try{
            long result = db.insert(Lecturer.TABLE_NAME,null,cv);
            return result != -1;
        }catch(SQLiteException e){
            return false;
        }
    }

    public boolean insertCourseData(String[] course){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Course.COL_1,course[0]); //Insert Lecturer ID
        cv.put(Course.COL_2,course[1]); //Insert Lecturer Name
        cv.put(Course.COL_3,course[2]); //Insert Lecturer Email
        cv.put(Course.COL_4,course[3]);//Insert Lecturer Password
        try{
            long result = db.insert(Course.TABLE_NAME,null,cv);
            return result != -1;
        }catch(SQLiteException e){
            return false;
        }
    }

    public String[] getSingularData(String className, String buttonName){
        List<String> rowInfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        switch(className){
            case "Student":{
                buttonName = buttonName.split(":")[0].trim();
                cursor = db.rawQuery("SELECT * FROM students where student_ID = ? ", new String[] {buttonName});
                if(cursor!=null && cursor.moveToFirst())
                    for(int i= 0; i < cursor.getColumnCount(); i++)
                        rowInfo.add(cursor.getString(i));

                break;
            }
            case "Lecturer":{
                buttonName = buttonName.split(":")[0].trim();
                cursor = db.rawQuery("SELECT * FROM lecturers where lecturer_ID = ? ", new String[] {buttonName});
                if(cursor!=null && cursor.moveToFirst()){
                    for(int i= 0; i < cursor.getColumnCount(); i++)
                        rowInfo.add(cursor.getString(i));
                }
                break;
            }
            case "Course":{
                buttonName = buttonName.split(":")[0].trim();
                cursor = db.rawQuery("SELECT * FROM courses where course_ID = ? ", new String[] {buttonName});
                if(cursor!=null && cursor.moveToFirst())
                    for(int i= 0; i < cursor.getColumnCount(); i++)
                        rowInfo.add(cursor.getString(i));
            }
            break;
            default:{
                return new String[]{};
            }
        }
        return rowInfo.toArray(new String[]{});
    }

    public boolean updateStudentData(String[] student,String target){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(Student.COL_1,student[0]); //Insert Student ID
        cv.put(Student.COL_2,student[1]); //Insert Student Name
        cv.put(Student.COL_3,student[2]); //Insert Student Programme
        target = target.split(":")[1].trim();
        try {
            long result = db.update(Student.TABLE_NAME,cv,"student_name=?", new String[]{target});
            return result != -1;
        }catch(SQLiteException e){
            return false;
        }
    }
    public boolean updateLecturerData(String[] lecturer,String target){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Lecturer.COL_1,lecturer[0]); //Insert Lecturer ID
        cv.put(Lecturer.COL_2,lecturer[1]); //Insert Lecturer Name
        cv.put(Lecturer.COL_3,lecturer[2]); //Insert Lecturer Email
        cv.put(Lecturer.COL_4,lecturer[3]);//Insert Lecturer Password
        target = target.split(":")[1].trim();
        try {
            long result = db.update(Lecturer.TABLE_NAME,cv,"lecturer_name=?", new String[]{target});
            return result != -1;
        }catch(SQLiteException e){
            return false;
        }
    }

    public boolean updateCourseData(String[] course,String target){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Course.COL_1,course[0]); //Insert Course ID
        cv.put(Course.COL_2,course[1]); //Insert Lecturer ID
        cv.put(Course.COL_3,course[2]); //Insert Course ID
        cv.put(Course.COL_4,course[3]);//Insert Programme Code
        target = target.split(":")[1].trim();
        try {
            long result = db.update(Course.TABLE_NAME, cv, "course_name=?", new String[]{target});
            return result != -1;
        }catch(SQLiteException e){
            return false;
        }
    }

    /**
     * WIP
     */
        /*
    public String[] getProgrammeData() {
        List<String> rowInfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;


        cursor = db.rawQuery("SELECT DISTINCT(programme_code) FROM students",null);
        if (cursor != null && cursor.moveToFirst())
            for (int i = 0; i < cursor.getColumnCount(); i++)
                rowInfo.add(cursor.getString(i));

        return  rowInfo.toArray(new String[]{});
    }
    */

    public List<StudentItem> getStudents() {
        List<StudentItem> studentData = new ArrayList<>();

        String studentID = "";
        String studentName = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;


        cursor = db.rawQuery("SELECT * FROM students", null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                studentID = cursor.getString(cursor.getColumnIndex("student_ID"));
                studentName = cursor.getString(cursor.getColumnIndex("student_name"));
                studentData.add(new StudentItem(studentName,studentID));
            }
        }

        return studentData;
    }

    public List<StudentItem> getCourseStudents(String courseID) {
        List<String> studentIDData = new ArrayList<>();
        List<StudentItem> studentData = new ArrayList<>();

        String studentCourseID = "";
        String studentName = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;


        cursor = db.rawQuery("SELECT student_ID FROM course_students where course_ID = ?", new String[]{courseID});
        if(cursor!=null){
            while(cursor.moveToNext()){
                studentCourseID = cursor.getString(cursor.getColumnIndex("student_ID"));
                studentIDData.add(studentCourseID);
            }
        }
        for(String studentID: studentIDData ){
            studentData.add(new StudentItem(getNamefromID("Student",studentID),studentID));
        }
        return studentData;
    }

    public List<CourseItem> getCourses(String lecturerID){
        List<CourseItem> courseData = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM courses where lecturer_ID = ? ", new String[]{lecturerID});
        if(cursor!=null){
            while(cursor.moveToNext()){
                String courseID = cursor.getString(cursor.getColumnIndex("course_ID"));
                String courseName = cursor.getString(cursor.getColumnIndex("course_name"));
                courseData.add(new CourseItem(courseName,courseID));
            }
        }

        return courseData;
    }

    public List<ClassListItem> getClasses(String courseID){
        List<ClassListItem> classData = new ArrayList<>();
        int classCount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT attendance_ID FROM attendance where course_ID = ? ORDER BY datetime", new String[]{courseID});
        }catch(SQLiteException e){
            Log.e("Query Failed : ", e.toString());
            return classData;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String attendanceID = cursor.getString(cursor.getColumnIndex("attendance_ID"));
                classCount += 1;
                classData.add(new ClassListItem(Integer.toString(classCount), attendanceID));
            }
        }


        return classData;
    }

    public String getNamefromID(String className,String ID) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        switch (className) {
            case "Student": {
                cursor = db.rawQuery("SELECT * FROM students where student_ID = ? ", new String[]{ID});
                if (cursor != null && cursor.moveToFirst())
                    name = cursor.getString(cursor.getColumnIndex("student_name"));

                break;
            }
            case "Lecturer": {
                cursor = db.rawQuery("SELECT * FROM lecturers where lecturer_ID = ? ", new String[]{ID});
                if (cursor != null && cursor.moveToFirst()) {
                    name = cursor.getString(cursor.getColumnIndex("lecturer_name"));
                }
                break;
            }
            /*
            case "Course": {
                 ID = split(":")[0].trim();
                cursor = db.rawQuery("SELECT * FROM courses where course_ID = ? ", new String[]{ID});
                if (cursor != null && cursor.moveToFirst())
                   name = cursor.getString(cursor.getColumnIndex("course_name"));
            }
            break;
            */

            default: {
                return name;
            }
        }
        return name;
    }

    public void populateCourseStudents() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT COUNT(*) FROM course_students",null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0) > 0)
                db.execSQL("DELETE FROM course_students");
        }

        //If student_ID (PC (programme_code)) is = to course_ID (PC). then inner join respectively
        db.execSQL("INSERT INTO course_students (student_ID, course_ID) " +
                "SELECT s.student_ID, c.course_ID " +
                "FROM students s " +
                "JOIN courses c ON s.programme_code = c.programme_code " +
                "WHERE s.programme_code IN ( " +
                "    SELECT DISTINCT programme_code FROM students " +
                ") " +
                "AND c.programme_code IN ( " +
                "    SELECT DISTINCT programme_code FROM courses " + ");");
    }

    public void insertAttendanceStudentsData(ArrayList<AttendanceListRowData> attendanceList){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for(AttendanceListRowData attendanceStudent : attendanceList){
            ContentValues cv = new ContentValues();
            cv.put(AttendanceStudents.COL_1, attendanceStudent.getAttendanceID());
            cv.put(AttendanceStudents.COL_2, attendanceStudent.getStudentID());
            cv.put(AttendanceStudents.COL_3, attendanceStudent.getAttendance());
            cv.put(AttendanceStudents.COL_4, attendanceStudent.getReason());

            db.insert(AttendanceStudents.TABLE_NAME, null, cv);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public boolean insertAttendanceData(String[] attendanceRow){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Attendance.COL_1,attendanceRow[0]); //Insert
        cv.put(Attendance.COL_2,attendanceRow[1]); //Insert
        cv.put(Attendance.COL_3,attendanceRow[2]); //Insert
        try {
            long result = db.insert(Attendance.TABLE_NAME,null, cv);
            return result != -1;
        }catch(SQLiteException e){
            return false;
        }
    }

    public ArrayList<AttendanceListRowData> getPastAttendanceData (String classID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AttendanceListRowData> attendanceList = new ArrayList<AttendanceListRowData>();
        Cursor cursor = null;
        Cursor cursorExtra =null;

        String sqlQuery = "SELECT a.attendance_ID, a.student_ID, a.attendance_status, a.reason, s.student_name " +
                "FROM attendance_students a " +
                "INNER JOIN students s ON a.student_ID = s.student_ID " +
                "WHERE a.attendance_ID = ?";

        cursor = db.rawQuery(sqlQuery, new String[] { classID });

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String attendanceID = cursor.getString(cursor.getColumnIndex("attendance_ID"));
                String studentID = cursor.getString(cursor.getColumnIndex("student_ID"));
                String studentName = cursor.getString(cursor.getColumnIndex("student_name"));
                boolean attendanceStatus = cursor.getInt(cursor.getColumnIndex("attendance_status")) > 0;
                String reason = cursor.getString(cursor.getColumnIndex("reason"));

                attendanceList.add(new AttendanceListRowData(attendanceID, studentID, studentName, attendanceStatus, reason));
            }
            cursor.close();
        }
        return attendanceList;

    }


}









