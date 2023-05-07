package com.example.a70_lolkek;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddToCourseActivity extends AppCompatActivity {

    private EditText amount, how_to_take, taking_days, choose_days, beginning, ending, choose_end_date, end_days_number, end_pill_number;
    private Spinner choose_pill;
    private TimePicker choose_time;
    private Button save_button, go_back;
    Context context;
    ArrayList<Integer> mSelectedItems;

    String[] takePill = {"До еды", "После еды", "Во время еды", "Неважно"};
    String[] takingDays = {"Каждый день", "Через день", "Выбрать дни"};
    String[] days = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
    String[] endPill = {"Дата", "Кол-во дней", "Кол-во таблеток"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_course);

        initWidgets();
        context = this;

        ArrayAdapter<Pill> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Pill.pillBox);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        choose_pill.setAdapter(adapter);

        choose_pill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);
                Pill pill = (Pill) parent.getSelectedItem();
                String name = pill.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                                ending.setText(endPill[0]);  // дата
                                choose_end_date.setVisibility(View.VISIBLE);
                                end_days_number.setVisibility(View.GONE);
                                end_pill_number.setVisibility(View.GONE);
                                end_days_number.setText("");
                                end_pill_number.setText("");
                                end_days_number.setFocusable(false);
                                end_days_number.setFocusableInTouchMode(false);
                                end_days_number.setClickable(false);
                                end_days_number.setCursorVisible(false);
                                end_pill_number.setFocusable(false);
                                end_pill_number.setFocusableInTouchMode(false);
                                end_pill_number.setClickable(false);
                                end_pill_number.setCursorVisible(false);
                                break;
                            case 1:
                                ending.setText(endPill[1]);  // кол-во дней
                                choose_end_date.setVisibility(View.GONE);
                                end_days_number.setVisibility(View.VISIBLE);
                                end_days_number.setFocusable(true);
                                end_days_number.setFocusableInTouchMode(true);
                                end_days_number.setClickable(true);
                                end_days_number.setCursorVisible(true);
                                choose_end_date.setText("");
                                end_pill_number.setText("");
                                end_pill_number.setVisibility(View.GONE);
                                break;
                            case 2:
                                ending.setText(endPill[2]);  // кол-во таблеток
                                choose_end_date.setVisibility(View.GONE);
                                end_days_number.setVisibility(View.GONE);
                                end_pill_number.setVisibility(View.VISIBLE);
                                end_days_number.setFocusable(false);
                                end_days_number.setFocusableInTouchMode(false);
                                end_days_number.setClickable(false);
                                end_days_number.setCursorVisible(false);
                                end_pill_number.setFocusable(true);
                                end_pill_number.setFocusableInTouchMode(true);
                                end_pill_number.setClickable(true);
                                end_pill_number.setCursorVisible(true);
                                end_days_number.setText("");
                                choose_end_date.setText("");
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        choose_end_date.setOnClickListener(new View.OnClickListener() {
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
                                choose_end_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = choose_pill.toString();
                String dosage = amount.getText().toString();
                String taking_time = choose_time.toString();
                String taking_method = how_to_take.getText().toString();

                String days = taking_days.getText().toString();
                String chosen_days = choose_days.getText().toString();

                String begin = beginning.getText().toString();
                String end = ending.getText().toString();

                String end_date = choose_end_date.getText().toString();
                String number_days = end_days_number.getText().toString();
                String number_pills = end_pill_number.getText().toString();

                if (name.matches("")) {
                    Toast.makeText(view.getContext(), "Вы не выбрали лекарство", Toast.LENGTH_SHORT).show();
                    return;
                } else if (dosage.matches("")) {
                    Toast.makeText(view.getContext(), "Вы не ввели дозировку", Toast.LENGTH_SHORT).show();
                    return;
                } else if (taking_time.matches("")) {
                    Toast.makeText(view.getContext(), "Вы не выбрали время приема лекарства", Toast.LENGTH_SHORT).show();
                    return;
                } else if (taking_method.matches("")) {
                    Toast.makeText(view.getContext(), "Вы не указали взаимосвязь с приемом пищи", Toast.LENGTH_SHORT).show();
                    return;
                } else if (days.matches("")) {
                    Toast.makeText(view.getContext(), "Вы не указали дни приема лекарства", Toast.LENGTH_SHORT).show();
                    return;
                } else if (chosen_days.matches("") && days.matches("Выбрать дни")) {
                    Toast.makeText(view.getContext(), "Вы не выбрали дни приема лекарства", Toast.LENGTH_SHORT).show();
                    return;
                } else if (begin.matches("")) {
                    Toast.makeText(view.getContext(), "Вы не указали начальную дату приема", Toast.LENGTH_SHORT).show();
                    return;
                } else if (end.matches("")) {
                    Toast.makeText(view.getContext(), "Вы не указали окончание приема лекарства", Toast.LENGTH_SHORT).show();
                    return;
                } else if (end_date.matches("") && end.matches("Дата")) {
                    Toast.makeText(view.getContext(), "Вы не указали конечную дату приема", Toast.LENGTH_SHORT).show();
                    return;
                } else if (number_days.matches("") && end.matches("Кол-во дней")) {
                    Toast.makeText(view.getContext(), "Вы не указали количество дней приема", Toast.LENGTH_SHORT).show();
                    return;
                } else if (number_pills.matches("") && end.matches("Кол-во таблеток")) {
                    Toast.makeText(view.getContext(), "Вы не указали количество лекарства для приема", Toast.LENGTH_SHORT).show();
                    return;
                }
//                int finalAmount = Integer.parseInt(amount);
//                Pill newPill = new Pill(name, dosage, best, finalAmount);
//                Pill.pillBox.add(newPill);
//
//                Intent intent = new Intent(context, PillsActivity.class);
//                startActivity(intent);
            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PillsActivity.class);
                startActivity(intent);
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
        end_days_number = findViewById(R.id.end_days_number);
        end_pill_number = findViewById(R.id.end_pill_number);
        choose_end_date = findViewById(R.id.choose_end_date);
        save_button = findViewById(R.id.save_button);
        go_back = findViewById(R.id.go_back);
    }

}