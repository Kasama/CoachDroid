package com.coachdroid.coachdroid;

import android.database.Cursor;

public interface CursorUser<T> {
    T use(T list, Cursor c);
}
