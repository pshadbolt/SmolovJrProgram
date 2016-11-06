package com.ssj.paul.smolovjrprogram.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ssj.paul.smolovjrprogram.R;
import com.ssj.paul.smolovjrprogram.db.Workout;
import com.ssj.paul.smolovjrprogram.db.WorkoutDataSource;
import com.ssj.paul.smolovjrprogram.settings.AboutActivity;
import com.ssj.paul.smolovjrprogram.settings.DatabaseActivity;
import com.ssj.paul.smolovjrprogram.settings.SettingsActivity;

public class WorkoutActivity extends AppCompatActivity {

    //
    private WorkoutDataSource datasource;
    Workout workout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // Create the database connection
        datasource = new WorkoutDataSource(this);
        datasource.open();

        // Set the back button to parent
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int index = getIntent().getExtras().getInt("index");

        workout = datasource.getWorkout(index);
        ((TextView) findViewById(R.id.workoutLabel)).setText(workout.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Workout?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            datasource.deleteWorkout(workout);
                            finish();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Do nothing.
                }
            }).show();
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_database) {
            Intent intent = new Intent(this, DatabaseActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        datasource.close();
        super.onRestart();
    }
}
