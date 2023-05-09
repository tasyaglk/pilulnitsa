package com.example.a70_lolkek;

import java.util.ArrayList;

public class CourseItem {

    private String name;
    private int amount;

    public static ArrayList<CourseItem> course = new ArrayList<>();

    public CourseItem(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}
