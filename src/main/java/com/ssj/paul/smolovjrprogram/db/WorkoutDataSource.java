package com.ssj.paul.smolovjrprogram.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PAUL on 5/1/2015.
 */
public class WorkoutDataSource {

    // Database fields
    private SQLiteDatabase database;
    private ProgramOpenHelper dbHelper;
    private String[] allColumns = {ProgramOpenHelper.COLUMN_ID, ProgramOpenHelper.COLUMN_SETS, ProgramOpenHelper.COLUMN_REPS, ProgramOpenHelper.COLUMN_WEIGHT, ProgramOpenHelper.COLUMN_DATE};

    public WorkoutDataSource(Context context) {
        dbHelper = new ProgramOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void recreate() throws SQLException {
        dbHelper.recreate(database);
    }

    public void close() {
        dbHelper.close();
    }

    public Workout createWorkout(int id, int sets, int reps, int weight, String date) {
        ContentValues values = new ContentValues();
        values.put(ProgramOpenHelper.COLUMN_ID, id);
        values.put(ProgramOpenHelper.COLUMN_SETS, sets);
        values.put(ProgramOpenHelper.COLUMN_REPS, reps);
        values.put(ProgramOpenHelper.COLUMN_WEIGHT, weight);
        values.put(ProgramOpenHelper.COLUMN_DATE, date);

        long insertId = database.insert(ProgramOpenHelper.PROGRAM_TABLE_NAME, null, values);
        Cursor cursor = database.query(ProgramOpenHelper.PROGRAM_TABLE_NAME,
                allColumns, ProgramOpenHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Workout newWorkout = cursorToWorkout(cursor);
        cursor.close();
        return newWorkout;
    }

    public void deleteWorkout(Workout workout) {
        long id = workout.getId();

        database.delete(ProgramOpenHelper.PROGRAM_TABLE_NAME, ProgramOpenHelper.COLUMN_ID
                + " = " + id, null);
    }

    public Workout getWorkout(int index) {
        Workout workout = null;
        Cursor cursor = database.query(ProgramOpenHelper.PROGRAM_TABLE_NAME, allColumns, ProgramOpenHelper.COLUMN_ID + " = " + index, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            workout = cursorToWorkout(cursor);
            cursor.close();
        }
        return workout;
    }

    /**
     * @return
     */
    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<Workout>();

        Cursor cursor = database.query(ProgramOpenHelper.PROGRAM_TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            workouts.add(workout);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return workouts;
    }

    /**
     * Return the index of the last entry found in the database
     */
    public int getLastIndex() {
        Cursor cursor = database.query(ProgramOpenHelper.PROGRAM_TABLE_NAME,
                allColumns, null, null, null, null, null);

        if (cursor.moveToLast())
            return cursor.getInt(0);
        else
            return -1;
    }

    /**
     * Return the maximun index for a single program
     */
    public int getMaxIndex() {
       return 11;
    }

    /**
     * @param cursor
     * @return
     */
    private Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setId(cursor.getInt(0));
        workout.setSets(cursor.getInt(1));
        workout.setReps(cursor.getInt(2));
        workout.setWeight(cursor.getInt(3));
        workout.setDate(cursor.getString(4));
        return workout;
    }
}
