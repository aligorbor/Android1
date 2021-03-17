package ru.geekbrains.android1.homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private Button buttonNext;
    private CheckBox checkBox;
    private EditText editText;
    private EditText editText2;
    private Switch aSwitch;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnBtnClickListener();
        setCheckboxBehavior();
        setaSwitchBehavior();
        setToggleButtonBehavior();
    }


    private void initViews() {
        buttonNext = findViewById(R.id.buttonNext);
        checkBox = findViewById(R.id.checkBox);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        aSwitch = findViewById(R.id.switch1);
        toggleButton = findViewById(R.id.toggleButton);
    }

    private void setOnBtnClickListener() {
        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TextActivity.class);
            startActivity(intent);
        });
    }

    private void changeTextColor(int colorRes) {
        int color = ContextCompat.getColor(getApplicationContext(), colorRes);
        editText.setTextColor(color);
        editText2.setTextColor(color);

    }

    private void setCheckboxBehavior() {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                changeTextColor(R.color.red);
            } else {
                changeTextColor(R.color.teal_700);
            }
        });
    }

    private void setaSwitchBehavior() {
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String string = editText.getText().toString();
            editText.setText(editText2.getText().toString());
            editText2.setText(string);
        });
    }

    private void setToggleButtonBehavior() {
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editText.setTextSize(30);
                editText2.setTextSize(30);
            } else {
                editText.setTextSize(20);
                editText2.setTextSize(20);
            }
        });

    }

}

