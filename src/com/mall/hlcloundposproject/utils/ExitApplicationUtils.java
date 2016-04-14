package com.mall.hlcloundposproject.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class ExitApplicationUtils extends Application {
	private List<Activity> mList = new LinkedList<Activity>();
	private static ExitApplicationUtils instance;

	private ExitApplicationUtils() {
	}

	public synchronized static ExitApplicationUtils getInstance() {
		if (null == instance) {
			instance = new ExitApplicationUtils();
		}
		return instance;
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

}
