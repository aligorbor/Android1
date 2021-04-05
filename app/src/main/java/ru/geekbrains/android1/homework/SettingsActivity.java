package ru.geekbrains.android1.homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SettingsActivity extends AppCompatActivity implements Constants {
    private static final int backgroundResource = R.drawable.background1;
    private int maxPrecision;
    SwitchMaterial switchDark;
    SwitchMaterial switchImage;
    ImageView imageView1;
    TextInputEditText precision;
    CalcSettings calcSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        maxPrecision = decFormatToNumber(getString(R.string.decimalFormat));
        calcSettings = getIntent().getExtras().getParcelable(CALC_SETTINGS);
        initViews();
    }

    private void initViews() {
        switchDark = findViewById(R.id.switchDark);
        switchImage = findViewById(R.id.switchImage);
        imageView1 = findViewById(R.id.imageView1);

        precision = findViewById(R.id.inputPrecision);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intentResult = new Intent();
            fillCalcSettings();
            intentResult.putExtra(CALC_SETTINGS, calcSettings);
            setResult(RESULT_OK, intentResult);
            finish();
        });

        switchDark.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        if (calcSettings.getResourceBackground() == 0) {
            switchImage.setChecked(false);
            imageView1.setImageResource(0);

        } else {
            switchImage.setChecked(true);
            imageView1.setImageResource(backgroundResource);
        }
        setSwitchBehavior();

        precision.setText(String.valueOf(decFormatToNumber(calcSettings.getStrDecFormat())));
        precision.setSelection(0, precision.length());
    }

    private void fillCalcSettings() {
        if (switchDark.isChecked())
            calcSettings.setModeNight(AppCompatDelegate.MODE_NIGHT_YES);
        else
            calcSettings.setModeNight(AppCompatDelegate.MODE_NIGHT_NO);

        if (switchImage.isChecked())
            calcSettings.setResourceBackground(backgroundResource);
        else
            calcSettings.setResourceBackground(0);

        String s = NumberToDecFormat(Integer.parseInt(precision.getText().toString()));
        calcSettings.setStrDecFormat(s);
    }

    private void setSwitchBehavior() {
        switchDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            //   getDelegate().applyDayNight();
        });
        switchImage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                imageView1.setImageResource(backgroundResource);
            } else {
                imageView1.setImageResource(0);
            }
        });
    }

    private int decFormatToNumber(String decFormat) {
        //#.######
        return decFormat.length() - 2;
    }

    private String NumberToDecFormat(int precision) {
        //#.######
        String str = "#.";
        if (precision < 1 || precision > maxPrecision) precision = maxPrecision;
        while (str.length() - 2 < precision) str = str.concat("#");
        return str;
    }
}