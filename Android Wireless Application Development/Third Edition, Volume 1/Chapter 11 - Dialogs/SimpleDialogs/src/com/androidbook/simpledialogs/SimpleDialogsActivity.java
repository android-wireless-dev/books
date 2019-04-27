package com.androidbook.simpledialogs;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SimpleDialogsActivity extends Activity {

    static final int BASIC_DIALOG_ID = 0;
    static final int ALERT_DIALOG_ID = 1;
    static final int CHARACTER_DIALOG_ID = 2;
    static final int DATE_DIALOG_ID = 3;
    static final int PROGRESS_DIALOG_ID = 4;
    static final int TIME_DIALOG_ID = 5;
    static final int CUSTOM_DIALOG_ID = 6;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
             
        
        // HANDLE BUTTON CLICKS
        
        // Handle Toast Button
        Button launchToast = (Button) findViewById(R.id.Button_Toast);
        launchToast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(SimpleDialogsActivity.this, "This is a toast of the toast broadcasting system. This is only a toast.", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Handle Basic Dialog Button
        Button launchBasicDialog = (Button) findViewById(R.id.Button_SimpleDialog);
        launchBasicDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	showDialog(BASIC_DIALOG_ID);
            }
        });
        
        // Handle Alert Dialog Button
        Button launchAlertDialog = (Button) findViewById(R.id.Button_AlertDialog);
        launchAlertDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	showDialog(ALERT_DIALOG_ID);
            }
        }); 
        // Handle Character Picker Button
        Button launchCharDialog = (Button) findViewById(R.id.Button_CharacterPickerDialog);
        launchCharDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	showDialog(CHARACTER_DIALOG_ID);
            }
        });
        // Handle Date Picker Dialog Button
        Button launchDateDialog = (Button) findViewById(R.id.Button_DatePickerDialog);
        launchDateDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	showDialog(DATE_DIALOG_ID);
            }
        });
        // Handle Progress Dialog Button
        Button launchProgressDialog = (Button) findViewById(R.id.Button_ProgressDialog);
        launchProgressDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	showDialog(PROGRESS_DIALOG_ID);
            }
        });
        // Handle Toast Button
        Button launchTimeDialog = (Button) findViewById(R.id.Button_TimePickerDialog);
        launchTimeDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	showDialog(TIME_DIALOG_ID);
            }
        });
        // Handle Custom Dialog Button
        Button launchCustomDialog = (Button) findViewById(R.id.Button_CustomDialog);
        launchCustomDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	showDialog(CUSTOM_DIALOG_ID);
            }
        });
        }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        
	        case BASIC_DIALOG_ID:
	            Dialog simpleDialog = new Dialog(this);
	            simpleDialog.setTitle("Basic Dialog");
	            return simpleDialog;
	        case ALERT_DIALOG_ID:
	            
	            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
	            alertDialog.setTitle("Alert Dialog");
    	        alertDialog.setMessage("You have been alerted.");
    	        alertDialog.setIcon(android.R.drawable.btn_star);
			    alertDialog.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(getApplicationContext(),
										"Clicked OK!", Toast.LENGTH_SHORT).show();
								return;
							}
						});
	            return alertDialog.create();

	        case CHARACTER_DIALOG_ID:
	            CharacterPickerDialog charDialog = new CharacterPickerDialog(this, new View(this), null, "AEIOU", true)
	            {            	
	            	public void onClick(View view) {
	            		Button letter = (Button)view;
	            		Toast.makeText(getApplicationContext(),
	            				"Clicked "+ letter.getText() +"!", Toast.LENGTH_SHORT).show();
	            		this.dismiss();
	            	}
	           };

	            
	            return charDialog;
	        case DATE_DIALOG_ID:
	            DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
	                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	                    Time dtOfInterest = new Time();
	                    dtOfInterest.set(dayOfMonth, monthOfYear, year);
	                    long dtDob = dtOfInterest.toMillis(true);
	                    CharSequence strDate = DateFormat.format("MMMM dd, yyyy", dtDob);
	                    Toast.makeText(SimpleDialogsActivity.this, strDate, Toast.LENGTH_SHORT).show();
	                }
	            }, 2011,1, 1);
	            
	            dateDialog.setTitle("Pick a date");
	            dateDialog.setMessage("Choose wisely");
	            
	            return dateDialog;
	        case PROGRESS_DIALOG_ID:
	            ProgressDialog progressDialog = new ProgressDialog(this);
	            // make it a spinner
	            progressDialog.setIndeterminate(true);
	            progressDialog.setTitle("Showing Progress");
	            progressDialog.setMessage("Houston, we're making progress!");
	            return progressDialog;
	        case TIME_DIALOG_ID: 
	            TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(SimpleDialogsActivity.this, "You picked "+hourOfDay+" hours and "+minute+" minutes." , Toast.LENGTH_SHORT).show();
                        
                    }
                }, 0, 0, true);
	            
	            timeDialog.setMessage("Take your time...");
	            return timeDialog;
	        case CUSTOM_DIALOG_ID:
	        	  LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	              final View layout = inflater.inflate(R.layout.custom_dialog, (ViewGroup) findViewById(R.id.root));
	              final EditText p1 = (EditText) layout.findViewById(R.id.EditText_Pwd1);
	              final EditText p2 = (EditText) layout.findViewById(R.id.EditText_Pwd2);
	              final TextView error = (TextView) layout.findViewById(R.id.TextView_PwdProblem);
	              p2.addTextChangedListener(new TextWatcher() {
	                  public void afterTextChanged(Editable s) {
	                      String strPass1 = p1.getText().toString();
	                      String strPass2 = p2.getText().toString();
	                      if (strPass1.equals(strPass2)) {
	                          error.setText(R.string.settings_pwd_equal);
	                      } else {
	                          error.setText(R.string.settings_pwd_not_equal);
	                      }
	                  }
	
	                  // ... other required overrides do nothing
	                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	                  }
	
	                  public void onTextChanged(CharSequence s, int start, int before, int count) {
	                  }
	              });
	              AlertDialog.Builder builder = new AlertDialog.Builder(this);
	              builder.setView(layout);
	              // Now configure the AlertDialog
	              builder.setTitle(R.string.password_title);
	              builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	                  public void onClick(DialogInterface dialog, int whichButton) {
	                      // We forcefully dismiss and remove the Dialog, so it
	                      // cannot be used again (no cached info)
	                      removeDialog(CUSTOM_DIALOG_ID);
	                  }
	              });
	              builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	                  public void onClick(DialogInterface dialog, int which) {
	                      String strPassword1 = p1.getText().toString();
	                      String strPassword2 = p2.getText().toString();
	                      if (strPassword1.equals(strPassword2)) {
	                    	  Toast.makeText(SimpleDialogsActivity.this, "Matching passwords="+strPassword2, Toast.LENGTH_SHORT).show();
	                      } 
	                      // We forcefully dismiss and remove the Dialog, so it
	                      // cannot be used again
	                      removeDialog(CUSTOM_DIALOG_ID);
	                  }
	              });
	              // Create the AlertDialog and return it
	              AlertDialog passwordDialog = builder.create();
	              return passwordDialog;
	          }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
        case DATE_DIALOG_ID:
            // Handle any DatePickerDialog initialization here
            DatePickerDialog dateDialog = (DatePickerDialog) dialog;
            int iDay,iMonth,iYear;

            // Always init the date picker to today's date
            Calendar cal = Calendar.getInstance();
            iDay = cal.get(Calendar.DAY_OF_MONTH);
            iMonth = cal.get(Calendar.MONTH);
            iYear = cal.get(Calendar.YEAR);
            dateDialog.updateDate(iYear, iMonth, iDay);
            return;
        case TIME_DIALOG_ID:
            // Handle any TimePickerDialog initialization here
            TimePickerDialog timeDialog = (TimePickerDialog) dialog;
            int iHour,iMinute;

            // Always init the time picker to current time
            Calendar cal1 = Calendar.getInstance();
            iHour = cal1.get(Calendar.HOUR_OF_DAY);
            iMinute = cal1.get(Calendar.MINUTE);
            timeDialog.updateTime(iHour, iMinute);
            return;
        case CUSTOM_DIALOG_ID:
            // Handle any custom "Password" Dialog initialization here
            // Since we don't want to show old password dialogs, just set new
            // ones, we need not do anything here
            // Because we are not "reusing" password dialogs once they have
            // finished, but removing them from
            // the Activity Dialog pool explicitly with removeDialog() and
            // recreating them as needed.
            return;
        }
    }
}