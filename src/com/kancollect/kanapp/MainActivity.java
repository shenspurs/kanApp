package com.kancollect.kanapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.kancollect.kanapp");
        Cursor curor = getContentResolver().query(uri, null, null, null, null);
        String[] files;
        try {
            files = getAssets().list("database");
            Log.v("spz", "file = " + Arrays.toString(files));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        curor.moveToFirst();
        while (curor.moveToNext()) {
            int count = curor.getColumnCount();
            String url = curor.getString(1);
            Log.v("spz", "file = " + url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
