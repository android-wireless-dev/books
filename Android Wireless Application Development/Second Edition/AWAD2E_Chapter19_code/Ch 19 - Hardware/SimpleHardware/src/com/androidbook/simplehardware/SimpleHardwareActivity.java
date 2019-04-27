package com.androidbook.simplehardware;

public class SimpleHardwareActivity extends MenuActivity {

    @Override
    void prepareMenu() {

        addMenuItem("1. WiFi Sample", WiFiActivity.class);
        addMenuItem("2. Sensors Sample", SensorsActivity.class);
        addMenuItem("3. Battery Monitor", BatteryActivity.class);
    }

}