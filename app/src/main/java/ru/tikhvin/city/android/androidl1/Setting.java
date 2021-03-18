package ru.tikhvin.city.android.androidl1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;

import com.google.android.material.button.MaterialButton;

public class Setting extends AppCompatActivity {
    private Switch mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        boolean isChecked = intent.getBooleanExtra(CalcActivity.KEY_NAME, false);

        mTheme = (Switch) findViewById(R.id.switch_theme);
        mTheme.setChecked(isChecked);

        MaterialButton buttonClose = findViewById(R.id.button_close);
        buttonClose.setOnClickListener((view) -> {
                    Intent result = new Intent();
                    result.putExtra(CalcActivity.KEY_NAME, (boolean) mTheme.isChecked());
                    setResult(RESULT_OK, result);
                    finish();
                }
        );
    }
}