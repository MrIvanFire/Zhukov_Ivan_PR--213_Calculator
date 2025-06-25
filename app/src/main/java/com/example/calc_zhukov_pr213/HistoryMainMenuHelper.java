package com.example.calc_zhukov_pr213;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoryMainMenuHelper {
    private static final ArrayList<String> allLogs = new ArrayList<>();
    private static final ArrayList<String> currentSessionLogs = new ArrayList<>();


    //Реализую логику схожую с предыдущим классом LogTime
    public static void addLog(String message) {
        // Получаем текущее время
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 6);
        Date dateWithOffset = calendar.getTime();
        String timestamp = new SimpleDateFormat("[MM.dd, HH:mm:ss.SSS]", Locale.getDefault()).format(dateWithOffset);
        String logEntry = timestamp + " " + message;

        allLogs.add(logEntry);
        currentSessionLogs.add(logEntry);
    }

    public static ArrayList<String> getAllLogs() {
        return new ArrayList<>(allLogs); // Возвращаем копию, чтобы избежать модификаций
    }

    public static ArrayList<String> getCurrentSessionLogs() {
        return new ArrayList<>(currentSessionLogs);
    }

    public static void clearCurrentSessionLogs() {
        currentSessionLogs.clear();
    }

    public static void clearAllLogs() {
        allLogs.clear();
        currentSessionLogs.clear();
    }
}
