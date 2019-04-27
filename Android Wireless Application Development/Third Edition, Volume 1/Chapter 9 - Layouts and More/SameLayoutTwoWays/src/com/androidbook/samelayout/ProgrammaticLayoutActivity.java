package com.androidbook.samelayout;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProgrammaticLayoutActivity extends MenuActivity {
    /** Called when the activity is first created. */
    @Override   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView text1 = new TextView(this);
        text1.setText(R.string.string1);

        TextView text2 = new TextView(this);
        text2.setText(R.string.string2);
        text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(text1);
        ll.addView(text2);

        setContentView(ll);
    }

}