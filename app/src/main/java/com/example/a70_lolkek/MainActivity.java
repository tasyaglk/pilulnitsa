package com.example.a70_lolkek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button_enter;
    private Button button_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим все элементы управления по их ID
        button_enter = findViewById(R.id.button2);
        button_registration = findViewById(R.id.button3);

        button_enter.setOnClickListener(view -> {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
            String surname = sharedPref.getString("surname", null);

            if (surname != null) {
                // Если surname существует в SharedPreferences, запускаем новую активность
                Intent intent = new Intent(MainActivity.this, MainScreen.class);
                startActivity(intent);
            } else {
                // Если surname не существует в SharedPreferences, показываем сообщение пользователю
                Toast.makeText(MainActivity.this, "У вас нет аккаунта, необходимо зарегистрироваться", Toast.LENGTH_LONG).show();
            }
        });


        button_registration.setOnClickListener(view -> {
            // Переходим на окно EnterNumber
            Intent intent = new Intent(MainActivity.this, EnterData.class);
            startActivity(intent);
        });
    }
}
