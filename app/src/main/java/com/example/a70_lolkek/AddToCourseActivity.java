package com.example.a70_lolkek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddToCourseActivity extends AppCompatActivity {

    private EditText choose_pill, amount, how_to_take, taking_days, choose_days;
    private TimePicker choose_time;
    Context context;
    ArrayList<Integer> mSelectedItems;

    String[] takePill = {"До еды", "После еды", "Во время еды", "Неважно"};
    String[] takingDays = {"Каждый день", "Через день", "Выбрать дни"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_course);

        initWidgets();
        context = this;
        String Time = choose_time.getHour() + ":" + choose_time.getMinute();

        how_to_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Лекарство принимать");
                builder.setItems(takePill, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                how_to_take.setText(takePill[0]);
                                break;
                            case 1:
                                how_to_take.setText(takePill[1]);
                                break;
                            case 2:
                                how_to_take.setText(takePill[2]);
                                break;
                            case 3:
                                how_to_take.setText(takePill[3]);
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        taking_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Дни приема");
                builder.setItems(takingDays, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                taking_days.setText(takingDays[0]);
                                choose_days.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                taking_days.setText(takingDays[1]);
                                choose_days.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                taking_days.setText(takingDays[2]);
                                choose_days.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        choose_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedItems = new ArrayList<>();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Выберите дни")
                        .setMultiChoiceItems(R.array.days, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    mSelectedItems.add(which);
                                }

                                else if (mSelectedItems.contains(which)) {
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }

                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                // user clicked OK, so save the mSelectedItems results somewhere
                                // here we are trying to retrieve the selected items indices
                                String selectedIndex = "";
                                for(Integer i : mSelectedItems){
                                    selectedIndex += i + ", ";
                                }
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // removes the AlertDialog in the screen
                            }
                        })

                        .show();
            }
        });

    }

    private void initWidgets() {
        choose_time = findViewById(R.id.choose_time);
        choose_time.setIs24HourView(true);
        choose_pill = findViewById(R.id.choose_pill);
        amount = findViewById(R.id.amount);
        how_to_take = findViewById(R.id.how_to_take);
        taking_days = findViewById(R.id.taking_days);
        choose_days = findViewById(R.id.choose_days);
    }

}