package com.example.android.test.minimalappwidgetwithconfigure;

import java.util.Arrays;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LauncherActivity extends Activity
{
	private final String TAG = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher_activity);

		AppWidgetManager wm = AppWidgetManager.getInstance(this);
		ComponentName providerComponentName = new ComponentName(this, MainAppWidgetProvider.class);
		int[] placedAppWidgetIds = wm.getAppWidgetIds(providerComponentName);
		Log.v(TAG, "placedAppWidgetIds=" + Arrays.toString(placedAppWidgetIds));

		ListView listView = (ListView) findViewById(R.id.listViewTest);
		final ArrayAdapter<String> aryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		for (int awId : placedAppWidgetIds)
		{
			aryAdapter.add(String.valueOf(awId));
		}
		listView.setAdapter(aryAdapter);
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String strId = aryAdapter.getItem(position);
				int[] selectAWIds = { Integer.valueOf(strId) };

				Intent intent = new Intent(getString(R.string.action_appwidget_manual_update));
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, selectAWIds);
				sendBroadcast(intent);
			}
		});
	}

	@Override
	protected void onRestart()
	{
		Log.v(TAG, "onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		Log.v(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		Log.v(TAG, "onPause. isFinishing=" + isFinishing());
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		Log.v(TAG, "onStop. isFinishing=" + isFinishing());
		super.onStop();
	};

	@Override
	protected void onDestroy()
	{
		Log.v(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onUserLeaveHint()
	{
		Log.v(TAG, "onUserLeaveHint");
	}

	@Override
	public void onUserInteraction()
	{
		Log.v(TAG, "onUserInteraction");
	}
}