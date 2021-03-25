package ru.geekbrains.android1.homework;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class Calc implements Parcelable {
    public static AppCompatActivity activity;
    private final char ADD;
    private final char SUBTRACT;
    private final char MULTIPLY;
    private final char DIVIDE;
    private final char EQUAL;
    private final char CANCEL;
    private final char N0;
    private final char NPoint;

    private String valueOne;
    private String valueTwo;
    private char currentAction;
    private String strFormula;
    private String strValue;
    private char currentChar;
    private DecimalFormat decimalFormat;

    public Calc() {
        N0 = activity.getString(R.string._0).charAt(0);
        NPoint = activity.getString(R.string.point).charAt(0);
        ADD = activity.getString(R.string.add).charAt(0);
        SUBTRACT = activity.getString(R.string.sub).charAt(0);
        MULTIPLY = activity.getString(R.string.mul).charAt(0);
        DIVIDE = activity.getString(R.string.div).charAt(0);
        EQUAL = activity.getString(R.string.equal).charAt(0);
        CANCEL = activity.getString(R.string.cancel).charAt(0);

        strFormula = "";
        strValue = Character.toString(N0);
        valueOne = Character.toString(N0);
        valueTwo = "";
        currentAction = N0;

        initDecimalFormat();
    }

    protected Calc(Parcel in) {
        ADD = (char) in.readInt();
        SUBTRACT = (char) in.readInt();
        MULTIPLY = (char) in.readInt();
        DIVIDE = (char) in.readInt();
        EQUAL = (char) in.readInt();
        CANCEL = (char) in.readInt();
        N0 = (char) in.readInt();
        NPoint = (char) in.readInt();
        valueOne = in.readString();
        valueTwo = in.readString();
        currentAction = (char) in.readInt();
        strFormula = in.readString();
        strValue = in.readString();
        currentChar = (char) in.readInt();

        initDecimalFormat();
    }

    private void initDecimalFormat() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator(NPoint);
        decimalFormat = new DecimalFormat(activity.getString(R.string.decimalFormat), otherSymbols);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt((int) ADD);
        dest.writeInt((int) SUBTRACT);
        dest.writeInt((int) MULTIPLY);
        dest.writeInt((int) DIVIDE);
        dest.writeInt((int) EQUAL);
        dest.writeInt((int) CANCEL);
        dest.writeInt((int) N0);
        dest.writeInt((int) NPoint);
        dest.writeString(valueOne);
        dest.writeString(valueTwo);
        dest.writeInt((int) currentAction);
        dest.writeString(strFormula);
        dest.writeString(strValue);
        dest.writeInt((int) currentChar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Calc> CREATOR = new Creator<Calc>() {
        @Override
        public Calc createFromParcel(Parcel in) {
            return new Calc(in);
        }

        @Override
        public Calc[] newArray(int size) {
            return new Calc[size];
        }
    };

    public void setCurrentChar(String currentChar) {
        this.currentChar = currentChar.charAt(0);
        fill();
    }

    public String getStrFormula() {
        return strFormula;
    }

    public String getStrValue() {
        return strValue;
    }

    private void fill() {
        if (Character.isDigit(currentChar) || currentChar == NPoint) {
            if (currentAction == N0) {
                valueOne = tryDigit(valueOne, currentChar);
                strFormula = "";
                strValue = valueOne;
            } else {
                valueTwo = tryDigit(valueTwo, currentChar);
                strValue = valueTwo;
            }

        } else if (currentChar == ADD || currentChar == SUBTRACT || currentChar == MULTIPLY || currentChar == DIVIDE || currentChar == EQUAL) {
            if (valueTwo.isEmpty()) {
                if (currentChar != EQUAL) {
                    currentAction = currentChar;
                    strFormula = valueOne + currentAction;
                }
            } else {
                doCalc(currentChar);
            }
        } else if (currentChar == CANCEL) {
            valueOne = Character.toString(N0);
            currentAction = N0;
            valueTwo = "";
            strFormula = "";
            strValue = Character.toString(N0);
        }
    }

    private void doCalc(char newAction) {
        //double v1 = Double.parseDouble(valueOne);
        // double v2 = Double.parseDouble(valueTwo);
        double v1 = 0;
        double v2 = 0;
        try {
            Number n1 = decimalFormat.parse(valueOne);
            Number n2 = decimalFormat.parse(valueTwo);
            if (n1 != null)
                v1 = n1.doubleValue();
            if (n2 != null)
                v2 = n2.doubleValue();
        } catch (ParseException e) {
            Log.i("Calc", e.getMessage());
        }

        double result = 0.;
        if (currentAction == ADD)
            result = v1 + v2;
        else if (currentAction == SUBTRACT)
            result = v1 - v2;
        else if (currentAction == MULTIPLY)
            result = v1 * v2;
        else if (currentAction == DIVIDE) {
            if (v2 == 0)
                result = Double.NaN;
            else
                result = v1 / v2;
        }


        if (Double.isNaN(result)) {
            valueOne = Character.toString(N0);
            currentAction = N0;
            valueTwo = "";
            strFormula = "";
            strValue = activity.getString(R.string.err_zero_div);
        } else {
            String strRes = decimalFormat.format(result);
            if (newAction == EQUAL) {
                strFormula = valueOne + currentAction + valueTwo + newAction;
                strValue = strRes;
                valueOne = strRes;
                currentAction = N0;
                valueTwo = "";
            } else {
                strFormula = strRes + newAction;
                strValue = strRes;
                valueOne = strRes;
                currentAction = newAction;
                valueTwo = "";
            }
        }
    }

    private String tryDigit(String strValue, char chToAdd) {
        String newValue;
        if (strValue.isEmpty())
            newValue = Character.toString(N0) + chToAdd;
        else
            newValue = strValue + chToAdd;
        double v = 0;
        try {
            Number n = decimalFormat.parse(newValue);
            if (n != null)
                v = n.doubleValue();
            // v = Double.parseDouble(newValue);
        } catch (NumberFormatException | ParseException e) {
            return strValue;
        }
        if (chToAdd == NPoint && strValue.indexOf(NPoint) < 0)
            return decimalFormat.format(v) + NPoint;
        else
            return decimalFormat.format(v);
    }

}
