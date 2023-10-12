package com.example.tapat.model;

public class AttendanceListRowData{

    String studentName;
    boolean attendance;
    String reason;

    public AttendanceListRowData(String studentName, boolean attendance, String reason) {
        this.studentName = studentName;
        this.attendance = attendance;
        this.reason = reason;
    }
    public String getAttendanceID(){return attendanceID;}
    public void setAttendanceID(String attendanceID) {this.attendanceID = attendanceID;}

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}