package com.example.a70_lolkek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class CourseActivity extends AppCompatActivity {

    BottomNavigationFragment bottomNavigationFragment;
    Context context;
    private ImageView pill_plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        context = this;

        initWidgets();

        Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment)bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }

        pill_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pill.pillBox.isEmpty()) {
                    Toast.makeText(view.getContext(), "У вас нет лекарств в Пилюльнице", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(context, PillsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initWidgets() {
        pill_plus = findViewById(R.id.pill_plus);
    }
}