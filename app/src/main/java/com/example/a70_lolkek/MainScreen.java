package com.example.a70_lolkek;

import static com.example.a70_lolkek.CalendarUtils.daysInMonthArray;
import static com.example.a70_lolkek.CalendarUtils.daysInWeekArray;
import static com.example.a70_lolkek.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity  implements CalendarAdapter.OnItemListener {

    private ImageView settings_button;
    private ImageView calendar_button;
    private ImageView pill_plus;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    BottomNavigationFragment bottomNavigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        initWidgets();
        setWeekView();

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь будет переход в настройки
                Intent intent = new Intent(MainScreen.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь будет переход в календарь
                Intent intent = new Intent(MainScreen.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь добавление лекарства в курс
                AddToCourseActivity.add_to_course = true;
                Intent intent = new Intent(MainScreen.this, AddToCourseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initWidgets() {
        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment)bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }
        settings_button = findViewById(R.id.settings_button);
        calendar_button = findViewById(R.id.calendar_button);
        pill_plus = findViewById(R.id.pill_plus);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
        CalendarUtils.selectedDate = LocalDate.now();
    }
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }
}