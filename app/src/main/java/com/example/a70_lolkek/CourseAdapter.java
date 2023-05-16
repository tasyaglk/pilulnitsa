package com.example.a70_lolkek;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pill_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.pill_cell);

        String amount = null;

        for (Pill pill : Pill.pillBox) {
            if (Objects.equals(pill.getName(), item.getName())) {
                amount = item.getAmount() + " " + pill.getAmount(item.getAmount());
            }
        }


// car
        String End = String.valueOf(item.getDate());
        String[] dateParts = End.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        StringBuilder whichDays = new StringBuilder();
        if(Objects.equals(item.getTaking_days(), "Выбрать дни")) {
            List<Integer> days =  item.getChoose_days();
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
            whichDays = new StringBuilder(item.getTaking_days());
        }
        String pillTitle = item.getName() + " " + item.getTime() + " " + amount + "\nПринимать: " +
                whichDays + "\nC " + item.getBeginning() + " по " + day / 10 + day % 10
                + '.' + month / 10 + month % 10 + '.' + year;


        eventCellTV.setText(pillTitle);

        return convertView;
    }


}