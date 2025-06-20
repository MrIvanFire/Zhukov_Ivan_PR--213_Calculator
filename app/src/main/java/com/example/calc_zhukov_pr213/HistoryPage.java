package com.example.calc_zhukov_pr213;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryPage extends AppCompatActivity {

    private Button backButton;
    private ListView historyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        backButton = findViewById(R.id.backButton);
        historyListView = findViewById(R.id.historyListView);

        // Получаем имя пользователя из Intent
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            TextView usernameTextView = findViewById(R.id.usernameTextView);
            usernameTextView.setText(username); // Устанавливаем имя пользователя
        }

        // Получаем историю из Intent
        ArrayList<String> historyData = getIntent().getStringArrayListExtra("HISTORY");
        List<Map<String, String>> displayData = new ArrayList<>();

        if (historyData != null) {
            for (String log : historyData) {
                Map<String, String> item = new HashMap<>();
                item.put("title", log); // Используем лог как заголовок
                displayData.add(item);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                displayData,
                android.R.layout.simple_list_item_1, // Используем простой элемент списка
                new String[]{"title"},
                new int[]{android.R.id.text1}
        );

        historyListView.setAdapter(adapter);

        // Кнопка назад на главную страницу
        backButton.setOnClickListener(v -> finish());
    }
}
