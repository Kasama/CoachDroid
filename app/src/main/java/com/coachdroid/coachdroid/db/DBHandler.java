package com.coachdroid.coachdroid.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "coachDroid";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new Schedule(false).createTable(db);
        new Series(false).createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new Schedule(false).reCreateTable(db);
        new Series(false).reCreateTable(db);
    }

    public void save(DBObject obj){
        SQLiteDatabase db = this.getWritableDatabase();
        obj.save(db);
    }

    public List<Schedule> allSchedules(){
        SQLiteDatabase db = this.getReadableDatabase();
        return DBObjectFactory.selectSchedules(db);
    }

    public List<Series> allSeries(Schedule schedule){
        SQLiteDatabase db = this.getReadableDatabase();
        return DBObjectFactory.selectSeries(db, schedule.getId());
    }

    public String testeDosRole(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBObject> schedules = new ArrayList<>();
        String sql = "SELECT * FROM SCHEDULE;";
        StringBuilder builder = new StringBuilder();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        do {
            builder.append("Got: ");
            builder.append(c.getInt(0));
            builder.append(" - name: ");
            builder.append(c.getString(1));
            builder.append("\n");
        } while (c.moveToNext());

        c.close();
        return builder.toString();

    }

}
