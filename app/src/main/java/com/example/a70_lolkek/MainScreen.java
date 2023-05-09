package com.example.a70_lolkek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainScreen extends AppCompatActivity {

    private ImageView settings_button;
    private ImageView calendar_button;
    private ImageView pill_plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen2);

        settings_button = findViewById(R.id.settings_button);
        calendar_button = findViewById(R.id.calendar_button);
        pill_plus = findViewById(R.id.pill_plus);

        Calendar calendar = Calendar.getInstance();
        //calendar.set(2022, 9, 18); // установить нужную дату: год, месяц (начиная с 0), день
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        TextView dateTextView = findViewById(R.id.date_text_view);
        dateTextView.setText(formattedDate);

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь будет переход в настройки
                Intent intent = new Intent(MainScreen.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь будет переход в календарь
                Intent intent = new Intent(MainScreen.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь добавление лекарства в курс
                Intent intent = new Intent(MainScreen.this, AddToCourseActivity.class);
                startActivity(intent);
            }
        });
    }
}
