package com.example.a70_lolkek;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

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

//        button_enter.setOnClickListener(view -> {
//            // Обработчик для кнопки "Номер телефона"
//            // TODO: Действия при нажатии на кнопку "Номер телефона"
//        });

        button_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переходим на окно EnterNumber
                Intent intent = new Intent(MainActivity.this, EnterNumber.class);
                startActivity(intent);
            }
        });
    }
}
