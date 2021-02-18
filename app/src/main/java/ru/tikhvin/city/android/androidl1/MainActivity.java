package ru.tikhvin.city.android.androidl1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  Integer arg1;
    private  Integer arg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        CheckBox round =  findViewById(R.id.round);
        RadioGroup radio = findViewById(R.id.radio);


        button.setOnClickListener(view -> {
            int selectedId = radio.getCheckedRadioButtonId();
            RadioButton radioButton =  findViewById(selectedId);
            String action = radioButton.getText().toString();
            EditText input1 = findViewById(R.id.input1);
            EditText input2 = findViewById(R.id.input2);
            TextView result = findViewById(R.id.result);
            String res = numberAction(input1, input2, action, round);
            result.setText(res);
        });
    }

    private String numberAction( EditText a, EditText b, String c , CheckBox r) {
        float d = 0;
        try {
            arg1 = Integer.valueOf(a.getText().toString());
            arg2 = Integer.valueOf(b.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Enter a number";
        }

        switch (c) {
            case "+": d = (float) arg1 + arg2;
                break;
            case "-": d = (float) arg1 - arg2;
                break;
            case "*": d = (float) arg1 * arg2;
                break;
            case "/": d = (float) arg1 / arg2;
                break;
            default: return  "Что-то пошло не так";
        }

        if(r.isChecked()) {
            return  String.format("%.2f", d);
        }

        return Float.toString(d);
    }

}