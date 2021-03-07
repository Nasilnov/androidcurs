package ru.tikhvin.city.android.androidl1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String prefs = "prefs.xml";
    private static final String pref_name = "theme";

    public static final String CALC_PREFIX = CalcActivity.class.getCanonicalName();
    public static final String CALC_ARG1 = CALC_PREFIX + ".AGR1";
    public static final String CALC_ARG2 = CALC_PREFIX + ".AGR2";
    public static final String CALC_OPERATOR = CALC_PREFIX + ".OPERATOR";
    public static final String CALC_FLAG = CALC_PREFIX + ".FLAG";
    public static final String CALC_RESULT = CALC_PREFIX + ".RESULT";

    ArrayList<View> mAllButtons;
    protected TextView mResult;
    private  String mArg1 = "";
    private  String mArg2 = "";
    private  String mOperator = "";
    private int mFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isActiveBar = getSharedPreferences(prefs, MODE_PRIVATE).
                getBoolean(pref_name, false);
        if (isActiveBar) {
            setTheme(R.style.Theme1);
        } else {
            setTheme(R.style.Theme2);
        }


        setContentView(R.layout.keyboard_linear);

        Switch themeSwitch = findViewById(R.id.switch_theme);
        themeSwitch.setOnCheckedChangeListener(
                (CompoundButton buttonView, boolean isChecked) -> {
                    SharedPreferences sharedPreferences = getSharedPreferences(prefs, MODE_PRIVATE);
                    if (sharedPreferences.getBoolean(pref_name, false) != isChecked) {
                        sharedPreferences.edit().
                                putBoolean(pref_name, isChecked).apply();
                        recreate();
                    }
                });


        mAllButtons = ((LinearLayout) findViewById(R.id.button_container)).getTouchables();
        for (int i = 0; i < mAllButtons.size(); i++) {
            mAllButtons.get(i).setOnClickListener(this);
        }
        mResult = findViewById(R.id.result);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(CALC_ARG1, mArg1);
        state.putString(CALC_ARG2, mArg2);
        state.putString(CALC_OPERATOR, mOperator);
        state.putString(CALC_RESULT, (String) mResult.getText());
        state.putInt(CALC_FLAG, mFlag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mArg1       = state.getString(CALC_ARG1);
        mArg2       = state.getString(CALC_ARG2);
        mOperator   = state.getString(CALC_OPERATOR);
        mFlag       = state.getInt(CALC_FLAG);
        mResult.setText(state.getString(CALC_RESULT));
    }


    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        switch (v.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                if (mFlag == 1) {
                    mResult.setText("");
                }
                mFlag = 0;
                mResult.setText(mResult.getText() + b.getText().toString());
                break;
            case R.id.buttonPt:
                if (!checkPoint((String) mResult.getText())) {
                    mResult.setText(mResult.getText() + b.getText().toString());
                }
                break;
            case R.id.button_clear:
                clear();
                break;

            case R.id.button_back:
                mResult.setText(backSpace((String) mResult.getText()));
                break;
            case R.id.button_plus:
            case R.id.button_minus:
            case R.id.button_div:
            case R.id.button_mult:
                setOperation((String) b.getText());
                break;
            case R.id.button_proc:
                if(!mArg1.equals("") && !mArg1.equals("")) {
                    mArg2 =  Float.toString(Float.parseFloat((String) mResult.getText()) * Float.parseFloat(mArg1) / 100 );
                    mResult.setText(numberAction());
                    mArg1 = "";
                    mArg2 = "";
                    mOperator = "";
                }
                break;
            case R.id.button_equal:
                if(!mArg1.equals("") && !mArg1.equals("")) {
                    mArg2 = (String) mResult.getText();
                    mResult.setText(numberAction());
                    mArg1 = "";
                    mArg2 = "";
                    mOperator = "";
                }
                break;
            default: break;
        }

    }

    private void clear() {
        mResult.setText("");
        mArg1 ="";
        mArg2 = "";
        mOperator = "";
    }

    private void setOperation(String operator) {

        if ( !mArg1.equals("") && mArg2.equals("")) {
            mArg2 = (String) mResult.getText();
            mResult.setText(numberAction());
            mArg1 = numberAction();
            mArg2 = "";
            mOperator = operator;
            mFlag = 1;
            return;
        }

        if ( mArg1.equals("") ) {
            mArg1 = (String) mResult.getText();
            mOperator = operator;
            mResult.setText("");
            return;
        }

    }


    private boolean checkPoint(String str) {
         if (str.contains(".")) {
             return true;
         }
         else return false;
    }

    private String backSpace (String str) {
        if (str.length() > 0 ) {
            return str.substring(0, str.length() - 1);
        } else return "";
    }


    private String numberAction() {
        float arg1;
        float arg2;
        float res = 0;

        try {
            arg1 = Float.valueOf(mArg1);
            arg2 = Float.valueOf(mArg2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mArg1 = "";
            mArg2 = "";
            mOperator = "";
            return "Error";
        }

        switch (mOperator) {
            case "+": res = arg1 + arg2;
                break;
            case "-": res = arg1 - arg2;
                break;
            case "*": res =  arg1 * arg2;
                break;
            case "/": res = arg1 / arg2;
                break;
        }

        return Float.toString(res);
    }

}