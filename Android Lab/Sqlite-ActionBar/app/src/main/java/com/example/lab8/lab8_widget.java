package com.example.lab8;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class lab8_widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        StringBuilder widgetText = new StringBuilder();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Product> plist = databaseHelper.getAllProduct();
        int j = 1;
        for(Product i : plist) {
            widgetText.append(j);
            j++;
            widgetText.append(". Product Name: ").append(i.getName());
            widgetText.append("\n");
        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.lab8_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText.toString());
        views.setOnClickPendingIntent(R.id.open,pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, lab8_widget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            StringBuilder widgetText = new StringBuilder();
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            List<Product> plist = databaseHelper.getAllProduct();
            int j = 1;
            for(Product i : plist) {
                widgetText.append(j);
                j++;
                widgetText.append(". Product Name: ").append(i.getName());
                widgetText.append("\n");
            }

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.lab8_widget);
            views.setTextViewText(R.id.appwidget_text, widgetText.toString());

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}