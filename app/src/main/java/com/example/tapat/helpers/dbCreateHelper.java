package com.example.tapat.helpers;

/**\
 * A Database Wiki, TO reference names, make changes etc.
 */
public class dbCreateHelper {
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

    class Users{
        private static final String DATABASE_NAME = "TapAt.db";
        private static final String TABLE_NAME = "users";
        private static final String COL_1 = "ID";
        private static final String COL_2 = "email";
        private static final String COL_3 = "password";
        private static final String COL_4 = "role";
    }



}
