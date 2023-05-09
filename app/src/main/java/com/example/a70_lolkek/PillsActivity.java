package com.example.a70_lolkek;

import static com.example.a70_lolkek.Pill.pillBox;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PillsActivity extends AppCompatActivity {

    private ImageView pill_plus;
    BottomNavigationFragment bottomNavigationFragment;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pills);
        initWidgets();

        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment)bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }


        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь будет создаваться новое лекарство
                Intent intent = new Intent(PillsActivity.this, AddToPillboxActivity.class);
                startActivity(intent);

                // Создаем и добавляем таблетку в базу данных SharedPreferences
               // savePillToSharedPreferences("Название таблетки", "Описание таблетки");
            }
        });
    }


    private void initWidgets() {
        pill_plus = findViewById(R.id.pill_plus);
        eventListView = findViewById(R.id.pillListView);

        // Читаем сохраненные таблетки из SharedPreferences
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
        }

        PillAdapter eventAdapter = new PillAdapter(getApplicationContext(), pillList);
        eventListView.setAdapter(eventAdapter);

        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь будет создаваться новое лекарство
                Intent intent = new Intent(PillsActivity.this, AddToPillboxActivity.class);
                startActivity(intent);

                // Создаем и добавляем таблетку в базу данных SharedPreferences
                //savePillToSharedPreferences("Название таблетки", "Описание таблетки");
            }
        });
    }

}