package com.example.a70_lolkek;

import java.util.ArrayList;
import java.util.Objects;

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

    public static boolean checkInCourse(String name) {
        for (CourseItem item : course) {
            if (Objects.equals(name, item.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void deleteFromCourse(String name) {
        course.removeIf(item -> Objects.equals(name, item.getName()));
    }
}
