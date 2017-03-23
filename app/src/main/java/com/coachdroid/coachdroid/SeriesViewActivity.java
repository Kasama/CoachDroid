package com.coachdroid.coachdroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.coachdroid.coachdroid.db.DBHandler;
import com.coachdroid.coachdroid.db.Schedule;
import com.coachdroid.coachdroid.db.Series;

import java.util.List;
import static com.coachdroid.coachdroid.Constants.*;

public class SeriesViewActivity extends AppCompatActivity {

    private static final int NEW_SERIES = 1;
    private Schedule schedule;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DBHandler db;
    private List<Series> series;
    private ListView seriesList;
    private SeriesAdapter seriesAdapter;
    private boolean active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        seriesList = (ListView) findViewById(R.id.listSeries);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        schedule = Schedule.build(getIntent());
        toolbar.setTitle(schedule.getName());

        db = new DBHandler(this);
        refreshList();

        setInactive();

        seriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Log.d("debug", "DICKBUTT");
                Toast.makeText(SeriesViewActivity.this, "ELEFANTE", Toast.LENGTH_LONG).show();
                Series series = (Series) parent.getAdapter().getItem(pos);
//                new AlertDialog.Builder(SeriesViewActivity.this)
//                        .setMessage("Do you want do delete this exercise ?")
//                        .setTitle("Delete")
//                        .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
//                        .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                db.delete(series);
//                                //setResult(RESULT_OK);
//                                //finish();
//                                SeriesViewActivity.this.refreshList();
//                            }
//                        }).show();
            }
        });


        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "It's not possible to edit the Schedule during activity", Toast.LENGTH_LONG).show();
                        if (SeriesViewActivity.this.isActive())
                            new AlertDialog.Builder(SeriesViewActivity.this)
                                    .setMessage("It's not possible to edit the Schedule during activity, do you wish to reset then edit ?")
                                    .setTitle("Reset")
                                    .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            SeriesViewActivity.this.setInactive();
                                            SeriesViewActivity.this.refreshList();
                                        }
                                    }).show();
                        else {
                            Intent i = new Intent(SeriesViewActivity.this, NewSeriesActivity.class);
                            SeriesViewActivity.this.startActivityForResult(i, NEW_SERIES);
                        }
                    }
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case NEW_SERIES:
                if (resultCode == RESULT_OK){
                    data.putExtra(Series.SCHEDULE, schedule.getId());
                    Series s = Series.build(data);
                    db.save(s);
                    Intent i = new Intent(SeriesViewActivity.this, NewSeriesActivity.class);
                    SeriesViewActivity.this.startActivityForResult(i, NEW_SERIES);
                }
                else if (resultCode == RESULT_CONTINUE){
                    data.putExtra(Series.SCHEDULE, schedule.getId());
                    Series s = Series.build(data);
                    db.save(s);
                }
                break;
        }

        refreshList();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_series_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            setResult(RESULT_CANCELED);
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.delete_confirm)
                    .setTitle(R.string.delete_confirm_title)
                    .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.delete(schedule);
                            SeriesViewActivity.this.setResult(RESULT_OK);
                            SeriesViewActivity.this.finish();
                        }
                    }).show();
        }
        return true;
    }

    private void refreshList(){
        reloadSeriesFromDatabase();
        reloadSeriesView();
    }

    private void reloadSeriesFromDatabase(){
        series = db.allSeries(schedule);
    }

    private void reloadSeriesView(){
        seriesAdapter = new SeriesAdapter(this, series, new User<Integer>() {
            @Override
            public void use(Integer index) {
                SeriesViewActivity.this.onButtonExecuteClick(index);
            }
        });
        seriesList.setAdapter(seriesAdapter);
    }

    private void onButtonExecuteClick(int index){
        setActive();
        Series serie = series.get(index);
        if (serie.decrementTimes()) {
            series.remove(index);
        }
        reloadSeriesView();
    }

    private void setActive(){
        active = true;
        fab.setImageResource(android.R.drawable.ic_menu_revert);
        fab.setRotation(0);
    }

    private void setInactive(){
        active = false;
        fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        fab.setRotation(45);
    }

    private boolean isActive(){
        return active;
    }
}
