package com.example.licht.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseConnector {
    private SQLiteDatabase database;
    private DatabaseHelper databaseOpenHelper;

    private static final String TABLE = "Notes";

    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseHelper(context);
    }

    public void addRecord(String record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("record", record);
        if (!database.isOpen())
            this.open();
        database.insert(TABLE, null, contentValues);
    }

    public ArrayList<String> getAllRecords() {
        ArrayList<String> result = new ArrayList<>();

        if (!database.isOpen())
            this.open();

        Cursor resCursor = database.query(TABLE, new String[]{"record"},
                null, null, null, null, null);

        resCursor.moveToFirst();

        if (!(resCursor.getCount() == 0)) {
            do
                result.add(resCursor.getString(0));
            while (resCursor.moveToNext());
        }

        resCursor.close();
        this.close();
        return result;
    }

    private void open() throws SQLException
    {
        database = databaseOpenHelper.getWritableDatabase();
    }

    private void close() {
        if (database != null)
            database.close();
    }


}
