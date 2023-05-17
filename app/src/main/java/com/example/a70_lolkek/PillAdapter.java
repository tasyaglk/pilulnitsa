package com.example.a70_lolkek;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class PillAdapter extends ArrayAdapter<Pill> {

    private List<Pill> pillList;
    private ArrayList<Pill> arraylist;

    public PillAdapter(@NonNull Context context, List<Pill> events) {
        super(context, 0, events);
        this.pillList = events;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(pillList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pill pill = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pill_cell, parent, false);

        TextView name = convertView.findViewById(R.id.name);
        ImageView image = convertView.findViewById(R.id.pill_image);
        TextView amount = convertView.findViewById(R.id.amount);
        TextView best_before = convertView.findViewById(R.id.best_before);

        String pillTitle = pill.getTabletsAmount() + pill.getAmount(pill.getTabletsAmount());
        String best = "годен до: " + pill.getBestBefore();

        name.setText(pill.getName());
        
        switch (pill.getDosageType()) {
            case "таблетка":
                image.setImageResource(R.drawable.tablet);
                break;
            case "капля":
                image.setImageResource(R.drawable.drops);
                break;
            case "ложка":
                image.setImageResource(R.drawable.teaspoon);
                break;
            case "шприц":
                image.setImageResource(R.drawable.syringe);
                break;
            default:
                image.setImageResource(R.drawable.more);
        }

        amount.setText(pillTitle);
        best_before.setText(best);
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        pillList.clear();
        if (charText.length() == 0) {
            pillList.addAll(arraylist);
            pillList.sort(Comparator.comparing(Pill::getName));
        } else {
            for (Pill wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    pillList.add(wp);
                    Log.d("pills", wp.getName());
                }
            }
        }
        notifyDataSetChanged();
    }

}