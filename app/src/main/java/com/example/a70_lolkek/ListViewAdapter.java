package com.example.a70_lolkek;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Pill> pillList = null;
    private ArrayList<Pill> arraylist;

    public ListViewAdapter(Context context, List<Pill> pillList) {
        mContext = context;
        this.pillList = pillList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(pillList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return pillList.size();
    }

    @Override
    public Pill getItem(int position) {
        return pillList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(pillList.get(position).getName());
        return view;
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
                }
            }
        }
        notifyDataSetChanged();
    }
}
