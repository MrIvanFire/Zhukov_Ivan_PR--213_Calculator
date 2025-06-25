package com.example.calc_zhukov_pr213;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrPage extends AppCompatActivity {

    private EditText usernameInput, emailInputReg, passwordInputReg, passwordRepeatInput;
    private Button btnRegister;
    private TextView txtGoAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr_page);

        usernameInput = findViewById(R.id.usernameInput);
        emailInputReg = findViewById(R.id.emailInputReg);
        passwordInputReg = findViewById(R.id.passwordInputReg);
        passwordRepeatInput = findViewById(R.id.passwordRepeatInput);
        btnRegister = findViewById(R.id.btnRegister);
        txtGoAuth = findViewById(R.id.txtGoAuth);

        // Обработка регистрации пользователя (Реализация валидации при заполнении полей)
        btnRegister.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String email = emailInputReg.getText().toString().trim();
            String password = passwordInputReg.getText().toString().trim();
            String repeatPassword = passwordRepeatInput.getText().toString().trim();

            // Проверка на пустоту
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // Проверка длины имени ( <= 20 символов)
            if (username.length() > 20) {
                usernameInput.setError("Максимум 20 символов");
                return;
            }

            // Простая проверка email (должен содержать @ и точку)
            if (!email.contains("@") || !email.contains(".")) {
                emailInputReg.setError("Некорректный email");
                return;
            }

            // Проверка длины пароля (4-8 символов)
            if (password.length() < 4 || password.length() > 8) {
                passwordInputReg.setError("Пароль должен быть от 4 до 8 символов");
                return;
            }

            // Проверка пароля на числовой формат
            try {
                Integer.parseInt(password);
            } catch (NumberFormatException e) {
                passwordInputReg.setError("Пароль должен содержать только цифры");
                return;
            }

            // Проверка совпадения паролей
            if (!password.equals(repeatPassword)) {
                passwordRepeatInput.setError("Пароли не совпадают");
                return;
            }

            // Сохраняю данные в SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USERNAME", username);
            editor.putString("EMAIL", email);
            editor.putString("PASSWORD", password);
            editor.apply();

            Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });

        // Переход на экран авторизации
        txtGoAuth.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginPage.class));
        });
    }
}