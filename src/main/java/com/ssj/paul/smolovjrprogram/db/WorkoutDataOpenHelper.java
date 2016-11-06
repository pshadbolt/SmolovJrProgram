package com.ssj.paul.smolovjrprogram.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by PAUL on 5/1/2015.
 */
public class WorkoutDataOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME_PROGRAM = "program";
    public static final String TABLE_NAME_SET = "paul";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_DATE = "date";

    public static final String COLUMN_ID_WORKOUT = "_idWorkout";
    public static final String COLUMN_COMPLETE = "complete";

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "program.db";

    private static final String CREATE_TABLE_PROGRAM = "CREATE TABLE " + TABLE_NAME_PROGRAM
            + " (" +COLUMN_ID + " INTEGER PRIMARY KEY, "
            +COLUMN_SETS + " INTEGER, "
            +COLUMN_REPS + " INTEGER, "
            +COLUMN_WEIGHT + " INTEGER, "
            +COLUMN_DATE + " DATE "
            +");";
    private static final String DROP_TABLE_PROGRAM = "DROP TABLE IF EXISTS " + TABLE_NAME_PROGRAM;

    private static final String CREATE_TABLE_SET = "CREATE TABLE " + TABLE_NAME_SET +"("
            +COLUMN_ID + " INTEGER, "
            +COLUMN_REPS + " INTEGER, "
            +COLUMN_WEIGHT + " INTEGER, "
            +COLUMN_COMPLETE + " INTEGER, "
            + "FOREIGN KEY ("+COLUMN_ID+") REFERENCES " + TABLE_NAME_PROGRAM + "(" + COLUMN_ID + ")"
            +");";
    private static final String DROP_TABLE_SET = "DROP TABLE IF EXISTS " + TABLE_NAME_SET;

    WorkoutDataOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("DB",CREATE_TABLE_PROGRAM);
        Log.v("DB",CREATE_TABLE_SET);
        db.execSQL(CREATE_TABLE_PROGRAM);
        db.execSQL(CREATE_TABLE_SET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WorkoutDataOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        //db.execSQL(DROP_TABLE_SET);
        db.execSQL(DROP_TABLE_PROGRAM);
        onCreate(db);
    }

    public void recreate(SQLiteDatabase db){
        db.execSQL(DROP_TABLE_PROGRAM);
        db.execSQL(CREATE_TABLE_PROGRAM);
    }
}
