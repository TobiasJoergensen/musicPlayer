package com.example.ixdstudio.musicplayer;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class ASyncTime extends AsyncTask<TextView, Boolean, String> {

    TextView textView;
    Date currentTime = Calendar.getInstance().getTime();

    private Context ContextAsync;

    @Override
    protected String doInBackground(TextView... TextViews) {
        if (TextViews.length > 0) {
            textView = TextViews[0];
        }
        return null;
    }

    public void setTimer () {

    }

    protected void onPostExecute(String strFromDoInBg) {
        textView.setText(currentTime.toString());
    }
}
