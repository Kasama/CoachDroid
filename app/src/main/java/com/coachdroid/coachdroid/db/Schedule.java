package com.coachdroid.coachdroid.db;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class Schedule extends DBObject {

    public static final String ID = "id";
    public static final String NAME = "name";

    private static final String[] columns = {ID, NAME};
    private static final String[] column_types = {tID, tTEXT};

    public Schedule(boolean fake){}

    public Schedule(){
        super();
    }

    public Schedule(String name) {
        this();
        setName(name);
    }

    public Schedule(int id, String name) {
        this(name);
        setId(id);
    }

    static Schedule build(Cursor c) {
        Schedule ret = new Schedule();
        ret.setId(c.getInt(c.getColumnIndex(ID)));
        ret.setName(c.getString(c.getColumnIndex(NAME)));
        return ret;
    }

    public static Schedule build(Intent data) {
        Schedule ret = new Schedule();
        Bundle info = data.getExtras();
        if (info.getInt(ID) != 0)
            ret.setId(info.getInt(ID));
        ret.setName(info.getString(NAME));
        return ret;
    }

    void setId(int id){
        values.put(ID, id);
    }

    Integer getId(){
        return values.getAsInteger(ID);
    }

    void setName(String name){ values.put(NAME, name); }

    String getName(){
        return values.getAsString(NAME);
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
    boolean save(SQLiteDatabase db) {
        if (getId() == null) setId(nextID(db));
        return super.save(db);
    }

    @Override
    public String toString() {
        return "DEBUG: " + this.getName();
    }
}