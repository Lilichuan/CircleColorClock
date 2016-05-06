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
        int h = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

        widgetTool = new WidgetTool(context, Setting.COLOR_BLUE, h, 7);

        Calendar calendar = Calendar.getInstance(Locale.TAIWAN);

        SimpleDateFormat format = new SimpleDateFormat("EEE");
        String text = format.format(Calendar.getInstance().getTime());

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int adjustDayOfWeek = 0;

        switch (dayOfWeek){
            case 1:
                adjustDayOfWeek = 7;
                break;
            case 2:
                adjustDayOfWeek = 1;
                break;
            case 3:
                adjustDayOfWeek = 2;
                break;
            case 4:
                adjustDayOfWeek = 3;
                break;
            case 5:
                adjustDayOfWeek = 4;
                break;
            case 6:
                adjustDayOfWeek = 5;
                break;
            case 7:
                adjustDayOfWeek = 6;
                break;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.text, text);
        views.setImageViewBitmap(R.id.circle, widgetTool.draw(dayOfWeek - 1, (float) adjustDayOfWeek / 7f));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
