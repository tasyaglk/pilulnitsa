package com.example.a70_lolkek;

import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PillsActivity extends AppCompatActivity {

    private ImageView pill_plus;
    BottomNavigationFragment bottomNavigationFragment;
    private ListView eventListView;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pills);
        initWidgets();

        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment) bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }

        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь будет создаваться новое лекарство
                Intent intent = new Intent(context, AddToPillboxActivity.class);
                startActivity(intent);
            }
        });

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // проверка на то, находится ли лекарство в курсе приема
                Pill selected = (Pill) eventListView.getItemAtPosition(position);
                String name = selected.getName();
                boolean isIn = CourseItem.checkInCourse(name);
                String message = "Вы действительно хотите удалить это лекарство?";
                if (isIn) {
                    message = "Это лекарство находится в курсе приема. Вы действительно хотите его удалить?";
                }

                new AlertDialog.Builder(context)
                        .setTitle("Удаление")
                        .setMessage(message)
                        .setPositiveButton("ДА",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (isIn) {
                                            CourseItem.deleteFromCourse(name);
                                        }
                                        SharedPreferences sharedPreferences = getSharedPreferences("Pills", MODE_PRIVATE);
                                        int size = sharedPreferences.getInt("Size", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.remove("Name_" + name);
                                        editor.remove("Dosage_" + name);
                                        editor.remove("Best_" + name);
                                        editor.remove("FinalAmount_" + name);
                                        editor.putInt("Size", size - 1);
                                        editor.apply();
                                        Intent intent = new Intent(context, context.getClass());
                                        startActivity(intent);
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }


    private void initWidgets() {
        pill_plus = findViewById(R.id.pill_plus);
        eventListView = findViewById(R.id.pillListView);
        context = this;

        // Читаем сохраненные таблетки из SharedPreferences
        List<Pill> pillList = new ArrayList<Pill>();
        SharedPreferences sharedPreferences = getSharedPreferences("Pills", MODE_PRIVATE);
        int size = sharedPreferences.getInt("Size", 0);
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

    }
}