package com.coachdroid.coachdroid;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBObjectTest {
    @Test
    public void getCreationSQL() throws Exception {

        Series series = new Series(false);
        String expectedSeriesSQL = "CREATE TABLE SERIES(id INTEGER PRIMARY KEY, name TEXT, schedule INTEGER, description TEXT, length INTEGER, times INTEGER );";
        String actualSeriesSQL = series.getCreationSQL();
        assertEquals(expectedSeriesSQL, actualSeriesSQL);

        Schedule schedule = new Schedule(false);
        String expectedScheduleSQL = "CREATE TABLE SCHEDULE(id INTEGER PRIMARY KEY, name TEXT );";
        String actualScheduleSQL = schedule.getCreationSQL();
        assertEquals(expectedScheduleSQL, actualScheduleSQL);

    }

    @Test
    public void getTableName() throws Exception {

        Series series = new Series(false);
        String expected = "SERIES";
        String actual = series.getTableName();
        assertEquals(expected, actual);

        Schedule schedule = new Schedule(false);
        expected = "SCHEDULE";
        actual = schedule.getTableName();
        assertEquals(expected, actual);

    }

}