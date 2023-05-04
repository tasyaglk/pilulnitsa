package com.example.a70_lolkek;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddToPillboxActivity extends AppCompatActivity {
    private EditText pill_name, dosage_type, best_before, tablets_amount;
    private Button go_back, save_button;
    FloatingActionButton pills, drops, teaspoon, spoon, syringe, more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_pillbox);
        initWidgets();

        pills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tablets_color)));
                drops.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                teaspoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                spoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                syringe.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                more.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));

            }
        });

        drops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                drops.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tablets_color)));
                teaspoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                spoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                syringe.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                more.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
            }
        });

        teaspoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                drops.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                teaspoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tablets_color)));
                spoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                syringe.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                more.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
            }
        });

        spoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                drops.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                teaspoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                spoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tablets_color)));
                syringe.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                more.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
            }
        });

        syringe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                drops.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                teaspoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                spoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                syringe.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tablets_color)));
                more.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                drops.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                teaspoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                spoon.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                syringe.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.non_tablets_color)));
                more.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tablets_color)));
            }
        });
    }

    private void initWidgets() {
        pill_name = findViewById(R.id.pill_name);
        dosage_type = findViewById(R.id.dosage_type);
        best_before = findViewById(R.id.best_before);
        tablets_amount = findViewById(R.id.tablets_amount);

        go_back = findViewById(R.id.go_back);
        save_button = findViewById(R.id.save_button);

        pills = findViewById(R.id.pills);
        drops = findViewById(R.id.drops);
        teaspoon = findViewById(R.id.teaspoon);
        spoon = findViewById(R.id.spoon);
        syringe = findViewById(R.id.syringe);
        more = findViewById(R.id.more);
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        final String[] catNamesArray = {"Васька", "Рыжик", "Мурзик"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Выберите любимое имя кота")
//                // добавляем переключатели
//                .setSingleChoiceItems(catNamesArray, -1,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int item) {
//                                Toast.makeText(
//                                        getActivity(),
//                                        "Любимое имя кота: "
//                                                + catNamesArray[item],
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User clicked OK, so save the mSelectedItems results somewhere
//                        // or return them to the component that opened the dialog
//
//                    }
//                })
//                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//
//        return builder.create();
//    }
}