package com.example.a70_lolkek;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imageView2;
    private TextView textView2;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим все элементы управления по их ID
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        textView2 = findViewById(R.id.textView2);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

//        // Задаем обработчики нажатия на кнопки
//        button2.setOnClickListener(view -> {
//            // Обработчик для кнопки "Номер телефона"
//            // TODO: Действия при нажатии на кнопку "Номер телефона"
//        });
//
//        button3.setOnClickListener(view -> {
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, EnterNumber.class);
//                startActivity(intent);
//            }
//
//        });
    }
}
