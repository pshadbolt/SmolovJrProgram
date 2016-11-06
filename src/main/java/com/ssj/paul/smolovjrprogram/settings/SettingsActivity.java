package com.ssj.paul.smolovjrprogram.settings;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.ssj.paul.smolovjrprogram.R;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NumberPicker.OnValueChangeListener {

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public static String pref_name = "settings_pref_v1";
    public static String pref_one_rep_max = "one_rep_max";
    public static String pref_increment = "increment";
    public static String pref_units = "units";
    public static String pref_rest = "rest";

    int one_rep_max_value;
    int increment_value;
    int rest_value;

    TextView tv_one_rep_max = null;
    TextView tv_increment = null;
    String units = null;
    TextView tv_rest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tv_one_rep_max = (TextView) findViewById(R.id.one_rep_max_value);
        tv_increment = (TextView) findViewById(R.id.increment_value);
        tv_rest = (TextView) findViewById(R.id.rest_value);
        Spinner sp_units = (Spinner) findViewById(R.id.units_value);

        // Set Spinner values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_units.setAdapter(adapter);

        // Restore the preference values
        SharedPreferences settings = getSharedPreferences(pref_name, 0);
        one_rep_max_value = settings.getInt(pref_one_rep_max, 300);
        tv_one_rep_max.setText(Integer.toString(one_rep_max_value));

        increment_value = settings.getInt(pref_increment, 5);
        tv_increment.setText(Integer.toString(increment_value));

        rest_value = settings.getInt(pref_rest, 2);
        tv_rest.setText(Integer.toString(rest_value));

        units = settings.getString(pref_units, "lbs");
        sp_units.setSelection(adapter.getPosition(units));

        // Add listeners
        sp_units.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    // Spinner Selector
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        units = (String) parent.getItemAtPosition(pos);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    // Update 1RM
    public void updateMax(View v) {
        final Dialog d = new Dialog(SettingsActivity.this);
        d.setTitle("One Rep Max");
        d.setContentView(R.layout.prompt_number);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(1000);
        np.setMinValue(0);
        np.setValue(one_rep_max_value);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one_rep_max_value = np.getValue();
                tv_one_rep_max.setText(String.valueOf(one_rep_max_value));
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    public void updateIncrement(View v) {
        final Dialog d = new Dialog(SettingsActivity.this);
        d.setTitle("Increment Value");
        d.setContentView(R.layout.prompt_number);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(100);
        np.setMinValue(0);
        np.setValue(increment_value);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment_value = np.getValue();
                tv_increment.setText(String.valueOf(increment_value));
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    public void updateRest(View v) {
        final Dialog d = new Dialog(SettingsActivity.this);
        d.setTitle("Rest Time");
        d.setContentView(R.layout.prompt_number);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(5);
        np.setMinValue(1);
        np.setValue(rest_value);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rest_value = np.getValue();
                tv_rest.setText(String.valueOf(rest_value));
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    /**
     *
     */
    public void savePreferences() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(pref_name, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(pref_one_rep_max, one_rep_max_value);
        editor.putInt(pref_increment, increment_value);
        editor.putInt(pref_rest, rest_value);
        editor.putString(pref_units, units);

        // Commit the edits!
        editor.commit();
    }
}
