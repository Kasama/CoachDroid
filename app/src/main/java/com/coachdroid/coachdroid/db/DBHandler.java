package com.coachdroid.coachdroid.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coachdroid.coachdroid.User;

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

    public void delete(DBObject obj){
        SQLiteDatabase db = this.getWritableDatabase();
        if (obj instanceof Schedule){
            List<Series> series = allSeries((Schedule) obj);
            for (Series d : series) {
                delete(d);
            }
        }
        obj.delete(db);
    }

    public List<Schedule> allSchedules(){
        List<Schedule> ret = new ArrayList<>();

        query(new Schedule().getTableName(), null, null,
                c -> ret.add(Schedule.build(c))
        );

        return ret;
    }

    public List<Series> allSeries(Schedule schedule){
        List<Series> ret = new ArrayList<>();

        query(new Series().getTableName(), Series.scheduleCompare(schedule.getId()), null,
                c -> ret.add(Series.build(c))
        );

        return ret;
    }

    private void query(String table, String where, String[] args, User<Cursor> u){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(false, table, null, where, args, null, null, null, null);

        if (c.moveToFirst()){
            do {
                u.use(c);
            } while (c.moveToNext());
        }

        c.close();
    }

    public String testeDosRole(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<DBObject> schedules = new ArrayList<>();
        String sql = "SELECT * FROM SCHEDULE;";
        StringBuilder builder = new StringBuilder();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                builder.append("Schedules Got: ");
                builder.append(c.getInt(0));
                builder.append(" - name: ");
                builder.append(c.getString(1));
                builder.append("\n");
            } while (c.moveToNext());
        }

        c.close();

        sql = "SELECT * FROM SERIES;";
        c = db.rawQuery(sql, null);
        if(c.moveToFirst()) {
            do {
                builder.append("Series Got: ");
                builder.append(c.getInt(0));
                builder.append(" - name: ");
                builder.append(c.getString(1));
                builder.append("\n");
            } while (c.moveToNext());
        }

        c.close();
        return builder.toString();

    }

}
