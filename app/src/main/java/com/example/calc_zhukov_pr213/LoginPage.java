package com.example.calc_zhukov_pr213;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        // Обработка нажатия кнопки входа
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginPage.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // Проверка данных в SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("User Prefs", MODE_PRIVATE);
            String savedEmail = sharedPreferences.getString("EMAIL", null);
            String savedPassword = sharedPreferences.getString("PASSWORD", null);

            if (email.equals(savedEmail) && password.equals(savedPassword)) {
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginPage.this, "Неверный email или пароль", Toast.LENGTH_SHORT).show();
            }
        });

        // Переход на страничку регистрации
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, RegistrPage.class);
            startActivity(intent);
        });
    }
}
