package com.coachdroid.coachdroid.db;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class Series extends DBObject {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SCHEDULE = "schedule";
    public static final String DESCRIPTION = "description";
    public static final String LENGTH = "length";
    public static final String TIMES = "times";

    private static String[] columns = {ID, NAME, SCHEDULE, DESCRIPTION, LENGTH, TIMES};
    private static String[] column_types = {tID, tTEXT, tINT, tTEXT, tINT, tINT};

    public Series(boolean fake){}

    public Series(){
        super();
    }

    public Series(String name, int schedule, String description, int length, int times){
        this();
        setName(name);
        setSchedule(schedule);
        setDescription(description);
        setLength(length);
        setTimes(times);
    }

    public Series(int id, String name, int schedule, String description, int length, int times){
        this(name, schedule, description, length, times);
        setId(id);
    }

    static Series build(Cursor c) {
        Series ret = new Series();
        ret.setId(c.getInt(c.getColumnIndex(ID)));
        ret.setName(c.getString(c.getColumnIndex(NAME)));
        ret.setSchedule(c.getInt(c.getColumnIndex(SCHEDULE)));
        ret.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
        ret.setLength(c.getInt(c.getColumnIndex(LENGTH)));
        ret.setTimes(c.getInt(c.getColumnIndex(TIMES)));
        return ret;
    }

    public static Series build(Intent i) {
        Series ret = new Series();
        Bundle bundle = i.getExtras();
        ret.setId(bundle.getInt(ID));
        ret.setName(bundle.getString(NAME));
        ret.setDescription(bundle.getString(DESCRIPTION));
        ret.setSchedule(bundle.getInt(SCHEDULE));
        ret.setLength(bundle.getInt(LENGTH));
        ret.setTimes(bundle.getInt(TIMES));
        return ret;
    }

    static String scheduleCompare(int schedule) {
        return SCHEDULE + " = " + schedule;
    }

    public Integer getId() {
        return values.getAsInteger(ID);
    }

    public void setId(int id) {
        values.put(ID, id);
    }

    public String getName() {
        return values.getAsString(NAME);
    }

    public void setName(String name) {
        values.put(NAME, name);
    }

    public Integer getSchedule() {
        return values.getAsInteger(SCHEDULE);
    }

    public void setSchedule(int schedule) {
        values.put(SCHEDULE, schedule);
    }

    public String getDescription() {
        return values.getAsString(DESCRIPTION);
    }

    public void setDescription(String description) {
        values.put(DESCRIPTION, description);
    }

    public Integer getLength() {
        return values.getAsInteger(LENGTH);
    }

    public void setLength(int length) {
        values.put(LENGTH, length);
    }

    public Integer getTimes() {
        return values.getAsInteger(TIMES);
    }

    public void setTimes(int times) {
        values.put(TIMES, times);
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
    int getPK() {
        return getId();
    }

    @Override
    boolean save(SQLiteDatabase db) {
        if (getId() == null || getId() == 0) setId(nextID(db));
        return super.save(db);
    }

    @Override
    boolean delete(SQLiteDatabase db) {
        return getId() != null && super.delete(db);
    }

    public boolean decrementTimes() {
        setTimes(getTimes() - 1);
        return getTimes() == 0;
    }
}
