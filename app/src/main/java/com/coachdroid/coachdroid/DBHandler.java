package com.coachdroid.coachdroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "coachDroid";

    DBHandler(Context context) {
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
        List<DBObject> ret = new Schedule().selectAll(db);
        // Unchecked Warning!
        // This wont cause problems because Schedule#selectAll only creates Schedules
        return (List<Schedule>) (List<?>) ret;
    }

    public List<Series> allSeries(Series s){
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBObject> ret = s.selectAll(db);
        // Unchecked Warning!
        // This wont cause problems because Series#selectAll only creates Series
        return (List<Series>) (List<?>) ret;
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
