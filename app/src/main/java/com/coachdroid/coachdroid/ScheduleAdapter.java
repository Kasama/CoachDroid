package com.coachdroid.coachdroid;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.coachdroid.coachdroid.db.DBHandler;
import com.coachdroid.coachdroid.db.Schedule;

import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {

    List<Schedule> schedules;
    Context context;

    public ScheduleAdapter(Context context, List<Schedule> schedules) {
        super(context, android.R.layout.simple_list_item_1, schedules);

        this.schedules = schedules;
        this.context = context;
    }

    public ScheduleAdapter updateScheduleList(DBHandler db){
        schedules = db.allSchedules();
        return this;
    }
}
