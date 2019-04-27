package com.androidbook.triviaquiz18;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class QuizWidgetProvider extends AppWidgetProvider {

    // private static final int IO_BUFFER_SIZE = 4 * 1024;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // push this to a Service so it runs in the background
        // We can't use a thread because the Provider may not remain around
        // (Don't forget to add the service entry to the Manifest)

        Intent serviceIntent = new Intent(context, WidgetUpdateService.class);
        context.startService(serviceIntent);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Note: Ignoring the appWidgetids is safe, but could stop an update for instance
        // of this app widget if more than one is running. This widget is not designed to be
        // a multi-instance widget.
        Intent serviceIntent = new Intent(context, WidgetUpdateService.class);
        context.stopService(serviceIntent);

        super.onDeleted(context, appWidgetIds);

    }

    public static class WidgetUpdateService extends Service {
        Thread widgetUpdateThread = null;

        private static final String DEBUG_TAG = "WidgetUpdateService";

        @Override
        public int onStartCommand(Intent intent, int flags, final int startId) {
            widgetUpdateThread = new Thread() {
                public void run() {
                    Context context = WidgetUpdateService.this;
                    WidgetData widgetData = new WidgetData("Unknown", "NA", "");

                    getWidgetData(widgetData);

                    // prep the RemoteView
                    String packageName = context.getPackageName();
                    Log.d(DEBUG_TAG, "packageName: " + packageName);
                    RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget);
                    remoteView.setTextViewText(R.id.widget_nickname, widgetData.nickname);
                    remoteView.setTextViewText(R.id.widget_score, "Score: " + widgetData.score);
                    if (widgetData.avatarUrl.length() > 0) {
                        // remoteView.setImageViewUri(R.id.widget_image, Uri.parse(avatarUrl));
                        URL image;
                        try {
                            image = new URL(widgetData.avatarUrl);
                            Log.d(DEBUG_TAG, "avatarUrl: " + widgetData.avatarUrl);

                            // See http://bit.ly/bAtW6W and http://bit.ly/a3Qkw4 for the reasons for not using decodeStream directly
                            // (in short, it works but not in certain situations)
                            // The work around shown below was also used in Android Wireless Application Development.

                            Bitmap bitmap = BitmapFactory.decodeStream(image.openStream());
                            /*
                             * BufferedInputStream in;
                             * BufferedOutputStream out;
                             * 
                             * in = new BufferedInputStream(image.openStream(), IO_BUFFER_SIZE);
                             * final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                             * out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
                             * copy(in, out);
                             * // implementation provided at the bottom of this file; uncomment to use
                             * out.flush();
                             * 
                             * final byte[] data = dataStream.toByteArray();
                             * Log.d(DEBUG_TAG, "Length: "+ data.length);
                             * Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                             */
                            if (bitmap == null) {
                                Log.w(DEBUG_TAG, "Failed to decode image");

                                remoteView.setImageViewResource(R.id.widget_image, R.drawable.avatar);
                            } else {
                                remoteView.setImageViewBitmap(R.id.widget_image, bitmap);
                            }
                        } catch (MalformedURLException e) {
                            Log.e(DEBUG_TAG, "Bad url in image", e);
                        } catch (IOException e) {
                            Log.e(DEBUG_TAG, "IO failure for image", e);
                        }

                    } else {
                        remoteView.setImageViewResource(R.id.widget_image, R.drawable.avatar);
                    }

                    try {

                        // add click handling
                        Intent launchAppIntent = new Intent(context, QuizMenuActivity.class);
                        PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteView.setOnClickPendingIntent(R.id.widget_view, launchAppPendingIntent);

                        // get the Android component name for the QuizWidgetProvider
                        ComponentName quizWidget = new ComponentName(context, QuizWidgetProvider.class);

                        // get the instance of the AppWidgetManager
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                        // update the widget
                        appWidgetManager.updateAppWidget(quizWidget, remoteView);

                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, "Failed to update widget", e);
                    }

                    if (!WidgetUpdateService.this.stopSelfResult(startId)) {
                        Log.e(DEBUG_TAG, "Failed to stop service");
                    }
                }

                /**
                 * Download data for displaying in the Widget
                 * 
                 * @param widgetData
                 */
                private void getWidgetData(WidgetData widgetData) {
                    SharedPreferences prefs = getSharedPreferences(QuizActivity.GAME_PREFERENCES, Context.MODE_PRIVATE);
                    Integer playerId = prefs.getInt(QuizActivity.GAME_PREFERENCES_PLAYER_ID, -1);

                    try {
                        URL userInfo = new URL(QuizActivity.TRIVIA_SERVER_BASE + "getplayer?playerId=" + playerId);
                        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                        parser.setInput(userInfo.openStream(), null);

                        int eventType = -1;
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_TAG) {
                                String strName = parser.getName();

                                if (strName.equals("nickname")) {
                                    widgetData.nickname = parser.nextText();
                                } else if (strName.equals("score")) {
                                    widgetData.score = parser.nextText();
                                } else if (strName.equals("avatarUrl")) {
                                    widgetData.avatarUrl = parser.nextText();
                                }
                            }
                            eventType = parser.next();
                        }
                    } catch (MalformedURLException e) {
                        Log.e(DEBUG_TAG, "Bad URL", e);
                    } catch (XmlPullParserException e) {
                        Log.e(DEBUG_TAG, "Parser exception", e);
                    } catch (IOException e) {
                        Log.e(DEBUG_TAG, "IO Exception", e);
                    }
                }
            };

            // start the background thread
            widgetUpdateThread.start();

            // if we're killed, restart us with the original Intent so we get an extra data again, should we choose to use it later
            return START_REDELIVER_INTENT;
        }

        @Override
        public void onDestroy() {
            widgetUpdateThread.interrupt();
            super.onDestroy();
        }

        @Override
        public IBinder onBind(Intent intent) {
            // no binding; can't from an App Widget
            return null;
        }

        /**
         * Copy the content of the input stream into the output stream, using a temporary byte array buffer whose size is defined by {@link #IO_BUFFER_SIZE}.
         * 
         * @param in
         *            The input stream to copy from.
         * @param out
         *            The output stream to copy to.
         * @throws IOException
         *             If any error occurs during the copy.
         */
        /*
         * private static void copy(InputStream in, OutputStream out) throws IOException { byte[] b = new byte[IO_BUFFER_SIZE]; int read; while ((read = in.read(b)) != -1) { out.write(b, 0, read); } }
         */

        public static class WidgetData {
            String nickname;
            String score;
            String avatarUrl;

            public WidgetData(String nickname, String score, String avatarUrl) {
                super();
                this.nickname = nickname;
                this.score = score;
                this.avatarUrl = avatarUrl;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }
        }
    }

}
