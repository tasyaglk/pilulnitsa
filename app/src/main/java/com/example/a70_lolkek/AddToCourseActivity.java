package com.example.a70_lolkek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class AddToCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_course);
        TimePicker choose_time = findViewById(R.id.choose_time);
        choose_time.setIs24HourView(true);

    }

    public void onRadioButtonClicked(View view) {
    }
}