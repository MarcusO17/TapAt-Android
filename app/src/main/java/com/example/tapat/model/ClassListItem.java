package com.example.tapat.model;

import java.io.Serializable;

public class ClassListItem implements Serializable {
    String className;
    String classID;
    String datetime;

    public ClassListItem(String className, String classID, String datetime) {
        this.className = "Class " + className;
        this.classID = classID;
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
