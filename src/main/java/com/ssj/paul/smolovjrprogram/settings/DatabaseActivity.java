package com.ssj.paul.smolovjrprogram.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ssj.paul.smolovjrprogram.R;
import com.ssj.paul.smolovjrprogram.db.WorkoutDataSource;
import com.ssj.paul.smolovjrprogram.ui.MainActivity;

/**
 * TODO: Archive database (allow reload)
 */
public class DatabaseActivity extends AppCompatActivity {

    private WorkoutDataSource datasource;
    private TextView databaseTotalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // Create the database connection
        datasource = new WorkoutDataSource(this);
        datasource.open();

        databaseTotalText = (TextView) findViewById(R.id.databaseTotal);
        setTotal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
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
            delete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTotal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTotal();
    }

    public void setTotal() {
        databaseTotalText.setText("Number of saved workouts: " + datasource.getAllWorkouts().size());
    }

    public void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void delete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Database?")
                .setMessage("All data will be lost")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        datasource.recreate();
                        launchMain();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
    }
}
