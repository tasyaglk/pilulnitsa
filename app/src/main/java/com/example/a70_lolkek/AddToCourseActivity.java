package com.example.a70_lolkek;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AddToCourseActivity extends AppCompatActivity {

    private EditText amount, how_to_take, taking_days, choose_days, beginning, ending, choose_end_date, end_days_number, end_pill_number;
    private Spinner choose_pill;
    private TimePicker choose_time;
    private Button save_button, go_back;
    private Context context;
    private ArrayList<Integer> mSelectedItems;
    public static boolean add_to_course = false;
    String[] takePill = {"До еды", "После еды", "Во время еды", "Неважно"};
    String[] takingDays = {"Каждый день", "Через день", "Выбрать дни"};
    String[] days = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
    String[] endPill = {"Дата", "Кол-во дней", "Кол-во таблеток"};
    String name;
    Calendar begin_calendar = Calendar.getInstance(), end_calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_course);
        initWidgets();
        amount = findViewById(R.id.amount);
        context = this;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Pills", MODE_PRIVATE);
        int size = sharedPreferences.getInt("Size", 0);
        List<Pill> pillList = new ArrayList<>();

        // Создаем список таблеток и добавляем в него все сохраненные таблетки
        for (int i = 0; i < size; i++) {
            String name = sharedPreferences.getString("Name_" + i, "");
            String dosage1 = sharedPreferences.getString("Dosage_" + i, "");
            String best = sharedPreferences.getString("Best_" + i, "");
            int finalAmount = sharedPreferences.getInt("FinalAmount_" + i, 0);
            if (!name.equals("-1")) {
                Pill pill = new Pill(name, dosage1, best, finalAmount);
                pillList.add(pill);
                Pill.pillBox.add(pill);
            }
        }

        ArrayAdapter<Pill> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pillList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_pill.setAdapter(adapter);

        choose_pill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView child = ((TextView) parent.getChildAt(0));
                if (child == null) return;
                child.setTextColor(Color.GRAY);
                child.setTextSize(20);
                child.setTypeface(Typeface.DEFAULT_BOLD);
                Pill pill = (Pill) parent.getSelectedItem();
                name = pill.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        how_to_take.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Лекарство принимать");
            builder.setItems(takePill, (dialog, which) -> {
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
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
//
        taking_days.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Дни приема");
            builder.setItems(takingDays, (dialog, which) -> {
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
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        choose_days.setOnClickListener(view -> {
            mSelectedItems = new ArrayList<>();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Выберите дни")
                        .setMultiChoiceItems(R.array.days, null, (dialog, which, isChecked) -> {
                            if (isChecked) {
                                mSelectedItems.add(which);
                            } else if (mSelectedItems.contains(which)) {
                                mSelectedItems.remove(Integer.valueOf(which));
                            }
                        })
                        .setPositiveButton("OK", (dialog, id) -> {
                            StringBuilder selectedDays = new StringBuilder();
                            for (int i = 0; i < mSelectedItems.size(); ++i) {
                                Integer number = mSelectedItems.get(i);
                                if (i == mSelectedItems.size() - 1) {
                                    selectedDays.append(days[number]);
                                } else {
                                    selectedDays.append(days[number]).append(", ");
                                }
                            }
                            choose_days.setText(selectedDays.toString());
                        })

                    .setNegativeButton("Cancel", (dialog, id) -> {
                        // removes the AlertDialog in the screen
                    })

                    .show();
        });

        // beging course year
        beginning.setOnClickListener(v -> {
            // calender class's instance and get current date , month and year from calender
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (view, year1, month1, dayOfMonth1) -> {
                        String last_day =  "" + dayOfMonth1 / 10 + dayOfMonth1 % 10 + "." + (month1 + 1) / 10 + (month1 + 1)  % 10 + "." + year1 / 10 + year1 % 10;
                        beginning.setText(last_day);
                        begin_calendar.set(year1, month1, dayOfMonth1);
                    }, mYear, mMonth, mDay);
            // Отображаем DatePickerDialog
            datePickerDialog.show();
        });

        ending.setOnClickListener(view -> {
            // здесь нужно доделать 3 вида
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Окончание по:");
            builder.setItems(endPill, (dialog, which) -> {
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
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        choose_end_date.setOnClickListener(v -> {
            // calender class's instance and get current date , month and year from calender
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (view, year1, month1, dayOfMonth1) -> {
                        String data_beg = String.valueOf(beginning.getText());
                        String[] dateParts = data_beg.split("\\.");
                        int day_beg = Integer.parseInt(dateParts[0]);
                        int month_beg = Integer.parseInt(dateParts[1]);
                        int year_beg = Integer.parseInt(dateParts[2]);
                        month_beg -= 1;
                        Calendar end_calendar1 = Calendar.getInstance();
                        Calendar begin_calendar1 = Calendar.getInstance();
                        begin_calendar1.set(year_beg, month_beg, day_beg);
                        end_calendar1.set(year1, month1, dayOfMonth1);
                        Calendar now_calendar1 = Calendar.getInstance();
                        now_calendar1.set(mYear, mMonth, mDay);
                        LocalDate now_local = LocalDateTime.ofInstant(now_calendar1.toInstant(), now_calendar1.getTimeZone().toZoneId()).toLocalDate();

                        LocalDate begin_local = LocalDateTime.ofInstant(begin_calendar1.toInstant(), begin_calendar1.getTimeZone().toZoneId()).toLocalDate();
                        LocalDate end_local = LocalDateTime.ofInstant(end_calendar1.toInstant(), end_calendar1.getTimeZone().toZoneId()).toLocalDate();
                        long days_number = ChronoUnit.DAYS.between(begin_local, end_local);
                        long days_number_before_now = ChronoUnit.DAYS.between(now_local, end_local);

                        // Обновить текст поля ввода даты рождения
                        //ssssss
                        if(days_number > 0) {
                            if(days_number_before_now < 0) {
                                Toast.makeText(context, "Данный курс уже должен быть закончен", Toast.LENGTH_SHORT).show();
                            } else {
                                String last_day = "" + dayOfMonth1 / 10 + dayOfMonth1 % 10 + "." + (month1 + 1) / 10 + (month1 + 1) % 10 + "." + year1 / 10 + year1 % 10;
                                choose_end_date.setText(last_day);
                                end_calendar.set(year1, month1, dayOfMonth1);
                            }
                        } else {
                            Toast.makeText(context, "Пожалуйста, введите корректную дату конца курса", Toast.LENGTH_SHORT).show();
                        }

                    }, mYear, mMonth, mDay);
            // Отображаем DatePickerDialog
            datePickerDialog.show();
        });

        save_button.setOnClickListener(view -> {
            String dosage = amount.getText().toString();
            String taking_time = String.valueOf(choose_time.getHour()) + ':' + choose_time.getMinute();
            String taking_method = how_to_take.getText().toString();

            String days = taking_days.getText().toString();
            String chosen_days = choose_days.getText().toString();

            String begin = beginning.getText().toString();
            String end = ending.getText().toString();

            String end_date = choose_end_date.getText().toString();
            String number_days = end_days_number.getText().toString();
            String number_pills = end_pill_number.getText().toString();
            int doza = Integer.parseInt(dosage);
            if (name.matches("")) {
                Toast.makeText(view.getContext(), "Вы не выбрали лекарство", Toast.LENGTH_SHORT).show();
                return;
            } else if (dosage.matches("") || doza == 0) {
                // z
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
            // забираем все введенные параметры
            int number_d = 0;
            int number_p = 0;
            long check_pills = 0;

            int finalAmount = Integer.parseInt(dosage);
            if (end.equals(endPill[1])) {
                number_d = Integer.parseInt(number_days);
                check_pills = number_d * finalAmount;
            } else if (end.equals(endPill[2])) {
                number_p = Integer.parseInt(number_pills);
                check_pills = number_p;
            }

            // создаем все события
            int amount = 1;
            LocalDate begin_local = LocalDateTime.ofInstant(begin_calendar.toInstant(), begin_calendar.getTimeZone().toZoneId()).toLocalDate();
            LocalDate end_local;

            long days_number = 0;
            long counter = 0;
            int substact = 1;

            if (!end_date.matches("")) { // если указана дата окончания
                end_local = LocalDateTime.ofInstant(end_calendar.toInstant(), end_calendar.getTimeZone().toZoneId()).toLocalDate();
                days_number = ChronoUnit.DAYS.between(begin_local, end_local);
                counter = days_number + 1;
            } else if (!number_days.matches("")) { // если указано кол-во дней
                days_number = number_d;
                counter = days_number + 1;
            } else if (!number_pills.matches("")) { // если указано кол-во таблеток
                counter = number_p;
                substact = finalAmount;
            }

            if (end.equals(endPill[0])) { // choose date
                if (days.matches(takingDays[0])) { // every day
                    check_pills = days_number * finalAmount;
                } else if (days.matches(takingDays[1])) {
                    check_pills = days_number / 2 + days_number % 2;
                } else {
                    days_number = countDays(counter);
                    check_pills = days_number * finalAmount;
                }
            }

            // check if enough pills

            SharedPreferences sharedPills = getApplicationContext().getSharedPreferences("Pills", MODE_PRIVATE);
            for (int i = 0; i < size; i++) {
                String name_sh = sharedPills.getString("Name_" + i, "");
                if (name_sh.equals(name)) {
                    int amount_sh = sharedPills.getInt("FinalAmount_" + i, 0);
                    if (amount_sh < check_pills) {
                        Toast.makeText(view.getContext(), "У вас недостаточно таблеток для приема. У вас "
                                + amount_sh + " лекарства", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }

            if (days.matches(takingDays[1])) {
                amount = 2;
            } else if (days.matches(takingDays[2])) {

                while (counter > 0) {
                    begin_local = LocalDateTime.ofInstant(begin_calendar.toInstant(), begin_calendar.getTimeZone().toZoneId()).toLocalDate();
                    if (mSelectedItems.contains(0) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                        Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                                finalAmount, number_d, number_p, begin_local, mSelectedItems);
                        Event.eventsList.add(newEvent);
                        counter -= substact;
                    } else if (mSelectedItems.contains(1) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                        Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                                finalAmount, number_d, number_p, begin_local, mSelectedItems);
                        Event.eventsList.add(newEvent);
                        counter -= substact;
                    } else if (mSelectedItems.contains(2) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                        Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                                finalAmount, number_d, number_p, begin_local, mSelectedItems);
                        Event.eventsList.add(newEvent);
                        counter -= substact;
                    } else if (mSelectedItems.contains(3) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                        Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                                finalAmount, number_d, number_p, begin_local, mSelectedItems);
                        Event.eventsList.add(newEvent);
                        counter -= substact;
                    } else if (mSelectedItems.contains(4) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                        Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                                finalAmount, number_d, number_p, begin_local, mSelectedItems);
                        Event.eventsList.add(newEvent);
                        counter -= substact;
                    } else if (mSelectedItems.contains(5) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                                finalAmount, number_d, number_p, begin_local, mSelectedItems);
                        Event.eventsList.add(newEvent);
                        counter -= substact;
                    } else if (mSelectedItems.contains(6) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                                finalAmount, number_d, number_p, begin_local, mSelectedItems);
                        Event.eventsList.add(newEvent);
                        counter -= substact;
                    }
                    Pill.changeAmount(name, finalAmount);
                    begin_calendar.add(Calendar.DATE, 1);
                }

                for (int i = 0; i < size; i++) {
                    String name_sh = sharedPills.getString("Name_" + i, "");
                    if (name_sh.equals(name)) {
                        int amount_sh = sharedPills.getInt("FinalAmount_" + i, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Log.d("creat", String.valueOf(amount_sh));
                        Log.d("creat", String.valueOf(check_pills));
                        editor.putInt("FinalAmount_" + i, (int) (amount_sh - check_pills));
                        editor.apply();
                        break;
                    }
                }

                CourseItem item = new CourseItem(name, finalAmount);
                CourseItem.course.add(item);

                CourseItem.course.sort(Comparator.comparing(CourseItem::getName));

                Intent intent;
                if (add_to_course) {
                    intent = new Intent(context, MainScreen.class);

                } else {
                    intent = new Intent(context, CourseActivity.class);
                }
                startActivity(intent);
                return;
            }

            // если каждый день или через день принимаем
            while (counter > 0) {
                begin_local = LocalDateTime.ofInstant(begin_calendar.toInstant(), begin_calendar.getTimeZone().toZoneId()).toLocalDate();
                Event newEvent = new Event(name, taking_time, taking_method, days, begin, end, end_date,
                        finalAmount, number_d, number_p, begin_local, mSelectedItems);
                Event.eventsList.add(newEvent);
                begin_calendar.add(Calendar.DATE, amount);

                Pill.changeAmount(name, finalAmount);

                counter -= substact;
            }

            for (int i = 0; i < size; i++) {
                String name_sh = sharedPills.getString("Name_" + i, "");
                if (name_sh.equals(name)) {
                    int amount_sh = sharedPills.getInt("FinalAmount_" + i, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Log.d("createver", String.valueOf(amount_sh));
                    Log.d("createver", String.valueOf(finalAmount));
                    editor.putInt("FinalAmount_" + i, (int) (amount_sh - check_pills));
                    editor.apply();
                    break;
                }
            }

            CourseItem item = new CourseItem(name, finalAmount);
            CourseItem.course.add(item);
            SharedPreferences shared = getApplicationContext().getSharedPreferences("Course", MODE_PRIVATE);
            int size1 = shared.getInt("Size", 0);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("Name_" + size1, CourseItem.course.get(CourseItem.course.size() - 1).getName());
            editor.putInt("FinalAmount_" + size1, CourseItem.course.get(CourseItem.course.size() - 1).getAmount());
            editor.putInt("Size", size1 + 1);
            editor.apply();

            CourseItem.course.sort(Comparator.comparing(CourseItem::getName));

            SharedPreferences sharedPreferencesEventList = getApplicationContext().getSharedPreferences("pillulnitsa", MODE_PRIVATE);
            String updatedEventsJson = convertEventsToJson(Event.eventsList);

            SharedPreferences.Editor editorEventList = sharedPreferencesEventList.edit();
            editorEventList.putString("events", updatedEventsJson);
            editorEventList.apply();
            Event.eventLast.add(Event.eventsList.get(Event.eventsList.size() - 1));

            SharedPreferences sharedPreferencesEventLast = getApplicationContext().getSharedPreferences("pillulnitsa2", MODE_PRIVATE);
            String updatedEventsJsonLast = convertEventsToJson(Event.eventLast);

            SharedPreferences.Editor editorEventLast = sharedPreferencesEventLast.edit();
            editorEventLast.putString("events", updatedEventsJsonLast);
            editorEventLast.apply();

            Intent intent;
            if (add_to_course) {
                intent = new Intent(context, MainScreen.class);
            } else {
                intent = new Intent(context, CourseActivity.class);
            }
            startActivity(intent);
        });

        go_back.setOnClickListener(view -> {
            Intent intent;
            if (add_to_course) {
                intent = new Intent(context, MainScreen.class);

            } else {
                intent = new Intent(context, CourseActivity.class);
            }
            startActivity(intent);
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

    private String convertEventsToJson(ArrayList<Event> events) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.toJson(events);
    }

    private long countDays(long counter) {
        long days_number = 0;
        while (counter > 0) {
            if (mSelectedItems.contains(0) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                days_number++;
            } else if (mSelectedItems.contains(1) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                days_number++;
            } else if (mSelectedItems.contains(2) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                days_number++;
            } else if (mSelectedItems.contains(3) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                days_number++;
            } else if (mSelectedItems.contains(4) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                days_number++;
            } else if (mSelectedItems.contains(5) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                days_number++;
            } else if (mSelectedItems.contains(6) && begin_calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                days_number++;
            }
            begin_calendar.add(Calendar.DATE, 1);
            counter--;
        }
        return days_number;
    }
}