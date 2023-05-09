package com.example.a70_lolkek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
            Intent intent = new Intent(MainActivity.this, MainScreen.class);
            startActivity(intent);
        });

        button_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переходим на окно EnterNumber
                Intent intent = new Intent(MainActivity.this, EnterData.class);
                startActivity(intent);
            }
        });
    }
}
