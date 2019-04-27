package com.androidbook.simplelayout;

import android.os.Bundle;

public class SimpleLayoutActivity extends MenuActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.getActionBar().setTitle("Simple Layout");
    	
    }
}