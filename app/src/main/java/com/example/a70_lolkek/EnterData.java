package com.example.a70_lolkek;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Calendar;

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

        birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EnterData.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Обновить текст поля ввода даты рождения
                                String birthDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                birthDateEditText.setText(birthDate);
                            }
                        }, year, month, dayOfMonth);
                // Отображаем DatePickerDialog
                datePickerDialog.show();
            }
        });


        // При нажатии на кнопку сохраняем данные
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String surname = surnameEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String birthDate = birthDateEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();

                // Сохраняем данные в SharedPreferences
                SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("surname", surname);
                editor.putString("name", name);
                editor.putString("birthDate", birthDate);
                editor.putString("phoneNumber", phoneNumber);
                editor.apply();

                 //Переходим на главный экран
                Intent intent = new Intent(EnterData.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}


