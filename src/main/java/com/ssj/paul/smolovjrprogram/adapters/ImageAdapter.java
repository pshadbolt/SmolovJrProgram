package com.ssj.paul.smolovjrprogram.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ssj.paul.smolovjrprogram.db.WorkoutDataSource;

/**
 * Created by PAUL on 5/3/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private WorkoutDataSource dataSource;

    // references to our images
    private String[] mThumbIds = {"DAY 01", "DAY 02", "DAY 03", "DAY 04", "DAY 05", "DAY 06", "DAY 07", "DAY 08", "DAY 09", "DAY 10", "DAY 11", "DAY 12"};

    public ImageAdapter(Context c, WorkoutDataSource dataSource) {
        this.dataSource = dataSource;
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView text_view;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            text_view = new TextView(mContext);
            text_view.setLayoutParams(new GridView.LayoutParams(150, 150));
            text_view.setPadding(8, 8, 8, 8);
        } else {
            text_view = (TextView) convertView;
        }

        if (dataSource.getWorkout(position) != null) {
            text_view.setTypeface(null, Typeface.BOLD);
            text_view.setBackgroundResource(mContext.getResources().getIdentifier("ic_action_accept" , "drawable", mContext.getPackageName()));
        }
        else {
            text_view.setTypeface(null, Typeface.NORMAL);
            text_view.setBackgroundResource(Color.TRANSPARENT);
        }

        text_view.setGravity(Gravity.CENTER);
        text_view.setText(mThumbIds[position]);
        return text_view;
    }
}
