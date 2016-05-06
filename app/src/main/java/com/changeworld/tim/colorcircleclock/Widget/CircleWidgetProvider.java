package com.changeworld.tim.colorcircleclock.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;

import java.util.Calendar;
import java.util.Locale;

//WeekDay
public class CircleWidgetProvider extends AppWidgetProvider {

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
        int h = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        widgetTool = new WidgetTool(context, Setting.COLOR_BLUE, h, 7);

        Calendar calendar = Calendar.getInstance(Locale.TAIWAN);



        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.text, dayOfWeek + "");
        views.setImageViewBitmap(R.id.circle, widgetTool.draw(dayOfWeek, dayOfWeek / 7));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
