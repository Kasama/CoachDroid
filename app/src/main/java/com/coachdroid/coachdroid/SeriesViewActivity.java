package com.coachdroid.coachdroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.coachdroid.coachdroid.db.DBHandler;
import com.coachdroid.coachdroid.db.Schedule;

public class SeriesViewActivity extends AppCompatActivity {

    Schedule schedule;
    Toolbar toolbar;
    FloatingActionButton fab;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        schedule = Schedule.build(getIntent());
        toolbar.setTitle(schedule.getName());

        db = new DBHandler(this);

        fab.setOnClickListener(
                view -> finish()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_series_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirm)
                .setTitle(R.string.delete_confirm_title)
                .setNegativeButton(R.string.negative, (dialog, which) -> {})
                .setPositiveButton(R.string.positive, (dialog, which) -> {
                    db.delete(schedule);
                    setResult(RESULT_OK);
                    finish();
                }).show();
        return true;
    }
}
