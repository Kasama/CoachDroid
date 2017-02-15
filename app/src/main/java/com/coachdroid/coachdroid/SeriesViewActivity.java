package com.coachdroid.coachdroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.coachdroid.coachdroid.db.DBHandler;
import com.coachdroid.coachdroid.db.Schedule;
import com.coachdroid.coachdroid.db.Series;

import java.util.List;

public class SeriesViewActivity extends AppCompatActivity {

    private Schedule schedule;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DBHandler db;
    private List<Series> series;
    private ListView seriesList;
    private SeriesAdapter seriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        seriesList = (ListView) findViewById(R.id.listSeries);

        setSupportActionBar(toolbar);

        schedule = Schedule.build(getIntent());
        toolbar.setTitle(schedule.getName());

        db = new DBHandler(this);
        refreshList();

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

    private void refreshList(){
        series = db.allSeries(schedule);
        seriesAdapter = new SeriesAdapter(this, series);
        seriesList.setAdapter(seriesAdapter);
    }
}
