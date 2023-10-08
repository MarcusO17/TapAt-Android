package com.example.tapat.model;

import java.io.Serializable;

// class for each item in recyclerview (student), can be reused for others such as course and lecturer
public class CourseItem implements Serializable {
    private String name;
    private String courseCode;
    public CourseItem(String name, String courseCode) {
        this.name = name;
        this.courseCode = courseCode;
    }
    public String getName() {
        return name;
    }
    public String getCourseCode() {
        return courseCode;
    }
}