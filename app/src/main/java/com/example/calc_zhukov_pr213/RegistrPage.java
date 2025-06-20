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

        // Обработка регистрации пользователя
        btnRegister.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String email = emailInputReg.getText().toString();
            String password = passwordInputReg.getText().toString();
            String repeatPassword = passwordRepeatInput.getText().toString();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(repeatPassword)) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 4) {
                Toast.makeText(this, "Пароль должен быть не менее 4 символов", Toast.LENGTH_SHORT).show();
            } else {
                // Сохраняю данные в SharedPreferences для временного хранения
                SharedPreferences sharedPreferences = getSharedPreferences("User  Prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME", username);
                editor.putString("EMAIL", email);
                editor.putString("PASSWORD", password);
                editor.apply();

                Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("USERNAME", username); // Передаем имя пользователя
                startActivity(intent);
            }
        });

        // Переход на экран авторизации
        txtGoAuth.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });
    }
}
