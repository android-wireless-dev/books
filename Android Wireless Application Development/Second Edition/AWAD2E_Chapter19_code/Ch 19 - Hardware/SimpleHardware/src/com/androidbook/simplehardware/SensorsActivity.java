package com.androidbook.simplehardware;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

@SuppressWarnings("serial")
public class SensorsActivity extends Activity implements SensorEventListener {

    private static final String DEBUG_TAG = "SensorsActivity";
    private SensorManager sensors = null;
    private static final Map<Integer, Integer> buttonSensorMap = new HashMap<Integer, Integer>() {
        {
            put(R.id.sensor_accel, Sensor.TYPE_ACCELEROMETER);
            put(R.id.sensor_light, Sensor.TYPE_LIGHT);
            put(R.id.sensor_mag, Sensor.TYPE_MAGNETIC_FIELD);
            put(R.id.sensor_orient, Sensor.TYPE_ORIENTATION);
            put(R.id.sensor_prox, Sensor.TYPE_PROXIMITY);
            put(R.id.sensor_temp, Sensor.TYPE_TEMPERATURE);
            put(R.id.sensor_pressure, Sensor.TYPE_PRESSURE);
            put(R.id.sensor_gyro, Sensor.TYPE_GYROSCOPE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors);
        final TextView status = (TextView) findViewById(R.id.status);
        sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        final RadioGroup sensorPicker = (RadioGroup) findViewById(R.id.sensor_group);

        final Button start = (Button) findViewById(R.id.start_sensor);
        final Button stop = (Button) findViewById(R.id.stop_sensor);

        sensorPicker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.v(DEBUG_TAG, "onCheckedChanged");
                handleStartSensor(status, start, stop, checkedId);
            }

        });

        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.v(DEBUG_TAG, "onClickListener");
                int checkedId = sensorPicker.getCheckedRadioButtonId();
                handleStartSensor(status, start, stop, checkedId);
            }

        });

        stop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sensors.unregisterListener(SensorsActivity.this);
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
            }

        });

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        String sensorName = sensor.getName();
        
        TextView status = (TextView) findViewById(R.id.status);
        
        status.setText("Sensor: " + sensorName + " changed accuracy to: "+accuracy);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        StringBuilder sensorMessage = new StringBuilder(event.sensor.getName()).append(" new values: ");
        
        for (float value : event.values) {
            sensorMessage.append("[").append(value).append("]");
        }
        
        sensorMessage.append(" with accuracy ").append(event.accuracy);
        sensorMessage.append(" at timestamp ").append(event.timestamp);
        
        sensorMessage.append(".");
        
        TextView status = (TextView) findViewById(R.id.status);
        status.setText(sensorMessage);
    }

    public void onPause() {
        if (sensors != null) {
            sensors.unregisterListener(this);
        }
        super.onPause();
    }

    private void handleStartSensor(TextView status, Button start, Button stop, int checkedId) {
        Sensor defaultSensor = sensors.getDefaultSensor(buttonSensorMap.get(checkedId));
        boolean isAvailable = sensors.registerListener(SensorsActivity.this, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (!isAvailable) {
            RadioButton checked = (RadioButton) findViewById(checkedId);
            status.setText("Current sensor (" + checked.getText() + ") not available.");
        } else {

            stop.setVisibility(View.VISIBLE);
            start.setVisibility(View.GONE);
        }
    }


}
