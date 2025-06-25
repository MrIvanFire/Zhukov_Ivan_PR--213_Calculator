package com.example.calc_zhukov_pr213;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// Класс для отображения текущего времени при использовании каких-либо элементов калькулятора
// Класс  не используется
public class LogTime {
    private static final ArrayList<String> logs = new ArrayList<>();

    public static void addLog(String message) {
        // Получаем текущее время
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 6);
        Date dateWithOffset = calendar.getTime();
        String timestamp = new SimpleDateFormat("[MM.dd, HH:mm:ss.SSS]", Locale.getDefault()).format(dateWithOffset);
        logs.add(timestamp + " " + message);
    }

    public static ArrayList<String> getLogs() {
        return logs;
    }

    public static void clearLogs() {
        logs.clear();
    }
}
