package com.example.a70_lolkek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class EventDay {

    //This is for a single day mood
    Calendar calendar = Calendar.getInstance();

    public EventDay() {
    }

    public EventDay(Date date) {
        //don't forget this line
        calendar.setTime(date);
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public Long getCalendarTimeStamp() {
        return calendar.getTime().getTime();
    }

    /**
     * @return integer so that we can use for dividing the portions in circularStatusView
     */
    public ArrayList<Integer> getMoods() {
        ArrayList<Integer> moods = new ArrayList<>();
        Random random = new Random();
        int size = random.nextInt(6);
        for (int i = 0; i < size; i++) {
            moods.add(random.nextInt(5) + 1);
        }
        return moods;
    }
}