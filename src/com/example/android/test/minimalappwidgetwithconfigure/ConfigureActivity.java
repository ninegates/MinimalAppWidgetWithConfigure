package com.example.android.test.minimalappwidgetwithconfigure;

import java.util.Arrays;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigureActivity extends Activity
{
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	private Button mBtnApply;

	private boolean mIsUserInteracted = false;
	private boolean mIsUserLeaveHinted = false;

	private final String TAG = getClass().getSimpleName();

	static
	{
		// android.os.Debug.waitingForDebugger();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.v(TAG, "onCreate isTaskRoot=" + isTaskRoot() + " isChild=" + isChild());

		AppWidgetManager wm = AppWidgetManager.getInstance(this);
		ComponentName providerComponentName = new ComponentName(this, MainAppWidgetProvider.class);
		int[] placedAppWidgetIds = wm.getAppWidgetIds(providerComponentName);
		Log.v(TAG, "placedAppWidgetIds=" + Arrays.toString(placedAppWidgetIds));

		super.onCreate(savedInstanceState);
		setContentView(R.layout.configure_activity);

		mBtnApply = (Button) findViewById(R.id.button1);
		mBtnApply.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				applySetting();
			}
		});

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null)
		{
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		Intent resultIntent = new Intent();
		resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_CANCELED, resultIntent);

		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
		{
			Toast.makeText(this, "invalid AppWidgetId", Toast.LENGTH_SHORT).show();
			finish();
		}

		((TextView) findViewById(R.id.textView1)).setText("設置中のAppWidgetID:" + mAppWidgetId);
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

		mIsUserInteracted = mIsUserLeaveHinted = false;
	}

	@Override
	protected void onPause()
	{
		Log.v(TAG, "onPause. isFinishing=" + isFinishing());
		super.onPause();

		if (isFinishing())
		{
			//HOMEキー押下時ここを通るがfinish()しても効果なし
			finish();
		}
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

		mIsUserLeaveHinted = true;
	}

	@Override
	public void onUserInteraction()
	{
		Log.v(TAG, "onUserInteraction");

		mIsUserInteracted = true;
	}

	private void applySetting()
	{
		// 本来はここで設定保存

		Intent resultIntent = new Intent();
		resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultIntent);

		finish();
	}

	@Override
	public void finish()
	{
		Log.v(TAG, "calling finish");
		super.finish();
	}
}
