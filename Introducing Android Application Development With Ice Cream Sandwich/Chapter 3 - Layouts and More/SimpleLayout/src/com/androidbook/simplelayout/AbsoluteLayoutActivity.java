package com.androidbook.simplelayout;

import android.os.Bundle;

public class AbsoluteLayoutActivity extends MenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.absolute_layout);
		this.getActionBar().setTitle("Absolute Layout");
	}

}
