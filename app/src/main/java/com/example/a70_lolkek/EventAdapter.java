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

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String amount = null;


        for (Pill pill : Pill.pillBox) {
            if (Objects.equals(pill.getName(), event.getName())) {
                amount = event.getAmount() + pill.getAmount(event.getAmount());
            }
        }

        String time = String.valueOf(event.getTime());
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int min  = Integer.parseInt(timeParts[1]);

        String eventTitle = event.getName() + ", " + amount + ", " + hours / 10 + hours % 10 + ":" + min / 10 + min % 10;
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}