package com.example.a70_lolkek;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AccountActivity extends AppCompatActivity {

    BottomNavigationFragment bottomNavigationFragment;
    private TextView surnameTextView;
    private TextView nameTextView;
    private TextView birthDateTextView;
    private TextView phoneNumberTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Инициализируем компоненты интерфейса
        surnameTextView = findViewById(R.id.textview_surname);
        nameTextView = findViewById(R.id.textview_name);
        birthDateTextView = findViewById(R.id.textview_birthdate);
        phoneNumberTextView = findViewById(R.id.textview_phonenumber);

        // Получаем данные из интента
        Intent intent = getIntent();
        String surname = intent.getStringExtra("surname");
        String name = intent.getStringExtra("name");
        String birthDate = intent.getStringExtra("birthDate");
        String phoneNumber = intent.getStringExtra("phoneNumber");

        // Устанавливаем полученные данные в соответствующие TextView
        surnameTextView.setText("Фамилия: " + surname);
        nameTextView.setText("Имя: " + name);
        birthDateTextView.setText("Дата рождения: " + birthDate);
        phoneNumberTextView.setText("Номер телефона: " + phoneNumber);
        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment)bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }
    }
}