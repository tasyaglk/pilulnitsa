package com.example.a70_lolkek;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class EnterData extends AppCompatActivity {

    private EditText surnameEditText, nameEditText, birthDateEditText, phoneNumberEditText;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        Intent intent_account = new Intent(EnterData.this, AccountActivity.class);

        // Находим все View элементы по id
        surnameEditText = findViewById(R.id.surname);
        nameEditText = findViewById(R.id.name);
        birthDateEditText = findViewById(R.id.birth_date);
        phoneNumberEditText = findViewById(R.id.phone_number);
        continueButton = findViewById(R.id.continue_begin);
        birthDateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(EnterData.this,
                    (view, year1, month1, dayOfMonth1) -> {
                        // Обновить текст поля ввода даты рождения
                        Calendar end_calendar = Calendar.getInstance();
                        Calendar begin_calendar = Calendar.getInstance();
                        begin_calendar.set(year, month, dayOfMonth);
                        end_calendar.set(year1, month1, dayOfMonth1);
                        LocalDate begin_local = LocalDateTime.ofInstant(begin_calendar.toInstant(), begin_calendar.getTimeZone().toZoneId()).toLocalDate();
                        LocalDate end_local = LocalDateTime.ofInstant(end_calendar.toInstant(), end_calendar.getTimeZone().toZoneId()).toLocalDate();
                        long days_number = ChronoUnit.DAYS.between(begin_local, end_local);
                        if (days_number < 0) {
                            String birthDate = "" + dayOfMonth1 / 10 + dayOfMonth1 % 10 + "." + (month1 + 1) / 10 + (month1 + 1) % 10 + "." + year1 / 10 + year1 % 10;
                            birthDateEditText.setText(birthDate);
                        } else {
                            Toast.makeText(EnterData.this, "Пожалуйста, введите корректную дату рождения", Toast.LENGTH_SHORT).show();
                        }
                    }, year, month, dayOfMonth);
            // Отображаем DatePickerDialog
            datePickerDialog.show();
        });

        // При нажатии на кнопку сохраняем данные
        continueButton.setOnClickListener(v -> {
            String surname = surnameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String birthDate = birthDateEditText.getText().toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (!(phoneNumber.startsWith("8") && phoneNumber.length() == 11)) {
                Toast.makeText(EnterData.this, "Пожалуйста, введите свой номер телефона корректно", Toast.LENGTH_SHORT).show();
                return;
            }
            // Проверяем, заполнены ли все поля
            if (TextUtils.isEmpty(surname)) {
                Toast.makeText(EnterData.this, "Пожалуйста, введите свою фамилию", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(EnterData.this, "Пожалуйста, введите свое имя", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(birthDate)) {
                Toast.makeText(EnterData.this, "Пожалуйста, введите свою дату рождения", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(EnterData.this, "Пожалуйста, введите свой номер телефона", Toast.LENGTH_SHORT).show();
                return;
            }
//

            // Сохраняем данные в SharedPreferences
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("surname", surname);
            editor.putString("name", name);
            editor.putString("birthDate", birthDate);
            editor.putString("phoneNumber", phoneNumber);
            editor.apply();

            //Переходим на главный экран
            Intent intent = new Intent(EnterData.this, MainScreen.class);
            startActivity(intent);
        });
    }
}