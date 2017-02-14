package com.coachdroid.coachdroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class Series extends DBObject {

    private static String[] columns = {"id", "name", "schedule", "description", "length", "times"};
    private static String[] column_types = {tID, tTEXT, tINT, tTEXT, tINT, tINT};

    Series(boolean fake){}

    Series(){
        super();
    }

    Series(String name, int schedule, String description, int length, int times){
        this();
        setName(name);
        setSchedule(schedule);
        setDescription(description);
        setLength(length);
        setTimes(times);
    }

    Series(int id, String name, int schedule, String description, int length, int times){
        this(name, schedule, description, length, times);
        setId(id);
    }

    @Override
    String[] getColumns() {
        return columns;
    }

    @Override
    String[] getColumnTypes() {
        return column_types;
    }

    @Override
    Series makeFromCursor(Cursor c) {
        Series ret = new Series();
        ret.setId(c.getInt(c.getColumnIndex(columns[0])));
        ret.setName(c.getString(c.getColumnIndex(columns[1])));
        ret.setSchedule(c.getInt(c.getColumnIndex(columns[2])));
        ret.setDescription(c.getString(c.getColumnIndex(columns[3])));
        ret.setLength(c.getInt(c.getColumnIndex(columns[4])));
        ret.setTimes(c.getInt(c.getColumnIndex(columns[5])));
        return ret;
    }

    @Override
    boolean save(SQLiteDatabase db) {
        if (getId() == null) setId(nextID(db));
        return super.save(db);
    }

    public Integer getId() {
        return values.getAsInteger(columns[0]);
    }

    public void setId(int id) {
        values.put(columns[0], id);
    }

    public String getName() {
        return values.getAsString(columns[1]);
    }

    public void setName(String name) {
        values.put(columns[1], name);
    }

    public Integer getSchedule() {
        return values.getAsInteger(columns[2]);
    }

    public void setSchedule(int schedule) {
        values.put(columns[2], schedule);
    }

    public String getDescription() {
        return values.getAsString(columns[3]);
    }

    public void setDescription(String description) {
        values.put(columns[3], description);
    }

    public Integer getLength() {
        return values.getAsInteger(columns[4]);
    }

    public void setLength(int length) {
        values.put(columns[4], length);
    }

    public Integer getTimes() {
        return values.getAsInteger(columns[5]);
    }

    public void setTimes(int times) {
        values.put(columns[5], times);
    }

}
