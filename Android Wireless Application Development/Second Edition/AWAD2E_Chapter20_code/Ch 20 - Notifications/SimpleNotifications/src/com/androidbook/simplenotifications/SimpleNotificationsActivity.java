package com.androidbook.simplenotifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class SimpleNotificationsActivity extends Activity {
    public static final String NOTIFY_KEY_1 = "NOTIFY_KEY_1";
    private static final int NOTIFY_1 = 0x1001;
    private static final int NOTIFY_2 = 0x1002;
    private static final int NOTIFY_3 = 0x1003;
    private static final int NOTIFY_4 = 0x1004;
    private static final int NOTIFY_5 = 0x1005;
    private NotificationManager notifier = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        notifier = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // create this outside the button so we can increment the number drawn over the icon
        final Notification notify = new Notification(R.drawable.android_32, "Hello!", System.currentTimeMillis());
        notify.icon = R.drawable.android_32;
        notify.tickerText = "Hello!";
        notify.when = System.currentTimeMillis();
        Button notify1 = (Button) findViewById(R.id.notify1);
        notify1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notify.number++;
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
                PendingIntent intentBack = PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0);
                notify.setLatestEventInfo(SimpleNotificationsActivity.this, "Hi there!", "This is even more text.",
                        intentBack);
                notifier.notify(NOTIFY_1, notify);
            }
        });
        Button notify2 = (Button) findViewById(R.id.notify2);
        notify2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Notification notify = new Notification(android.R.drawable.stat_notify_chat, "Vibrate!", System
                        .currentTimeMillis());
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notify.vibrate = new long[] { 200, 200, 600, 600, 600, 200, 200, 600, 600, 200, 200, 200, 200, 600,
                        200, 200, 600, 200, 200, 600, 600, 200, 600, 200, 600, 600, 200, 200, 200, 600, 600, 200, 200,
                        200, 200, 600, };
                Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
                PendingIntent intentBack = PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0);
                notify.setLatestEventInfo(SimpleNotificationsActivity.this, "Bzzt!", "This vibrated your phone.",
                        intentBack);
                notifier.notify(NOTIFY_2, notify);
                // more than one way to vibrate
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(500);
            }
        });
        Button notify3 = (Button) findViewById(R.id.notify3);
        notify3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notify.number++;
                notify.flags |= Notification.FLAG_SHOW_LIGHTS;
                if (notify.number < 2) {
                    notify.ledARGB = Color.GREEN;
                    notify.ledOnMS = 1000;
                    notify.ledOffMS = 1000;
                } else if (notify.number < 3) {
                    notify.ledARGB = Color.BLUE;
                    notify.ledOnMS = 750;
                    notify.ledOffMS = 750;
                } else if (notify.number < 4) {
                    notify.ledARGB = Color.WHITE;
                    notify.ledOnMS = 500;
                    notify.ledOffMS = 500;
                } else {
                    notify.ledARGB = Color.RED;
                    notify.ledOnMS = 50;
                    notify.ledOffMS = 50;
                }
                Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
                PendingIntent intentBack = PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0);
                notify.setLatestEventInfo(SimpleNotificationsActivity.this, "Bright!", "This lit up your phone.",
                        intentBack);
                notifier.notify(NOTIFY_3, notify);
            }
        });
        Button notify4 = (Button) findViewById(R.id.notify4);
        notify4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notify.audioStreamType = AudioManager.STREAM_NOTIFICATION;
                notify.sound = Uri.parse("android.resource://com.androidbook.simplenotifications/"+R.raw.fallbackring);
                Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
                PendingIntent intentBack = PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0);
                notify.setLatestEventInfo(SimpleNotificationsActivity.this, "Wow!", "This made your phone noisy.",
                        intentBack);
                notifier.notify(NOTIFY_4, notify);
            }
        });
        Button notifyRemote = (Button) findViewById(R.id.notifyRemote);
        notifyRemote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                RemoteViews remote = new RemoteViews(getPackageName(), R.layout.remote);
                remote.setTextViewText(R.id.text1, "Big text here!");
                remote.setTextViewText(R.id.text2, "Red text down here!");
                notify.contentView = remote;
                Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
                PendingIntent intentBack = PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0);
                notify.contentIntent = intentBack;
                notifier.notify(NOTIFY_5, notify);
            }
        });
    }
}