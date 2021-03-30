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
    private static final char ADD = '+';
    private static final char SUBTRACT = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';
    private static final char EQUAL = '=';
    private final char CANCEL;
    private static final char N0 = '0';
    private final char NPoint;

    private String valueOne;
    private String valueTwo;
    private char currentAction;
    private String strFormula;
    private String strValue;
    private char currentChar;
    private DecimalFormat decimalFormat;

    public Calc() {
        NPoint = activity.getString(R.string.point).charAt(0);
        CANCEL = activity.getString(R.string.cancel).charAt(0);

        strFormula = "";
        strValue = Character.toString(N0);
        valueOne = Character.toString(N0);
        valueTwo = "";
        currentAction = N0;

        initDecimalFormat();
    }

    protected Calc(Parcel in) {
        CANCEL = (char) in.readInt();
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
        dest.writeInt((int) CANCEL);
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
                if (strFormula.isEmpty())
                    valueOne = tryDigit(valueOne, currentChar);
                else {
                    valueOne = tryDigit("", currentChar);
                    strFormula = "";
                }
                strValue = valueOne;
            } else {
                valueTwo = tryDigit(valueTwo, currentChar);
                strValue = valueTwo;
            }

        } else if (currentChar == ADD || currentChar == SUBTRACT || currentChar == MULTIPLY || currentChar == DIVIDE || currentChar == EQUAL) {
            if (valueTwo.isEmpty()) {
                if (currentChar != EQUAL) {
                    currentAction = currentChar;
                    valueOne = toStrDecimalFormat(valueOne);
                    strFormula = valueOne + currentAction;
                }
            } else {
                valueTwo = toStrDecimalFormat(valueTwo);
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

        double result;
        switch (currentAction) {
            case ADD:
                result = v1 + v2;
                break;
            case SUBTRACT:
                result = v1 - v2;
                break;
            case MULTIPLY:
                result = v1 * v2;
                break;
            case DIVIDE:
                if (v2 == 0)
                    result = Double.NaN;
                else
                    result = v1 / v2;
                break;
            default:
                result = 0.;
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
                currentAction = N0;
            } else {
                strFormula = strRes + newAction;
                currentAction = newAction;
            }
            strValue = strRes;
            valueOne = strRes;
            valueTwo = "";
        }
    }

    private String tryDigit(String strValue, char chToAdd) {
        String newValue;
        if (strValue.isEmpty())
            newValue = Character.toString(N0) + chToAdd;
        else
            newValue = strValue + chToAdd;
        String strDecimal = toStrDecimalFormat(newValue);
        if (strDecimal.isEmpty())
            return strValue;

        if (chToAdd == NPoint && strValue.indexOf(NPoint) < 0)
            return strDecimal + NPoint;
        else if (chToAdd == N0 && strValue.indexOf(NPoint) > 0)
            return newValue;
        else
            return strDecimal;
    }

    private String toStrDecimalFormat(String strValue) {
        if (strValue.isEmpty())
            strValue = Character.toString(N0);
        double v = 0;
        try {
            Number n = decimalFormat.parse(strValue);
            if (n != null)
                v = n.doubleValue();

        } catch (NumberFormatException | ParseException e) {
            return "";
        }
        return decimalFormat.format(v);
    }

}
