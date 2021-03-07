package ru.tikhvin.city.android.androidl1;

import android.content.Intent;
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
    private static boolean isActiveBar = false;

    public static final String KEY_NAME = "our.setting";
    public static final int CODE_SETTING = 99;

    public static final String CALC_PREFIX = CalcActivity.class.getCanonicalName();
    public static final String CALC_ARG1 = CALC_PREFIX + ".AGR1";
    public static final String CALC_ARG2 = CALC_PREFIX + ".AGR2";
    public static final String CALC_OPERATOR = CALC_PREFIX + ".OPERATOR";
    public static final String CALC_FLAG = CALC_PREFIX + ".FLAG";
    public static final String CALC_RESULT = CALC_PREFIX + ".RESULT";
    public static final String CALC_EXPR = CALC_PREFIX + ".EXPR";

    ArrayList<View> mAllButtons;
    protected TextView mResult;
    protected TextView mExrp;
    private  String mArg1 = "";
    private  String mArg2 = "";
    private  String mOperator = "";
    private int mFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isActiveBar = getSharedPreferences(prefs, MODE_PRIVATE).
            getBoolean(pref_name, false);
        if (isActiveBar) {
            setTheme(R.style.Theme1);
        } else {
            setTheme(R.style.Theme2);
        }


        setContentView(R.layout.keyboard_linear);

        mAllButtons = ((LinearLayout) findViewById(R.id.button_container)).getTouchables();
        for (int i = 0; i < mAllButtons.size(); i++) {
            mAllButtons.get(i).setOnClickListener(this);
        }
        mResult = findViewById(R.id.result);
        mExrp = findViewById(R.id.expr);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent result) {
        super.onActivityResult(requestCode, responseCode, result);

        SharedPreferences sharedPreferences = getSharedPreferences(prefs, MODE_PRIVATE);

        if ( requestCode != CODE_SETTING ) {
            super.onActivityResult(requestCode, responseCode, result);
            return;
        }
        if ( responseCode == RESULT_OK) {
            boolean theme = result.getBooleanExtra(KEY_NAME, false);
            sharedPreferences.edit().
                    putBoolean(pref_name, theme).apply();
            recreate();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(CALC_ARG1, mArg1);
        state.putString(CALC_ARG2, mArg2);
        state.putString(CALC_OPERATOR, mOperator);
        state.putString(CALC_RESULT, (String) mResult.getText());
        state.putInt(CALC_FLAG, mFlag);
        state.putString(CALC_EXPR, (String) mExrp.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mArg1       = state.getString(CALC_ARG1);
        mArg2       = state.getString(CALC_ARG2);
        mOperator   = state.getString(CALC_OPERATOR);
        mFlag       = state.getInt(CALC_FLAG);
        mResult.setText(state.getString(CALC_RESULT));
        mExrp.setText(state.getString(CALC_EXPR));
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
                setExpression((String) b.getText());
                break;
            case R.id.button_proc:
                if(!mArg1.equals("") && !mArg1.equals("")) {
                    setExpression((String) b.getText());
                    mArg2 =  Float.toString(Float.parseFloat((String) mResult.getText()) * Float.parseFloat(mArg1) / 100 );
                    mResult.setText(numberAction());
                    mArg1 = "";
                    mArg2 = "";
                    mOperator = "";
                }
                break;
            case R.id.button_equal:
                if(!mArg1.equals("") && !mArg1.equals("")) {
                    setExpression((String) b.getText());
                    mArg2 = (String) mResult.getText();
                    mResult.setText(numberAction());
                    mArg1 = "";
                    mArg2 = "";
                    mOperator = "";
                }
                break;
            case R.id.button_setting:
                intentSetting(v);
                break;
            default: break;
        }

    }

    private void intentSetting(View v) {
        Intent intent = new Intent(this, Setting.class);
        intent.putExtra(KEY_NAME, isActiveBar);
        startActivityForResult(intent, CODE_SETTING);
    }


    private void clear() {
        mResult.setText("");
        mExrp.setText("");
        mArg1 ="";
        mArg2 = "";
        mOperator = "";
    }

    private void setExpression(String operator) {
        String res = mArg1 + " " +  mOperator + " " + (String) mResult.getText();
        mExrp.setText( res );
        if (operator.equals("=") ) {
            mExrp.setText(res + " " + operator);
        }
        if (operator.equals("%")) {
            mExrp.setText(res + operator + " =");
        }
    }

    private void setOperation(String operator) {
        if ( !mArg1.equals("") && mArg2.equals("")) {
            try {
                float ex = Float.valueOf((String) mResult.getText());
                mArg2 = (String) mResult.getText();
                mResult.setText("");
                mArg1 = numberAction();
                mArg2 = "";
                mOperator = operator;
                mFlag = 1;
            } catch (NumberFormatException e) {
                mOperator = operator;
            }
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