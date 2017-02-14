package com.coachdroid.coachdroid;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

abstract class DBObject {
    protected ContentValues values;

    // Supported types for SQLite
    protected static String tID = "INTEGER PRIMARY KEY";
    protected static String tINT = "INTEGER";
    protected static String tNUM = "NUMERIC";
    protected static String tReal = "REAL";
    protected static String tTEXT = "TEXT";
    protected static String tBlob = "BLOB";

    abstract String[] getColumns();
    abstract String[] getColumnTypes();
    abstract DBObject makeFromCursor(Cursor c);

    protected DBObject(){
        values = new ContentValues();
        for (String col : getColumns()){
            values.putNull(col);
        }
    }

    protected DBObject(ContentValues values){
        this.values = values;
    }

    String getCreationSQL(){
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(getTableName());
        builder.append("(");
        String[] cols = getColumns();
        String[] types = getColumnTypes();
        for (int i = 0; i < getColumnCount();) {
            builder.append(cols[i]);
            builder.append(" ");
            builder.append(types[i]);
            String finalizer = (++i == getColumnCount()) ? " " : ", ";
            builder.append(finalizer);
        }
        builder.append(");");
        return builder.toString();
    }

    private String getDropSQL() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    String getTableName() {
        return this.getClass().getSimpleName().toUpperCase();
    }

    int getColumnCount(){
        return getColumns().length;
    }

    void createTable(SQLiteDatabase db){
        db.execSQL(getCreationSQL());
    }

    void dropTable(SQLiteDatabase db){
        db.execSQL(getDropSQL());
    }

    void reCreateTable(SQLiteDatabase db){
        dropTable(db);
        createTable(db);
    }

    String pkColumn(){
        int i;
        String[] colTypes = getColumnTypes();
        for (i = 0; i < getColumnCount(); i++){
            if (colTypes[i].equals(tID)) break;
        }
        return getColumns()[i];
    }

    int nextID(SQLiteDatabase db){
        int max;
        String sql = "SELECT max(" + pkColumn() + ") FROM " + getTableName() + ";";
        Cursor c = db.rawQuery(sql, null);
        if (!c.moveToFirst() || c.isNull(0))
            max = 1;
        else
            max = c.getInt(0) + 1;
        c.close();
        return max;
    }

    boolean exists(SQLiteDatabase db, int id) {
        String pk = pkColumn();
        String sql = "Select " + pk + " FROM " + getTableName() + " WHERE " + pk + " = " + id + ";";
        Cursor c = db.rawQuery(sql, null);
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    boolean save(SQLiteDatabase db){
        long res = db.
                insertWithOnConflict(getTableName(), null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return (res != -1);
    }

    List<DBObject> selectAll(SQLiteDatabase db){
        List<DBObject> ret = new ArrayList<>();

        String sql = "SELECT * FROM " + getTableName();
        Cursor c = db.rawQuery(sql, null);

        if(c.moveToFirst()){
            do {
                ret.add(makeFromCursor(c));
            } while (c.moveToNext());
        }

        c.close();
        return ret;

    }

}
