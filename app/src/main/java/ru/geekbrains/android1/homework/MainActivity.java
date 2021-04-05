package ru.geekbrains.android1.homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Constants {
    private Calc calc;
    private CalcSettings calcSettings;
    private ImageView imageView;
    private TextView textFormula;
    private EditText textValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Calc.activity = this;
        calc = new Calc();

        calcSettings = new CalcSettings();
        if (savedInstanceState == null) {
            calcSettings.setStrDecFormat(calc.getStrDecFormat());
            calcSettings.getPreferences(this);
            AppCompatDelegate.setDefaultNightMode(calcSettings.getModeNight());
            populateSettings();
        }
        initButtonListener();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            calc.parseCalcStr(bundle.getString(CALC_STRING));
            textFormula.setText(calc.getStrFormula());
            textValue.setText(calc.getStrValue());
        }
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
        imageView.setTag(0);
        textFormula = findViewById(R.id.textFormula);
        textValue = findViewById(R.id.textValue);
    }

    private void initButtonListener() {
        int[] numberButtonIds = new int[]{R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonPoint, R.id.buttonEqual, R.id.buttonDiv, R.id.buttonMul,
                R.id.buttonSub, R.id.buttonAdd, R.id.buttonCancel};

        for (int numberButtonId : numberButtonIds)
            findViewById(numberButtonId).setOnClickListener(this);

        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> {
            Intent runSettings = new Intent(MainActivity.this, SettingsActivity.class);
            fillCalcSettings();
            runSettings.putExtra(CALC_SETTINGS, calcSettings);
            //  startActivity(runSettings);
            startActivityForResult(runSettings, REQUEST_CODE_SETTING_ACTIVITY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != REQUEST_CODE_SETTING_ACTIVITY) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == RESULT_OK) {
            calcSettings = data.getParcelableExtra(CALC_SETTINGS);
            calcSettings.setPreferences(this);
            populateSettings();
        }
    }

    private void populateSettings() {
        imageView.setImageResource(calcSettings.getResourceBackground());
        imageView.setTag(calcSettings.getResourceBackground());
        calc.setStrDecFormat(calcSettings.getStrDecFormat());
    }

    private void fillCalcSettings() {
        calcSettings.setModeNight(AppCompatDelegate.getDefaultNightMode());
        calcSettings.setResourceBackground((Integer) imageView.getTag());
        calcSettings.setStrDecFormat(calc.getStrDecFormat());
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        calc.setCurrentChar(b.getText().toString());
        textFormula.setText(calc.getStrFormula());
        textValue.setText(calc.getStrValue());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(keyCounters, calc);
        outState.putParcelable(keySettings, calcSettings);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calc = (Calc) savedInstanceState.getParcelable(keyCounters);
        calcSettings = (CalcSettings) savedInstanceState.getParcelable(keySettings);
        setViews();
    }

    private void setViews() {
        textFormula.setText(calc.getStrFormula());
        textValue.setText(calc.getStrValue());
        populateSettings();
    }
}
