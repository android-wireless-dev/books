package com.androidbook.simplescrolling;

import android.app.Activity;
import android.os.Bundle;

public class VerticalScrollActivity extends MenuActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verticalscroll);
    }
}