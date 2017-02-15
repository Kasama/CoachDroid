package com.coachdroid.coachdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.coachdroid.coachdroid.db.DBHandler;
import com.coachdroid.coachdroid.db.Schedule;

import java.util.List;

public class ScheduleViewActivity extends AppCompatActivity {

    public static final int CREATE_SCHEDULE = 0;
    public static final int CREATE_SERIES = 1;
    private static final int VIEW_SCHEDULE = 2;

    private List<Schedule> schedules;
    private ArrayAdapter<Schedule> schedulesView;
    private Toolbar toolbar;
    private ListView scheduleList;
    private DBHandler db;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        scheduleList = (ListView) findViewById(R.id.listSchedule);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        db = new DBHandler(this);
        refreshSchedules();

        scheduleList.setOnItemClickListener((parent, view, pos, id) -> {
            Schedule schedule = (Schedule) parent.getAdapter().getItem(pos);
            Intent intent = new Intent(this, SeriesViewActivity.class);
            intent.putExtra(Schedule.ID, schedule.getId());
            intent.putExtra(Schedule.NAME, schedule.getName());
            startActivityForResult(intent, VIEW_SCHEDULE);
        });

        fab.setOnClickListener(view -> {
            Intent i = new Intent(this, NewScheduleActivity.class);
            startActivityForResult(i, CREATE_SCHEDULE);
        }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case CREATE_SCHEDULE:
                Log.d("D/CoachDroid", "Came back from activity\nresult: " + resultCode + "\nok: " + RESULT_OK + "\ncancel: " + RESULT_CANCELED);
                if (resultCode == RESULT_OK){
                    Schedule newSchedule = Schedule.build(data);
                    db.save(newSchedule);
                }
                break;
        }

        refreshSchedules();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_schedule_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshSchedules(){
        schedules = db.allSchedules();
        schedulesView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, schedules);
        scheduleList.setAdapter(schedulesView);
        Log.d("D/CoachDroid", db.testeDosRole());
    }
}
