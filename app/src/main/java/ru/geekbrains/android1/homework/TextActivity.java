package ru.geekbrains.android1.homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class TextActivity extends Activity {
    private Button buttonBack;
    private CalendarView calendarView;
    private EditText editTextDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        initViews();
        setOnBtnClickListener();
        setCalendarViewBehavior();
    }

    private void initViews() {
        buttonBack = findViewById(R.id.buttonBack);
        calendarView = findViewById(R.id.calendarView);
        editTextDate = findViewById(R.id.editTextDate);
    }

    private void setOnBtnClickListener() {
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(TextActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void setCalendarViewBehavior() {
        calendarView.setOnDateChangeListener((calendarView1, year, month, day) -> {
            String string = day + "/" + month + "/" + year;
            editTextDate.setText(string);
        });
    }

}
