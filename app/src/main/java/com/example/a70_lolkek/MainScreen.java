package com.example.a70_lolkek;

import static com.example.a70_lolkek.CalendarUtils.daysInMonthArray;
import static com.example.a70_lolkek.CalendarUtils.daysInWeekArray;
import static com.example.a70_lolkek.CalendarUtils.formattedDate;
import static com.example.a70_lolkek.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        SharedPreferences sharedPreferences = getSharedPreferences("Pills", MODE_PRIVATE);
        int size = sharedPreferences.getInt("Size", 0);
        List<Pill> pillList = new ArrayList<Pill>();

        // Создаем список таблеток и добавляем в него все сохраненные таблетки
        for (int i = 0; i < size; i++) {
            String name = sharedPreferences.getString("Name_" + i, "");
            String dosage = sharedPreferences.getString("Dosage_" + i, "");
            String best = sharedPreferences.getString("Best_" + i, "");
            int finalAmount = sharedPreferences.getInt("FinalAmount_" + i, 0);
            Pill pill = new Pill(name, dosage, best, finalAmount);
            pillList.add(pill);
            Pill newPill = new Pill(name, dosage, best, finalAmount);
            Pill.pillBox.add(newPill);
        }

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
                SharedPreferences sharedPreferences = getSharedPreferences("Pills", MODE_PRIVATE);
                int size = sharedPreferences.getInt("Size", 0);
                for (int i = 0; i < size; i++) {
                    String name = sharedPreferences.getString("Name_" + i, "");
                    if (!name.equals("-1")) {
                        AddToCourseActivity.add_to_course = true;
                        Intent intent = new Intent(MainScreen.this, AddToCourseActivity.class);
                        startActivity(intent);
                    }
                }
                Toast.makeText(view.getContext(), "У вас нет лекарств в Пилюльнице", Toast.LENGTH_SHORT).show();
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
        if (CalendarUtils.selectedDate == null) {
            CalendarUtils.selectedDate = LocalDate.now();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        String eventsJson = sharedPreferences.getString("events", null);
        ArrayList<Event> allEvents;
        if (eventsJson == null) {
            allEvents = new ArrayList<Event>();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Event>>() {}.getType();
            allEvents = gson.fromJson(eventsJson, type);
        }

// Обновление списка всех событий событием, созданным для выбранной даты
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        allEvents.addAll(dailyEvents);

// Сохранение списка всех событий в SharedPreferences
        String updatedEventsJson = convertEventsToJson(allEvents);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("events", updatedEventsJson);
        editor.apply();
        // Создание EventAdapter с обновленным списком всех событий и установка его для eventListView
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), allEvents);
        eventListView.setAdapter(eventAdapter);
    }

    private void setWeekView() {
        monthYearText.setText(formattedDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        monthYearText.setText(formattedDate(CalendarUtils.selectedDate));
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdpater();
    }

    private String convertDailyEventsToJson(ArrayList<Event> dailyEvents) {
        Gson gson = new Gson();
        return gson.toJson(dailyEvents);
    }

    private ArrayList<Event> getDailyEventsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        String dailyEventsJson = sharedPreferences.getString("daily_events", null);
        if (dailyEventsJson == null) {
            return new ArrayList<Event>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        return gson.fromJson(dailyEventsJson, type);
    }

    private void setEventAdpater() {
// Получение сохраненных данных и создание списка всех событий
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        String eventsJson = sharedPreferences.getString("events", null);
        ArrayList<Event> allEvents;
        if (eventsJson == null) {
            allEvents = new ArrayList<Event>();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Event>>() {}.getType();
            allEvents = gson.fromJson(eventsJson, type);
        }

// Обновление списка всех событий событием, созданным для выбранной даты
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        allEvents.addAll(dailyEvents);

// Сохранение списка всех событий в SharedPreferences
        String updatedEventsJson = convertEventsToJson(allEvents);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("events", updatedEventsJson);
        editor.apply();
        // Создание EventAdapter с обновленным списком всех событий и установка его для eventListView
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    private String convertEventsToJson(ArrayList<Event> events) {
        Gson gson = new Gson();
        return gson.toJson(events);
    }
}