package com.changeworld.tim.colorcircleclock.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;

import java.text.SimpleDateFormat;
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
        int h = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        widgetTool = new WidgetTool(context, Setting.COLOR_BLUE, 7);

        Calendar calendar = Calendar.getInstance(Locale.TAIWAN);

        SimpleDateFormat format = new SimpleDateFormat("EEE");
        String text = format.format(Calendar.getInstance().getTime());

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int adjustDayOfWeek = 0;

        switch (dayOfWeek){
            case Calendar.SUNDAY:
                adjustDayOfWeek = 7;
                break;
            case Calendar.MONDAY:
                adjustDayOfWeek = 1;
                break;
            case Calendar.TUESDAY:
                adjustDayOfWeek = 2;
                break;
            case Calendar.WEDNESDAY:
                adjustDayOfWeek = 3;
                break;
            case Calendar.THURSDAY:
                adjustDayOfWeek = 4;
                break;
            case Calendar.FRIDAY:
                adjustDayOfWeek = 5;
                break;
            case Calendar.SATURDAY:
                adjustDayOfWeek = 6;
                break;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_2);
        views.setTextViewText(R.id.text, text);
        views.setImageViewBitmap(R.id.circle, widgetTool.draw(adjustDayOfWeek, (float) adjustDayOfWeek / 7f));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
