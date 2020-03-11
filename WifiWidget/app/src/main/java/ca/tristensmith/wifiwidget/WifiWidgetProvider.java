package ca.tristensmith.wifiwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.RemoteViews;

public class WifiWidgetProvider extends AppWidgetProvider
{

    RemoteViews views;
    private static final String TAG = "WifiWidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        ComponentName thisWidget = new ComponentName(context, WifiWidgetProvider.class);
        int[] widgetIdArray = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : widgetIdArray)
        {
            Intent intent = new Intent(context, WifiWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            WifiManager wifiManager = (WifiManager)context.getSystemService(context.WIFI_SERVICE);

            if(wifiManager.isWifiEnabled())
            {
                wifiManager.setWifiEnabled(false);
                views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_red);
                Log.d(TAG, "WIFI OFF");
            }else{
                wifiManager.setWifiEnabled(true);
                views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_green);
                Log.d(TAG, "WIFI ON");
            }
            views.setOnClickPendingIntent(R.id.text_view_status, pendingIntent);
            appWidgetManager.updateAppWidget(thisWidget, views);
        }

    }
}
