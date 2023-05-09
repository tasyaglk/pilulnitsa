package com.example.a70_lolkek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class CourseAdapter extends ArrayAdapter<CourseItem> {
    public CourseAdapter(@NonNull Context context, List<CourseItem> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseItem item = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pill_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.pill_cell);

        String amount = null;

        for (Pill pill : Pill.pillBox) {
            if (Objects.equals(pill.getName(), item.getName())) {
                amount = item.getAmount() + " " + pill.getAmount(item.getAmount());
            }
        }

        String pillTitle = item.getName() + ", " + amount;
        eventCellTV.setText(pillTitle);
        return convertView;
    }
}