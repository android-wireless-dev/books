package com.androidbook.samelayout;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProgrammaticLayoutActivity extends MenuActivity {
    /** Called when the activity is first created. */
    @Override   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView text1 = new TextView(this);
        text1.setText("Hi there!");

        TextView text2 = new TextView(this);
        text2.setText("I'm second. I need to wrap.");
        text2.setTextSize((float) 60);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(text1);
        ll.addView(text2);

        setContentView(ll);
    }

}