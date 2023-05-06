package com.example.a70_lolkek;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddToCourseActivity extends AppCompatActivity {

    private EditText choose_pill, amount, how_to_take, taking_days, choose_days, beginning, ending;
    private TimePicker choose_time;
    Context context;
    ArrayList<Integer> mSelectedItems;

    String[] takePill = {"До еды", "После еды", "Во время еды", "Неважно"};
    String[] takingDays = {"Каждый день", "Через день", "Выбрать дни"};
    String[] days = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
    String[] endPill = {"Дата", "Кол-во таблеток", "Кол-во дней"};

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
                                choose_days.setVisibility(View.GONE);
                                break;
                            case 1:
                                taking_days.setText(takingDays[1]);
                                choose_days.setVisibility(View.GONE);
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
                                } else if (mSelectedItems.contains(which)) {
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }

                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                StringBuilder selectedDays = new StringBuilder();
                                for(int i = 0; i < mSelectedItems.size(); ++i){
                                    Integer number = mSelectedItems.get(i);
                                    if (i == mSelectedItems.size() - 1) {
                                        selectedDays.append(days[number]);
                                    } else {
                                        selectedDays.append(days[number]).append(", ");
                                    }
                                }
                                choose_days.setText(selectedDays.toString());
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

        beginning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                beginning.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        ending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // здесь нужно доделать 3 вида
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Дни приема");
                builder.setItems(endPill, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ending.setText(endPill[0]);
                                break;
                            case 1:
                                ending.setText(endPill[1]);
                                break;
                            case 2:
                                ending.setText(endPill[2]);
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
        beginning = findViewById(R.id.beginning);
        ending = findViewById(R.id.ending);
    }

}