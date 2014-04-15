package com.example.businesscardholder;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity
{
	TabHost tabHost;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupTabHost();
	}

	private void setupTabHost()
	{
		tabHost = getTabHost();
		addTab((Class) InfoActivity.class, "Info", "Info");
		addTab((Class) LocalActivity.class, "Local", "Local");
		addTab((Class) WebActivity.class, "Web", "Web");
		tabHost.setCurrentTab(0);
	}

	private void addTab(Class activityClass, String spec, String indicator)
	{
		Intent intentInfo = new Intent().setClass(this, activityClass);
		TabSpec tabSpecInfo = tabHost.newTabSpec(spec).setIndicator(indicator).setContent(intentInfo);
		tabHost.addTab(tabSpecInfo);
	}

}
