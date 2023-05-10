package com.example.a70_lolkek;

import static com.example.a70_lolkek.CourseItem.course;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
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
            bottomNavigationFragment = (BottomNavigationFragment)bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }

        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Course", MODE_PRIVATE);
                int size = sharedPreferences.getInt("Size", 0);
                if (size == 0) {
                    Toast.makeText(view.getContext(), "У вас нет лекарств в Пилюльнице", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddToCourseActivity.add_to_course = false;
                Intent intent = new Intent(context, AddToCourseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initWidgets() {
        eventListView = findViewById(R.id.pillListView);
        pill_plus = findViewById(R.id.pill_plus);
        context = this;
        SharedPreferences sharedPreferences = getSharedPreferences("Course", MODE_PRIVATE);
        int size = sharedPreferences.getInt("Size", 0);
        List<CourseItem> itemList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String name = sharedPreferences.getString("Name_" + i, "");
            int amount = sharedPreferences.getInt("CntToTake_" + i, 0);
            CourseItem item = new CourseItem(name, amount);
            itemList.add(item);
        }

// Создаем адаптер и устанавливаем его в ListView
        CourseAdapter eventAdapter = new CourseAdapter(getApplicationContext(), itemList);
        eventListView.setAdapter(eventAdapter);


        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pill.pillBox.isEmpty()) {
                    Toast.makeText(view.getContext(), "У вас нет лекарств в Пилюльнице", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddToCourseActivity.add_to_course = false;
                Intent intent = new Intent(context, AddToCourseActivity.class);
                startActivity(intent);
            }
        });

    }
}