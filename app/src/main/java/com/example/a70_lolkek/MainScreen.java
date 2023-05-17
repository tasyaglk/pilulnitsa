package com.example.a70_lolkek;

import static com.example.a70_lolkek.CalendarUtils.daysInWeekArray;
import static com.example.a70_lolkek.CalendarUtils.formattedDate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainScreen extends AppCompatActivity implements CalendarAdapter.OnItemListener {

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

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Pills", MODE_PRIVATE);
        int size = sharedPreferences.getInt("Size", 0);
        List<Pill> pillList = new ArrayList<>();

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

        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("pillulnitsa", MODE_PRIVATE);
        String eventsJson = sharedPreferences1.getString("events", null);
        ArrayList<Event> allEvents;
        if (eventsJson == null) {
            allEvents = new ArrayList<>();
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
            Gson gson = gsonBuilder.create();
            Type type = new TypeToken<ArrayList<Event>>() {
            }.getType();
            allEvents = gson.fromJson(eventsJson, type);
        }
        Event.eventsList = new ArrayList<>(allEvents);

        settings_button.setOnClickListener(view -> {
            // Здесь будет переход в настройки
            Intent intent = new Intent(MainScreen.this, SettingsActivity.class);
            startActivity(intent);
        });

        calendar_button.setOnClickListener(view -> {
            // Здесь будет переход в календарь
            Intent intent = new Intent(MainScreen.this, CalendarActivity.class);
            startActivity(intent);
        });

        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Pills", MODE_PRIVATE);
                int size = sharedPreferences.getInt("Size", 0);

                if (size == 0) {
                    Toast.makeText(view.getContext(), "У вас нет лекарств в Пилюльнице", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < size; i++) {
                    String name = sharedPreferences.getString("Name_" + i, "");
                    if (!name.equals("-1")) {
                        AddToCourseActivity.add_to_course = true;
                        Intent intent = new Intent(MainScreen.this, AddToCourseActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void initWidgets() {
        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment) bottom_fragment;
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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("pillulnitsa", MODE_PRIVATE);
        String eventsJson = sharedPreferences.getString("events", null);
        ArrayList<Event> allEvents;
        if (eventsJson == null) {
            allEvents = new ArrayList<>();
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
            Gson gson = gsonBuilder.create();
            Type type = new TypeToken<ArrayList<Event>>() {
            }.getType();
            allEvents = gson.fromJson(eventsJson, type);
        }
        Event.eventsList = new ArrayList<>(allEvents);

        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("pillulnitsa_last", MODE_PRIVATE);
        String eventsJson1 = sharedPreferences.getString("events", null);
        ArrayList<Event> allEvents1;
        if (eventsJson == null) {
            allEvents1 = new ArrayList<>();
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
            Gson gson = gsonBuilder.create();
            Type type = new TypeToken<ArrayList<Event>>() {
            }.getType();
            allEvents1 = gson.fromJson(eventsJson, type);
        }
        Event.eventLast = new ArrayList<>(allEvents1);

// Обновление списка всех событий событием, созданным для выбранной даты
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        allEvents.addAll(dailyEvents);

        // Создание EventAdapter с обновленным списком всех событий и установка его для eventListView
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), allEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public static String formDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU"));
        return date.format(formatter);
    }

    private void setWeekView() {
        monthYearText.setText(formDate(CalendarUtils.selectedDate));

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
    public void onItemClick(int position, LocalDate date) {
        monthYearText.setText(formattedDate(CalendarUtils.selectedDate));
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater() {
// Получение сохраненных данных и создание списка всех событий
        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences("pillulnitsa", MODE_PRIVATE);
        String eventsJson = sharedPreferences.getString("events", null);
        ArrayList<Event> allEvents;
        if (eventsJson == null) {
            allEvents = new ArrayList<>();
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
            Gson gson = gsonBuilder.create();
            Type type = new TypeToken<ArrayList<Event>>() {
            }.getType();
            allEvents = gson.fromJson(eventsJson, type);
        }
        Event.eventsList = new ArrayList<>(allEvents);

        // Обновление списка всех событий событием, созданным для выбранной даты
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);

        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }
}