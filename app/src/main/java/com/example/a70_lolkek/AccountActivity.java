package com.example.a70_lolkek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AccountActivity extends AppCompatActivity {

    BottomNavigationFragment bottomNavigationFragment;
    private TextView surnameTextView;
    private TextView nameTextView;
    private TextView birthDateTextView;
    private TextView phoneNumberTextView;
    private Button button_course;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Инициализируем компоненты интерфейса
        surnameTextView = findViewById(R.id.textview_surname);
        nameTextView = findViewById(R.id.textview_name);
        birthDateTextView = findViewById(R.id.textview_birthdate);
        phoneNumberTextView = findViewById(R.id.textview_phonenumber);
        surnameTextView.setPaintFlags(surnameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        birthDateTextView.setPaintFlags(birthDateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        phoneNumberTextView.setPaintFlags(phoneNumberTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        String surname = prefs.getString("surname", "");
        String name = prefs.getString("name", "");
        String birthDate = prefs.getString("birthDate", "");
        String phoneNumber = prefs.getString("phoneNumber", "");

        // Устанавливаем полученные данные в соответствующие TextView
        surnameTextView.setText(surname);
        nameTextView.setText(name);
        birthDateTextView.setText(birthDate);
        phoneNumberTextView.setText(phoneNumber);

        button_course = findViewById(R.id.course);

        button_course.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, CourseActivity.class);
            startActivity(intent);
        });
        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment)bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }
    }
}