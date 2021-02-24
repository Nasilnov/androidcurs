package ru.tikhvin.city.android.androidl1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<View> allButtons;
    protected TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard_linear);
        allButtons = ((LinearLayout) findViewById(R.id.button_container)).getTouchables();
        for (int i = 0; i < allButtons.size(); i++) {
            allButtons.get(i).setOnClickListener(this);
        }
        result = findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        Button b;
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
            case R.id.buttonPt:
                b = (Button) v;
                result.setText(result.getText() + b.getText().toString());
                break;
            default: break;
        }

    }


//    private String numberAction( EditText a, EditText b, String c , CheckBox r) {
//        float d = 0;
//        try {
//            arg1 = Integer.valueOf(a.getText().toString());
//            arg2 = Integer.valueOf(b.getText().toString());
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return "Enter a number";
//        }
//
//        switch (c) {
//            case "+": d = (float) arg1 + arg2;
//                break;
//            case "-": d = (float) arg1 - arg2;
//                break;
//            case "*": d = (float) arg1 * arg2;
//                break;
//            case "/": d = (float) arg1 / arg2;
//                break;
//            default: return  "Что-то пошло не так";
//        }
//
//        return Float.toString(d);
//    }

}