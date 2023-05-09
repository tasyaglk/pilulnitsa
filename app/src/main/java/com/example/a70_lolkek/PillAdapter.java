package com.example.a70_lolkek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PillAdapter extends ArrayAdapter<Pill>
{
    public PillAdapter(@NonNull Context context, List<Pill> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Pill pill = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pill_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.pill_cell);

        String pillTitle = pill.getName() + "      " + pill.getAmount() + "\nгоден до: " + pill.getBestBefore();
        eventCellTV.setText(pillTitle);
        return convertView;
    }

}
