package com.changeworld.tim.colorcircleclock.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;

import java.util.Calendar;

/**
 * Created by tim on 2016/5/6.
 */
public class WorkWidgetProvider extends AppWidgetProvider {

    private WidgetTool widgetTool;

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

        widgetTool = new WidgetTool(context, Setting.COLOR_GREEN, h);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);


        int full = 21 - 6;
        int now = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        float a = 0;

        if(now > 21 ){
            a = ((float) (now - 21)) / (float)9;
        }else if(now < 6){
            a = ((float) (now + 3)) / (float) 9;
        }else if(now >= 6 || now <= 21){
            a = ((float) now) / (float) full;
        }

        int pers = (int)(a*100);
        views.setTextViewText(R.id.text, pers + "%");
        views.setTextViewText(R.id.text2, context.getString(R.string.strength));
        views.setImageViewBitmap(R.id.circle, widgetTool.draw((int)(a * 10), a));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
