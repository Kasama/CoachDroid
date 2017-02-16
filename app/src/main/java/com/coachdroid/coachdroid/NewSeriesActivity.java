package com.coachdroid.coachdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.coachdroid.coachdroid.db.Series;

public class NewSeriesActivity extends AppCompatActivity {

    EditText name;
    EditText description;
    EditText times;
    EditText length;
    NumberPicker minutesPicker;
    NumberPicker secondsPicker;

    private static final int SECONDS = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_series);

        name = (EditText) findViewById(R.id.exercice_name);
        description = (EditText) findViewById(R.id.description);
        times = (EditText) findViewById(R.id.times);
        length = (EditText) findViewById(R.id.length);
        minutesPicker = (NumberPicker) findViewById(R.id.npMinutes);
        secondsPicker = (NumberPicker) findViewById(R.id.npSeconds);

        String[] values = new String[SECONDS];
        for (int i = 0; i < values.length; i++){
            values[i] = String.valueOf(i);
        }
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(values.length - 1);
        secondsPicker.setWrapSelectorWheel(true);
        secondsPicker.setDisplayedValues(values);

        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(values.length - 1);
        minutesPicker.setWrapSelectorWheel(true);
        minutesPicker.setDisplayedValues(values);

        minutesPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            minutesPicker.setValue(newVal);
        });

    }

    public void onClickCancel(View view){
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }

    public void onClickCreate(View view){
        int i_times, i_length;
        i_times = Integer.parseInt(times.getText().toString());
        i_length = Integer.parseInt(length.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(Series.NAME, name.getText().toString());
        intent.putExtra(Series.DESCRIPTION, description.getText().toString());
        intent.putExtra(Series.LENGTH, i_length);
        intent.putExtra(Series.TIMES, i_times);
        setResult(RESULT_OK, intent);
        finish();
    }
}
