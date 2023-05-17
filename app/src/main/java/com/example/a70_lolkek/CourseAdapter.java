package com.example.a70_lolkek;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CourseAdapter extends ArrayAdapter<Event> {
    public CourseAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event item = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_item, parent, false);

        String amount_str = null;

        for (Pill pill : Pill.pillBox) {
            if (Objects.equals(pill.getName(), item.getName())) {
                amount_str = item.getAmount() + " " + pill.getAmount(item.getAmount());
            }
        }

        String End = "";
        int year, month, day;
        End = String.valueOf(item.getDate());
        String[] dateParts = End.split("-");
        year = Integer.parseInt(dateParts[0]);
        month = Integer.parseInt(dateParts[1]);
        day = Integer.parseInt(dateParts[2]);

        StringBuilder whichDays = new StringBuilder();

        if(Objects.equals(item.getTaking_days(), "Выбрать дни")) {
            List<Integer> days =  item.getChoose_days();
            Collections.sort(days);
            for(Integer i : days) {
                if(i == 0) {
                    whichDays.append("Пн ");
                }
                if(i == 1) {
                    whichDays.append("Вт ");
                }
                if(i == 2) {
                    whichDays.append("Ср ");
                }
                if(i == 3) {
                    whichDays.append("Чт ");
                }
                if(i == 4) {
                    whichDays.append("Пт ");
                }
                if(i == 5) {
                    whichDays.append("Сб ");
                }
                if(i == 6) {
                    whichDays.append("Вс ");
                }
            }
        } else {
            whichDays = new StringBuilder(item.getTaking_days().toLowerCase());
        }
        String time = String.valueOf(item.getTime());
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int min  = Integer.parseInt(timeParts[1]);
        String pillTitle = item.getName() + " " + hours / 10 + hours % 10 + ":" + min / 10 + min % 10 ;
        String days_str = "Дни приема: " + whichDays;
        String from = "C " + item.getBeginning();
        String until = "по " + day / 10 + day % 10 + '.' + month / 10 + month % 10 + '.' + year;

        TextView name_time = convertView.findViewById(R.id.name_time);
        TextView amount = convertView.findViewById(R.id.amount);
        TextView days = convertView.findViewById(R.id.days);
        TextView food = convertView.findViewById(R.id.food);
        TextView date_start = convertView.findViewById(R.id.date_start);
        TextView date_end = convertView.findViewById(R.id.date_end);


        name_time.setText(pillTitle);
        amount.setText(amount_str);
        days.setText(days_str);
        food.setText(item.getHow_to_take());
        date_start.setText(from);
        Log.d("end", until);
        date_end.setText(until);

        return convertView;
    }


}