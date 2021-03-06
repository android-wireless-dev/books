package com.androidbook.advancedlayouts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListAdapterSampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActionBar().setTitle("List Adapter");

        Cursor names = managedQuery(Contacts.Phones.CONTENT_URI, null, null, null, null);

        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.scratch_layout, names, new String[] {
                Contacts.Phones.NAME, Contacts.Phones.NUMBER }, new int[] { R.id.scratch_text1, R.id.scratch_text2 });

        setContentView(R.layout.scratch_list);

        int view_id = R.id.scratch_adapter_view;

        ListView av = (ListView) findViewById(view_id);
        av.setAdapter(adapter);
        av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListAdapterSampleActivity.this, "Clicked _id=" + id, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
