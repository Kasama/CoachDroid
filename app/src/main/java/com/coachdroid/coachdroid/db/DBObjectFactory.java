package com.coachdroid.coachdroid.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coachdroid.coachdroid.User;

import java.util.ArrayList;
import java.util.List;

class DBObjectFactory {

    static List<Schedule> selectSchedules(SQLiteDatabase db){
        List<Schedule> ret = new ArrayList<>();

        dbWork(db, new Schedule().getTableName(), null, null,
                c -> ret.add(Schedule.build(c))
        );

        return ret;
    }

    static List<Series> selectSeries(SQLiteDatabase db, int schedule){
        List<Series> ret = new ArrayList<>();
        dbWork(db, new Series().getTableName(), Series.scheduleCompare(schedule), null,
                c -> ret.add(Series.makeFromCursor(c))
        );
        return ret;
    }

    private static void dbWork(SQLiteDatabase db, String table, String where, String[] args, User<Cursor> u){
        Cursor c = db.query(false, table, null, where, args, null, null, null, null);

        if (c.moveToFirst()){
            do {
                u.use(c);
            } while (c.moveToNext());
        }

        c.close();

    }

}
