package com.example.a70_lolkek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    private String name, time_to_take, how_to_take, taking_days, beginning, ending, choose_end_date;
    private int amount, end_days_number, end_pill_number;
    public ArrayList<Integer> choose_days;
    private LocalDate date;
    LocalTime time;
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public Event(String name, String time_to_take, String how_to_take, String taking_days, String beginning, String ending,
                 String choose_end_date, int amount, int end_days_number, int end_pill_number, LocalDate date, ArrayList<Integer> choose_days) {
        this.name = name;
        this.time_to_take = time_to_take;
        this.how_to_take = how_to_take;
        this.taking_days = taking_days;
        this.beginning = beginning;
        this.ending = ending;
        this.choose_end_date = choose_end_date;
        this.amount = amount;
        this.end_days_number = end_days_number;
        this.end_pill_number = end_pill_number;
        this.date = date;
        this.choose_days = choose_days;
    }

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            if (event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
