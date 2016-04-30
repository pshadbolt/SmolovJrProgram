package com.ssj.paul.smolovjrprogram.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by PAUL on 5/1/2015.
 */
public class ProgramOpenHelper extends SQLiteOpenHelper {

    public static final String PROGRAM_TABLE_NAME = "program";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_DATE = "date";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "program.db";
    private static final String PROGRAM_TABLE_CREATE = "CREATE TABLE " + PROGRAM_TABLE_NAME
            + " (" +COLUMN_ID + " INTEGER PRIMARY KEY, "
            +COLUMN_SETS + " INTEGER, "
            +COLUMN_REPS + " INTEGER, "
            +COLUMN_WEIGHT + " INTEGER, "
            +COLUMN_DATE + " DATE "
            +");";
    private static final String PROGRAM_TABLE_DROP = "DROP TABLE IF EXISTS " + PROGRAM_TABLE_NAME;

    ProgramOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROGRAM_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ProgramOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL(PROGRAM_TABLE_DROP);
        onCreate(db);
    }

    public void recreate(SQLiteDatabase db){
        db.execSQL(PROGRAM_TABLE_DROP);
        db.execSQL(PROGRAM_TABLE_CREATE);
    }
}
