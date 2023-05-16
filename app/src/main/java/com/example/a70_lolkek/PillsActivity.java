package com.example.a70_lolkek;

import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PillsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ImageView pill_plus;
    BottomNavigationFragment bottomNavigationFragment;
    private ListView eventListView;
    Context context;
    ListViewAdapter adapter;
    SearchView editsearch;

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

        pill_plus.setOnClickListener(view -> {
            // Здесь будет создаваться новое лекарство
            Intent intent = new Intent(context, AddToPillboxActivity.class);
            startActivity(intent);
        });

        eventListView.setOnItemClickListener((parent, view, position, id) -> {
            // проверка на то, находится ли лекарство в курсе приема
            Pill selected = (Pill) eventListView.getItemAtPosition(position);
            String name = selected.getName();
            Log.d("name", name);
            boolean isIn = CourseItem.checkInCourse(name);
            String message = "Вы действительно хотите удалить это лекарство?";
            if (isIn) {
                message = "Это лекарство находится в курсе приема. Вы действительно хотите его удалить?";
            }

            new AlertDialog.Builder(context)
                    .setTitle("Удаление")
                    .setMessage(message)
                    .setPositiveButton("ДА",
                            (dialog, id1) -> {
                                if (isIn) {
                                    CourseItem.deleteFromCourse(name);
                                }
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Pills", MODE_PRIVATE);
                                int size = sharedPreferences.getInt("Size", 0);
                                for (int i = 0; i < size; i++) {
                                    String name_sh = sharedPreferences.getString("Name_" + i, "");
                                    if (name_sh.equals(name)) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("Name_" + i, "-1");
                                        editor.apply();
                                        break;
                                    }
                                }
                                dialog.cancel();
                                Intent intent = new Intent(context, context.getClass());
                                startActivity(intent);
                            })
                    .setNegativeButton("НЕТ", (dialog, id12) -> dialog.cancel()).show();
        });
    }

    private void initWidgets() {
        pill_plus = findViewById(R.id.pill_plus);
        eventListView = findViewById(R.id.pillListView);
        context = this;

        // Читаем сохраненные таблетки из SharedPreferences
        List<Pill> pillList = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Pills", MODE_PRIVATE);
        int size = sharedPreferences.getInt("Size", 0);
        // Создаем список таблеток и добавляем в него все сохраненные таблетки
        for (int i = 0; i < size; i++) {
            String name = sharedPreferences.getString("Name_" + i, "");
            String dosage = sharedPreferences.getString("Dosage_" + i, "");
            String best = sharedPreferences.getString("Best_" + i, "");
            int finalAmount = sharedPreferences.getInt("FinalAmount_" + i, 0);
            if (!name.equals("-1")) {
                Pill pill = new Pill(name, dosage, best, finalAmount);
                pillList.add(pill);
            }
        }

        adapter = new ListViewAdapter(this, pillList);
        eventListView.setAdapter(adapter);

        editsearch = findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        pillList.sort(Comparator.comparing(Pill::getName)
                                .thenComparing(Pill::getBestBeforeDate));


        PillAdapter eventAdapter = new PillAdapter(getApplicationContext(), pillList);
        eventListView.setAdapter(eventAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }
}