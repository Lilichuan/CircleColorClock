package com.changeworld.tim.colorcircleclock.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.changeworld.tim.colorcircleclock.Data.BatteryTool;
import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;

/**
 * Created by tim on 2016/5/6.
 */
public class BatteryWidgetProvider extends AppWidgetProvider {
    private WidgetTool widgetTool;
    private BatteryTool batteryTool;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (appWidgetIds != null && appWidgetIds.length > 0) {
            int appWidgetId = appWidgetIds[0];
            reDraw(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        reDraw(context, appWidgetManager, appWidgetId);
    }

    private void reDraw(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        if(widgetTool == null){
            widgetTool = new WidgetTool(context, Setting.COLOR_YELLOW);
        }

        if(batteryTool == null){
            batteryTool = new BatteryTool(context);
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.text, "ABC");

        float select = batteryTool.getBatteryAgain();;
        views.setImageViewBitmap(R.id.circle,widgetTool.draw((int)select*10, select));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
