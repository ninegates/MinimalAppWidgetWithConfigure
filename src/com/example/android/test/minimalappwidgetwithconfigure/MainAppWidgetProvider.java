package com.example.android.test.minimalappwidgetwithconfigure;

import java.util.Arrays;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MainAppWidgetProvider extends AppWidgetProvider
{
	private final String TAG = getClass().getName();

	@Override
	public void onEnabled(Context context)
	{
		Log.v(TAG, "onEnabled");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		Log.v(TAG, "onUpdate appWidgetIds=" + Arrays.toString(appWidgetIds));

		ComponentName providerComponentName = new ComponentName(context, MainAppWidgetProvider.class);
		int[] placedAppWidgetIds = appWidgetManager.getAppWidgetIds(providerComponentName);
		Log.v(TAG, "placedAppWidgetIds=" + Arrays.toString(placedAppWidgetIds));

		for (int appWidgetId : appWidgetIds)
		{
			for (int placedAppWidgetId : placedAppWidgetIds)
			{
				if (placedAppWidgetId == appWidgetId)
				{
					updateAppWidget(context, appWidgetManager, appWidgetId);
					break;
				}
			}
		}
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		Log.v(TAG, "onReceive action=" + action);

		super.onReceive(context, intent);

		if (action.equals(context.getString(R.string.action_appwidget_manual_update)))
		{
			int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

			for (int appWidgetId : appWidgetIds)
			{
				updateAppWidget(context, appWidgetManager, appWidgetId);
			}
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		Log.w(TAG, "onDeleted appWidgetIds=" + Arrays.toString(appWidgetIds));
	}

	@Override
	public void onDisabled(Context context)
	{
		Log.v(TAG, "onDisabled");
	}

	/**
	 * ウィジェットの更新処理
	 *
	 * @param context
	 * @param appWidgetManager
	 * @param appWidgetId
	 */
	private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
	{
		Log.v(TAG, "updateAppWidget id=" + appWidgetId);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_base);
		remoteViews.setTextViewText(R.id.appWidgetTextView, "ID:" + String.valueOf(appWidgetId));
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

}
