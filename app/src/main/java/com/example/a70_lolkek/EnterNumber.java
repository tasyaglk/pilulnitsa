package com.example.a70_lolkek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterNumber extends AppCompatActivity {

    private EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);

        editTextPhone = findViewById(R.id.editTextPhone);
        Button buttonGetCode = findViewById(R.id.button4);
        buttonGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editTextPhone.getText().toString();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Введите номер телефона", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(EnterNumber.this, EnterCode.class);
                    intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);
                }
            }
        });
    }
}
