package com.ssj.paul.smolovjrprogram.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ssj.paul.smolovjrprogram.R;
import com.ssj.paul.smolovjrprogram.db.SmolovUtil;
import com.ssj.paul.smolovjrprogram.db.WorkoutDataSource;
import com.ssj.paul.smolovjrprogram.settings.AboutActivity;
import com.ssj.paul.smolovjrprogram.settings.DatabaseActivity;
import com.ssj.paul.smolovjrprogram.settings.SettingsActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO: Lifts hit or missed
 */
public class TrainingActivity extends ActionBarActivity {

    private WorkoutDataSource datasource;
    private SharedPreferences settings;

    private MediaPlayer mp;

    private int index;
    private int weight;
    private int sets;
    private int setsRemaining;
    private int reps;
    private int rest = -1;
    private String units;

    private TextView countdownText;
    private TextView setsText;
    private SetCountDown aCounter;

    private boolean saved=false;

    public class SetCountDown extends CountDownTimer {

        DecimalFormat df = new DecimalFormat("#.0");

        public SetCountDown(long startTime, long interval) {
            super(startTime, interval);
        }

        public void onTick(long millisUntilFinished) {
            countdownText.setText("Rest Time: " + df.format(millisUntilFinished / 1000.0)+" secs");
        }

        public void onFinish() {
            countdownText.setText("Lift!");
            mp.start();
            if (setsRemaining == 1) {
                setsRemaining--;
                completeTraining();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        // Create the database connection
        datasource = new WorkoutDataSource(this);
        datasource.open();

        // Get the settings preferences
        settings = getSharedPreferences(SettingsActivity.pref_name, 0);

        // Get the workout information
        index = getIntent().getExtras().getInt("index");
        ((TextView) findViewById(R.id.label)).setText("DAY " + (index + 1));

        sets = SmolovUtil.getSets(index);
        setsRemaining = sets;
        reps = SmolovUtil.getReps(index);

        setsText = (TextView) findViewById(R.id.sets);
        setsText.setText("Sets Remaining: " + setsRemaining);
        setTrainingDay();

        // Set the back button to parent
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the counter based on settings preference
        countdownText = (TextView) findViewById(R.id.countdown);
        setTimer();

        // Prepare the chime
        mp = MediaPlayer.create(getApplicationContext(), R.raw.chime);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTimer();
        setTrainingDay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            saveWorkout();
            finish();
            return true;
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

    public void setTimer() {
        int newRest = settings.getInt(SettingsActivity.pref_rest, 2);

        // Refresh the timer if the preference changed
        if (newRest != rest) {
            if (aCounter != null)
                aCounter.cancel();

            DecimalFormat df = new DecimalFormat("#.0");

            rest = newRest;
            countdownText.setText("Rest Time: " +  df.format(rest * 60)+" secs");
            aCounter = new SetCountDown((rest * 60 * 1000), 100);
        }
    }

    /**
     *
     */
    public void setTrainingDay() {

        // Set the title
        units = settings.getString(SettingsActivity.pref_units,"lbs");

        // Get the weight values
        int one_rep_max = settings.getInt(SettingsActivity.pref_one_rep_max, 0);
        int increment = settings.getInt(SettingsActivity.pref_increment, 0);
        weight = SmolovUtil.getWeight(index, one_rep_max, increment);

        String workout = sets + " X " + reps + " @ " + weight+" "+units;
        ((TextView) findViewById(R.id.training)).setText(workout);

        for(int i=1;i<=sets;i++) {
            int resId = getResources().getIdentifier("set" + i, "id", getPackageName());
            ((TextView) findViewById(resId)).setText("Set "+i+": \t\t" + reps + " reps at " + weight);
        }
    }

    /**
     *
     */
    public void start(View v) {

        setsRemaining--;

        int resId = getResources().getIdentifier("set" + (sets-setsRemaining), "id", getPackageName());
        TextView tv  = ((TextView) findViewById(resId));
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (setsRemaining == 0) {
            saveWorkout();
            completeTraining();
        } else {
            setsText.setText("Sets Remaining: " + setsRemaining);
            aCounter.start();
        }
    }

    /**
     *
     */
    public void saveWorkout() {
        if(!saved) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            datasource.createWorkout(index, sets, reps, weight, dateFormat.format(date));
            saved=true;
        }
    }

    /**
     *
     */
    public void completeTraining() {
        findViewById(R.id.hit).setVisibility(View.GONE);
        findViewById(R.id.miss).setVisibility(View.GONE);

        aCounter.cancel();
        countdownText.setVisibility(View.GONE);
        setsText.setText("Training Complete.");

        // Pause for 5 seconds before exit
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}
