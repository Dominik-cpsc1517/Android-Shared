package com.example.batterywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;

public class BatteryWidgetProvider extends AppWidgetProvider
{
    RemoteViews views;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        context.getApplicationContext().registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        ComponentName cn = new ComponentName(context, BatteryWidgetProvider.class);
        appWidgetManager.updateAppWidget(cn, views);
    }
    private BroadcastReceiver batteryReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Integer level = intent.getIntExtra("level", -1);
            views = new RemoteViews(context.getPackageName(),R.layout.widget_layout_green);
            views.setTextViewText(R.id.text_view_status, level.toString() + "%");
            ComponentName cn = new ComponentName(context, BatteryWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(cn, views);
        }
    };
}
