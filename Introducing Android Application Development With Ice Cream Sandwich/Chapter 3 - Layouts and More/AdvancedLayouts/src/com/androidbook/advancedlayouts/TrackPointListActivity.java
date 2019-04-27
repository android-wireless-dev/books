package com.androidbook.advancedlayouts;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class TrackPointListActivity extends ListActivity {
    private static final String DEBUG_TAG = "TrackPointListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points_layout);
        try {
            Uri tpp = Uri
                .parse("content://com.mamlambo.gpx.TrackPointProvider/points");
            String[] viewColumns = {
                "timestamp", "latitude", "longitude",
                "elevation",
            };
            Cursor names = managedQuery(tpp, null, null,
                null, null);
            if (names != null) {
                startManagingCursor(names);
                ListAdapter adapter = new SimpleCursorAdapter(
                    this, R.layout.points_item, names,
                    viewColumns, new int[] {
                        R.id.timestamp, R.id.latitude,
                        R.id.longitude, R.id.elevation,
                });
                setListAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Failed to load provider for trackpoints");
        }
    }
}
