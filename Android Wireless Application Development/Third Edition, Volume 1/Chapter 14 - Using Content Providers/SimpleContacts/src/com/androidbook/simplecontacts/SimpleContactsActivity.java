package com.androidbook.simplecontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

public class SimpleContactsActivity extends Activity {
	
	public static final String DEBUG_TAG = "SimpleContactsActivity";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    
    public void listContactsContract(View v)
    {
    	String[] requestedColumns = {
    		    ContactsContract.Contacts.DISPLAY_NAME,
    		    ContactsContract.CommonDataKinds.Phone.NUMBER,
    		};

    		Cursor contacts = managedQuery(
    			ContactsContract.Data.CONTENT_URI,
    		    requestedColumns,
    		    null,
    		    null, "display_name desc limit 1");

    		int recordCount = contacts.getCount();
    		Log.d(DEBUG_TAG, "Contacts count: "
    		    + recordCount);

    		if(recordCount > 0)
    		{
	    		int nameIdx = contacts
	    		    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
	    		int phoneIdx = contacts
	    		    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
	
	    		contacts.moveToFirst();
	    		Log.d(DEBUG_TAG, "Name: " + contacts.getString(nameIdx));
	    		Log.d(DEBUG_TAG, "Phone: " + contacts.getString(phoneIdx));
    		}

    }

    public void listContactsLegacy(View v)
    {
    	String[] requestedColumns = {
    		    Contacts.Phones.NAME,
    		    Contacts.Phones.NUMBER,
    		};

    		Cursor contacts = managedQuery(
    		    Contacts.Phones.CONTENT_URI,
    		    requestedColumns,
    		    null,
    		    null, "name desc limit 1");

    		int recordCount = contacts.getCount();
    		Log.d(DEBUG_TAG, "Contacts count: "
    		    + recordCount);

    		if(recordCount > 0)
    		{
    			// Grab the first record
	    		int nameIdx = contacts
	    		    .getColumnIndex(Contacts.Phones.NAME);
	    		int phoneIdx = contacts
	    		    .getColumnIndex(Contacts.Phones.NUMBER);
	
	    		contacts.moveToFirst();
	    		Log.d(DEBUG_TAG, "Name: " + contacts.getString(nameIdx));
	    		Log.d(DEBUG_TAG, "Phone: " + contacts.getString(phoneIdx));
    		}
    }
}