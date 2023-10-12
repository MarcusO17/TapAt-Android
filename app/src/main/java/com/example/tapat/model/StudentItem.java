package com.example.tapat.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class StudentItem implements Serializable, Parcelable {
    private String studentName;
    private String studentID;

    public StudentItem(String studentName, String studentID) {
        this.studentName = studentName;
        this.studentID = studentID;
    }
    protected StudentItem(Parcel in) {
        studentName = in.readString();
        studentID = in.readString();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(studentName);
        parcel.writeString(studentID);
    }
    public static final Creator<StudentItem> CREATOR = new Creator<StudentItem>() {
        @Override
        public StudentItem createFromParcel(Parcel in) {
            return new StudentItem(in);
        }

        @Override
        public StudentItem[] newArray(int size) {
            return new StudentItem[size];
        }
    };
}
