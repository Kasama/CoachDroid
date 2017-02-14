package com.coachdroid.coachdroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class Schedule extends DBObject {

    private static final String[] columns = {"id", "name"};
    private static final String[] column_types = {tID, tTEXT};

    Schedule(boolean fake){}

    Schedule(){
        super();
    }

    Schedule(String name) {
        this();
        setName(name);
    }

    Schedule(int id, String name) {
        this(name);
        setId(id);
    }

    void setId(int id){
        values.put(columns[0], id);
    }

    Integer getId(){
        return values.getAsInteger(columns[0]);
    }

    void setName(String name){
        values.put(columns[1], name);
    }

    String getName(){
        return values.getAsString(columns[1]);
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
    Schedule makeFromCursor(Cursor c) {
        Schedule ret = new Schedule();
        ret.setId(c.getInt(c.getColumnIndex(columns[0])));
        ret.setName(c.getString(c.getColumnIndex(columns[1])));
        return ret;
    }

    @Override
    boolean save(SQLiteDatabase db) {
        if (getId() == null) setId(nextID(db));
        return super.save(db);
    }

}