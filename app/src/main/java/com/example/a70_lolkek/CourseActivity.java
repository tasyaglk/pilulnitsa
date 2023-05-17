package com.example.a70_lolkek;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    BottomNavigationFragment bottomNavigationFragment;
    Context context;
    private ImageView pill_plus;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        context = this;

        initWidgets();

        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment) bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }

        pill_plus.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Pills", MODE_PRIVATE);
            int size = sharedPreferences.getInt("Size", 0);
            if (size == 0) {
                Toast.makeText(view.getContext(), "У вас нет лекарств в Пилюльнице", Toast.LENGTH_SHORT).show();
                return;
            }
            AddToCourseActivity.add_to_course = false;
            Intent intent = new Intent(context, AddToCourseActivity.class);
            startActivity(intent);
        });
    }

    private void initWidgets() {
        eventListView = findViewById(R.id.pillListView);
        pill_plus = findViewById(R.id.pill_plus);
        context = this;

        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences("pillulnitsa_last", MODE_PRIVATE);
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
        Event.eventLast = new ArrayList<>(allEvents);
        Event.eventLast.sort(Comparator.comparing(Event::getName)
                .thenComparing(Event::getTime));

        CourseAdapter eventAdapter = new CourseAdapter(getApplicationContext(), Event.eventLast);
        eventListView.setAdapter(eventAdapter);
    }


}
