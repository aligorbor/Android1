package ru.geekbrains.android1.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    private void initButtonListener() {
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonPoint = findViewById(R.id.buttonPoint);
        Button buttonEqual = findViewById(R.id.buttonEqual);
        Button buttonDiv = findViewById(R.id.buttonDiv);
        Button buttonMul = findViewById(R.id.buttonMul);
        Button buttonSub = findViewById(R.id.buttonSub);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonPoint.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
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

}
