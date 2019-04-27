package com.androidbook.simplecontentprovider;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;

public class SimpleBookmarks extends Activity {
    private static final String DEBUG_TAG = "SimpleBookmarks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seelogcat);
        try {
            String[] requestedColumns = {
                Browser.BookmarkColumns.TITLE,
                Browser.BookmarkColumns.VISITS,
                Browser.BookmarkColumns.BOOKMARK };
            Cursor faves =
                managedQuery(Browser.BOOKMARKS_URI, requestedColumns,
                    Browser.BookmarkColumns.BOOKMARK
                        + "=1", null, Browser.BookmarkColumns.VISITS
                        + " DESC limit 5");
            Log.d(DEBUG_TAG, "Bookmarks count: " + faves.getCount());
            int titleIdx = faves.getColumnIndex(Browser.BookmarkColumns.TITLE);
            int visitsIdx = faves.getColumnIndex(Browser.BookmarkColumns.VISITS);
            int bmIdx = faves.getColumnIndex(Browser.BookmarkColumns.BOOKMARK);
            faves.moveToFirst();
            while (!faves.isAfterLast()) {
                Log.d(DEBUG_TAG, faves.getString(titleIdx) + " visited "
                    + faves.getInt(visitsIdx) + " times : "
                    + (faves.getInt(bmIdx) != 0 ? "true" : "false"));
                faves.moveToNext();
            }
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Failed: ", e);
        }
    }
}
