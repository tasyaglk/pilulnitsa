package com.example.a70_lolkek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EventEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

    }

    void initAllPicker() {
        initPicker(0, 12, numPickerH);
        initPicker(0, 59, numPickerM);
        initPickerWithString(0, (str.size - 1), numPickerAm, str);
    }
}