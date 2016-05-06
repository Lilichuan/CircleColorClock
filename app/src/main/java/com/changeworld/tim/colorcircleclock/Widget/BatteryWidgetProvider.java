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
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        reDraw(context, appWidgetManager, appWidgetId);
    }

    private void reDraw(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int h = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

        widgetTool = new WidgetTool(context, Setting.COLOR_YELLOW,h);

        if(batteryTool == null){
            batteryTool = new BatteryTool(context);
        }

        float select = batteryTool.getBatteryAgain();
        int percent = ((int)(select*100));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.text, percent + "%");
        views.setTextViewText(R.id.text2, context.getString(R.string.battery));

        views.setImageViewBitmap(R.id.circle, widgetTool.draw((int)select*10, select));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


}
