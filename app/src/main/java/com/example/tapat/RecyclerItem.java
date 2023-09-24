package com.example.tapat;

// class for each item in recyclerview (student), can be reused for others such as course and lecturer
public class RecyclerItem {
    private String name;
    public RecyclerItem(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
