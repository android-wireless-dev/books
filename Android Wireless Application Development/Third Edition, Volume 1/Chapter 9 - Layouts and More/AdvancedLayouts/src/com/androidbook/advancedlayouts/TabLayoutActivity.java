package com.androidbook.advancedlayouts;

import java.util.Date;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class TabLayoutActivity extends TabActivity implements android.widget.TabHost.TabContentFactory {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Tab Layout");

        TabHost tabHost = getTabHost();

        LayoutInflater.from(this).inflate(R.layout.example_layout, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Grid").setContent(new Intent(this, GridLayoutActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("List").setContent(new Intent(this, MyListActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Basic").setContent(R.id.two_texts));

        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Fact'ry").setContent(this));
    }

    public View createTabContent(String tag) {
        if (tag.compareTo("tab4") == 0) {
            TextView tv = new TextView(this);
            Date now = new Date();
            tv.setText("I'm from a factory. Created: " + now.toString());
            tv.setTextSize((float) 24);
            return (tv);
        } else {
            return null;
        }
    }
}
