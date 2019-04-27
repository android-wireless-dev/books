package com.androidbook.SimpleFragDialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SimpleFragDialogActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Handle Alert Dialog Button
		Button launchAlertDialog = (Button) findViewById(R.id.Button_AlertDialog);
		launchAlertDialog.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialogFragment("Fragment Instance One");
			}
		});

		// Handle Alert Dialog 2 Button
		Button launchAlertDialog2 = (Button) findViewById(R.id.Button_AlertDialog2);
		launchAlertDialog2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialogFragment("Fragment Instance Two");
			}
		});
	}

	void showDialogFragment(String strFragmentNumber) {
		DialogFragment newFragment = MyAlertDialogFragment.newInstance(strFragmentNumber);
		newFragment.show(getFragmentManager(), strFragmentNumber);
	}

	public void doPositiveClick(String strFragmentNumber) {
		Toast.makeText(getApplicationContext(),
				"Clicked OK! (" + strFragmentNumber + ")", Toast.LENGTH_SHORT).show();
	}

}
