package com.example.calc_zhukov_pr213;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText inputRangeStart, inputRangeEnd;
    private Button btnSearch, btnClear, btnHistory;
    private Button btnTask1, btnTask2, btnTask3, btnTask4;
    private TextView logsTextView;
    private int currentTask = 1; // 1 - Цукермана, 2 - Нивена, 3 - Лишрел, 4 - Армстронга

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        inputRangeStart = findViewById(R.id.inputRangeStart);
        inputRangeEnd = findViewById(R.id.inputRangeEnd);
        btnSearch = findViewById(R.id.btnSearch);
        btnClear = findViewById(R.id.btnClear);
        btnHistory = findViewById(R.id.btnHistory);
        btnTask1 = findViewById(R.id.btnTask1);
        btnTask2 = findViewById(R.id.btnTask2);
        btnTask3 = findViewById(R.id.btnTask3);
        btnTask4 = findViewById(R.id.btnTask4);
        logsTextView = findViewById(R.id.logsTextView);

        // Получаем имя пользователя из Intent
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            LogTime.addLog("Добро пожаловать, " + username + "!");
        }

        // Обработчики кнопок задач
        btnTask1.setOnClickListener(v -> selectTask(1, "Цукермана"));
        btnTask2.setOnClickListener(v -> selectTask(2, "Нивена"));
        btnTask3.setOnClickListener(v -> selectTask(3, "Лишрел"));
        btnTask4.setOnClickListener(v -> selectTask(4, "Армстронга"));

        // Кнопка поиска
        btnSearch.setOnClickListener(v -> searchNumbers());

        // Кнопка очистки
        btnClear.setOnClickListener(v -> {
            logsTextView.setText(""); // Очищаем текстовое поле
            LogTime.clearLogs(); // Очищаем логи
        });

        // Кнопка истории
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryPage.class);
            intent.putStringArrayListExtra("HISTORY", LogTime.getLogs()); // Передаем историю
            intent.putExtra("USERNAME", username); // Передаем имя пользователя
            startActivity(intent);
        });

        // Инициализация
        updateTaskButtons();
        displayLogs();
    }

    private void selectTask(int task, String taskName) {
        currentTask = task;
        updateTaskButtons();
        LogTime.addLog("Выбрана задача: Поиск чисел " + taskName);
        displayLogs();
    }

    private void searchNumbers() {
        String startStr = inputRangeStart.getText().toString();
        String endStr = inputRangeEnd.getText().toString();

        if (startStr.isEmpty() || endStr.isEmpty()) {
            LogTime.addLog("Ошибка: Введите диапазон");
            displayLogs();
            return;
        }

        try {
            int start = Integer.parseInt(startStr);
            int end = Integer.parseInt(endStr);

            if (start >= end) {
                LogTime.addLog("Ошибка: Начало диапазона должно быть меньше конца");
                displayLogs();
                return;
            }

            LogTime.addLog("Поиск в диапазоне от " + start + " до " + end);
            findSpecialNumbers(start, end);
        } catch (NumberFormatException e) {
            LogTime.addLog("Ошибка: Введите корректные числа");
            displayLogs();
        }
    }

    private void updateTaskButtons() {
        int colorSelected = ContextCompat.getColor(this, R.color.button1Col);
        int colorNormal = ContextCompat.getColor(this, R.color.button2Col);

        btnTask1.setBackgroundColor(currentTask == 1 ? colorSelected : colorNormal);
        btnTask2.setBackgroundColor(currentTask == 2 ? colorSelected : colorNormal);
        btnTask3.setBackgroundColor(currentTask == 3 ? colorSelected : colorNormal);
        btnTask4.setBackgroundColor(currentTask == 4 ? colorSelected : colorNormal);
    }

    private void displayLogs() {
        StringBuilder logBuilder = new StringBuilder();
        for (String log : LogTime.getLogs()) {
            logBuilder.append(log).append("\n");
        }
        logsTextView.setText(logBuilder.toString());
    }

    private void findSpecialNumbers(int start, int end) {
        switch (currentTask) {
            case 1:
                findZuckermanNumbers(start, end);
                break;
            case 2:
                findNivenNumbers(start, end);
                break;
            case 3:
                findLychrelNumbers(start, end);
                break;
            case 4:
                findArmstrongNumbers(start, end);
                break;
        }
    }

    private void findZuckermanNumbers(int start, int end) {
        LogTime.addLog("Поиск чисел Цукермана...");
        for (int i = start; i <= end; i++) {
            if (isZuckermanNumber(i)) {
                LogTime.addLog("Найдено число Цукермана: " + i);
            }
        }
        LogTime.addLog("Поиск завершен");
        displayLogs();
    }

    private void findNivenNumbers(int start, int end) {
        LogTime.addLog("Поиск чисел Нивена...");
        for (int i = start; i <= end; i++) {
            if (isNivenNumber(i)) {
                LogTime.addLog("Найдено число Нивена: " + i);
            }
        }
        LogTime.addLog("Поиск завершен");
        displayLogs();
    }

    private void findLychrelNumbers(int start, int end) {
        LogTime.addLog("Поиск чисел Лишрел (" + start + "-" + end + ")");
        boolean found = false;

        for (int i = start; i <= end; i++) {
            if (isLychrelNumber(i)) {
                LogTime.addLog(String.valueOf(i));
                found = true;
            }
        }

        if (!found) {
            LogTime.addLog("Чисел Лишрел не найдено");
        }
        displayLogs();
    }

    private void findArmstrongNumbers(int start, int end) {
        LogTime.addLog("Поиск чисел Армстронга...");
        for (int i = start; i <= end; i++) {
            if (isArmstrongNumber(i)) {
                LogTime.addLog("Найдено число Армстронга: " + i);
            }
        }
        LogTime.addLog("Поиск завершен");
        displayLogs();
    }

    private boolean isZuckermanNumber(int number) {
        if (number == 0) return false;

        int product = 1;
        int temp = number;

        while (temp != 0) {
            int digit = temp % 10;
            if (digit == 0) return false;
            product *= digit;
            temp /= 10;
        }

        return number % product == 0;
    }

    private boolean isNivenNumber(int number) {
        if (number == 0) return false;

        int sum = 0;
        int temp = number;

        while (temp != 0) {
            sum += temp % 10;
            temp /= 10;
        }

        return number % sum == 0;
    }

    private boolean isLychrelNumber(int number) {
        long current = number;
        // 30 итераций +- достаточно
        for (int i = 0; i < 30; i++) {
            long reversed = reverseNumber(current);
            if (current == reversed && i > 0) {
                return false; // Нашли палиндром - не Лишрел
            }
            try {
                current = Math.addExact(current, reversed);
            } catch (ArithmeticException e) {
                return true; // Переполнение - считаем Лишрел
            }
        }
        return true; // Не нашли палиндром за 30 итераций - Лишрел
    }

    private boolean isArmstrongNumber(int number) {
        int originalNumber = number;
        int sum = 0;
        int digits = String.valueOf(number).length();

        while (number != 0) {
            int digit = number % 10;
            sum += Math.pow(digit, digits);
            number /= 10;
        }

        return sum == originalNumber; // Сравниваем сумму с оригинальным числом
    }

    private long reverseNumber(long number) {
        long reversed = 0;
        while (number != 0) {
            reversed = reversed * 10 + number % 10;
            number /= 10;
        }
        return reversed;
    }
}
