package com.example.tapat.model;

public class AttendanceListRowData{

    String attendanceID;
    String studentName;
    boolean attendance;
    String reason;

    public AttendanceListRowData(String studentName, boolean attendance, String reason) {
        this.studentName = studentName;
        this.attendance = attendance;
        this.reason = reason;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isAttendance() {
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
