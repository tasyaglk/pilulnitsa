package com.example.a70_lolkek;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterCode extends AppCompatActivity {

    private TextView textViewPhone;
    private EditText editTextNumberPassword3;
    private Button button;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        textViewPhone = findViewById(R.id.textViewPhone);
        editTextNumberPassword3 = findViewById(R.id.editTextNumberPassword3);
        editTextNumberPassword3.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // чекаем что не больше 4 символов
                if (s.length() > 4) {
                    // типа очищение и вывод ошибки
                    editTextNumberPassword3.setText("");
                    Toast.makeText(EnterCode.this, "Максимальная длина кода - 4 символа", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button = findViewById(R.id.button);

        // тут номерокм получаем
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phoneNumber = extras.getString("phoneNumber");
            textViewPhone.setText(phoneNumber);
        }

        // "Отправить код"
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextNumberPassword3.getText().toString();
                // здесь надо отправить код на сервер для проверки - хз как
                // код верный
//                Intent intent = new Intent(EnterCode.this, NextActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}
