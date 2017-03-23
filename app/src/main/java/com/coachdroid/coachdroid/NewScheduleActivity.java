package com.coachdroid.coachdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.coachdroid.coachdroid.db.Schedule;

public class NewScheduleActivity extends AppCompatActivity {

    public static final String INTENT_NAME = "schedule_selection";

    private EditText textScheduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNewSchedule);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textScheduleName = (EditText) findViewById(R.id.textScheduleName);
    }

    public void onClickCreate(View view){
        Intent ret = new Intent(INTENT_NAME);
        Log.d("DEBUG: ", "text is '" + textScheduleName.getText().toString() + "'");
        if (textScheduleName.getText().toString().isEmpty()){
            ret.putExtra(Schedule.NAME, "");
            setResult(RESULT_CANCELED, ret);
        } else {
            ret.putExtra(Schedule.NAME, textScheduleName.getText().toString());
            setResult(RESULT_OK, ret);
        }
        finish();
    }

    public void cancel() {
        Intent ret = new Intent(INTENT_NAME);
        ret.putExtra(Schedule.NAME, "");
        setResult(RESULT_CANCELED, ret);
        finish();
    }

    public void onClickCancel(View view){
        cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) cancel();
        return true;
    }

}
