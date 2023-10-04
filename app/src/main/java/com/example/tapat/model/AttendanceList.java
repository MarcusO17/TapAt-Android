package com.example.tapat.model;

import java.util.List;

public class AttendanceList {

    List<Student> studentList;
    List<Integer> attendance;

    public AttendanceList(List<Student> studentList, List<Integer> attendance) {
        this.studentList = studentList;
        this.attendance = attendance;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Integer> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Integer> attendance) {
        this.attendance = attendance;
    }
}
