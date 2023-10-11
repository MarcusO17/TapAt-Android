package com.example.tapat.model;

import java.io.Serializable;

public class ClassListItem implements Serializable {
    String className;
    String classID;

    public ClassListItem(String className, String classID) {
        this.className = className;
        this.classID = classID;
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
