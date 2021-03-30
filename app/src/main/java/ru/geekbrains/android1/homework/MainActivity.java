package ru.geekbrains.android1.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String keyCounters = "Calc";
    private Calc calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calc.activity = this;
        if (savedInstanceState == null)
            calc = new Calc();
        initButtonListener();
        setSwitchBehavior();
    }

    private void initButtonListener() {
        int[] numberButtonIds = new int[]{R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonPoint, R.id.buttonEqual, R.id.buttonDiv, R.id.buttonMul,
                R.id.buttonSub, R.id.buttonAdd, R.id.buttonCancel};

        for (int numberButtonId : numberButtonIds)
            findViewById(numberButtonId).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView textFormula = findViewById(R.id.textFormula);
        EditText textValue = findViewById(R.id.textValue);
        Button b = (Button) v;
        calc.setCurrentChar(b.getText().toString());
        textFormula.setText(calc.getStrFormula());
        textValue.setText(calc.getStrValue());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(keyCounters, calc);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calc = (Calc) savedInstanceState.getParcelable(keyCounters);
        setViews();
    }

    private void setViews() {
        TextView textFormula = findViewById(R.id.textFormula);
        EditText textValue = findViewById(R.id.textValue);
        textFormula.setText(calc.getStrFormula());
        textValue.setText(calc.getStrValue());
    }

    private void setSwitchBehavior() {
        SwitchMaterial switchDark = findViewById(R.id.switchDark);
        SwitchMaterial switchImage = findViewById(R.id.switchImage);
        ImageView imageView = findViewById(R.id.imageView);

        switchDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        });
        switchImage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                imageView.setImageResource(R.drawable.background1);
            else
                imageView.setImageResource(0);
        });
    }


}
