package ca.tristensmith.buttonwidget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class ButtonWidgetProvider extends AppWidgetProvider
{

    public static String BUTTON_ACTION_LAUNCH = "launch_button";
    public static String BUTTON_ACTION_NOTIFY = "notify_button";
    public static String MESSAGE_KEY = "message";

    public static final String TAG = "ButtonWidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        Log.d(TAG, "in onUpdate");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent launchIntent = new Intent(context, MainActivity.class);
        launchIntent.setAction(BUTTON_ACTION_LAUNCH);

        Intent notifyIntent = new Intent(context, ButtonWidgetProvider.class);
        notifyIntent.setAction(BUTTON_ACTION_NOTIFY);
        notifyIntent.putExtra(MESSAGE_KEY, "Message posted");

        PendingIntent launchPendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(context, 0, notifyIntent, 0);

        views.setOnClickPendingIntent(R.id.button_one, launchPendingIntent);
        views.setOnClickPendingIntent(R.id.button_two, notifyPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, views);


        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(BUTTON_ACTION_NOTIFY))
        {
            String msg = null;
            try
            {
                msg = intent.getStringExtra(MESSAGE_KEY);
            }
            catch (NullPointerException npe)
            {
                Log.d(TAG, "Message is null");
            }
            PendingIntent notifyIntent = PendingIntent.getActivity(context, 0, intent, 0);
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setAutoCancel(false);
            builder.setTicker("this is the ticker");
            builder.setContentTitle("Content Title");
            builder.setContentText(msg);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentIntent(notifyIntent);
            //builder.setOngoing(true);
            builder.setNumber(100);
            builder.build();

            Notification notification = builder.getNotification();
            nm.notify(1, notification);
        }

        super.onReceive(context, intent);
    }
}
