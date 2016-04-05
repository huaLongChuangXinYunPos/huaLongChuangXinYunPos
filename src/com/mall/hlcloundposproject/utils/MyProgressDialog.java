package com.mall.hlcloundposproject.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 自定义  进度显示    对话框
 */
public final class MyProgressDialog extends ProgressDialog{
	
	/**
	 * 用volatile修饰的变量 
	 * 线程在每次使用变量的时候  都会读取变量修改后的值
	 * volatile用来进行原子性操作
	 */
	private static volatile MyProgressDialog dialog = null;
	
	@SuppressWarnings("unused")
	private Context context;

	/**
	 * 私有化   构造参数对象
	 * @param context
	 */
	private MyProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}
	
	/**
	 * 显示  当前进度  对话框
	 */
	public static void showProgress(Context context,String title,String message){
		dialog = new MyProgressDialog(context);
		
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.show();
	}
	
	/**
	 * 停止     当前进度显示框
	 */
	public static void stopProgress(){
		dialog.dismiss();
	}

}
