package com.example.hlcloundposproject.activity;

import com.example.hlcloundposproject.Configs;
import com.example.hlcloundposproject.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class WelcomeActivity extends Activity implements Runnable {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		//��ȡ  �����������
		SharedPreferences sp = getSharedPreferences(
				Configs.APP_NAME, MODE_PRIVATE);
		
		int versionCode = 0;
		String versionName = "version";
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionCode = info.versionCode;
			versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		int appVersion = sp.getInt(versionName, 
				0);
		
		if( appVersion != versionCode){
			
			Thread thread = new Thread(this);
			thread.start();
			
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt(versionName,versionCode);
			editor.commit();
			
		}else{
			toActivity(SplashActivity.class);
		}
		
	}

	/**
	 *  ���µ�    activity
	 * @param class1
	 */
	private void toActivity(Class<SplashActivity> class1) {
		Intent intent = new Intent();
		intent.setClass(this, class1);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void run() {
		/**
		 * �̳߳�˯  ������
		 */
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		toActivity(SplashActivity.class);
	}
}
