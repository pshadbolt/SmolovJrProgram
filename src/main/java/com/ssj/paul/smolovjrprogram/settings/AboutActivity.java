package com.ssj.paul.smolovjrprogram.settings;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ssj.paul.smolovjrprogram.R;

import java.io.BufferedInputStream;
import java.io.IOException;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setChangeLog();
    }

    private void setChangeLog(){
        BufferedInputStream stream = new BufferedInputStream(getResources().openRawResource(R.raw.changelog));

        byte[] contents = new byte[1024];
        int bytesRead = 0;
        String strFileContents="";
        try {
            while ((bytesRead = stream.read(contents)) != -1) {
                strFileContents += new String(contents, 0, bytesRead);
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.changelog)).setText(strFileContents);
    }

}
