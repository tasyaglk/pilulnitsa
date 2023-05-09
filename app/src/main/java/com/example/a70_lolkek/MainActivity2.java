package com.example.a70_lolkek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView calendarRecyclerView;
    private GridLayoutManager layoutManager;
    private CalendarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        layoutManager = new GridLayoutManager(this, 7, RecyclerView.VERTICAL, false);
        calendarRecyclerView.setLayoutManager(layoutManager);

        adapter = new CalendarAdapter();
        calendarRecyclerView.setAdapter(adapter);

        // Установка отображаемой недели
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        adapter.setStartDate(calendar.getTime());
    }

    private static class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

        private final List<Date> dates = new ArrayList<>();
        private Date startDate;


        @NonNull
        @Override
        public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
            Date date = dates.get(position);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.getDefault());
            holder.calendarDayTextView.setText(sdf.format(date));
        }

        @Override
        public int getItemCount() {
            return dates.size();
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            for (int i = 0; i < 7; i++) {
                dates.add(calendar.getTime());
                calendar.add(Calendar.DATE, 1);
            }
            notifyDataSetChanged();
        }
    }

    private static class CalendarViewHolder extends RecyclerView.ViewHolder {

        private final TextView calendarDayTextView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            calendarDayTextView = itemView.findViewById(R.id.calendar_day_text_view);
        }
    }
}
