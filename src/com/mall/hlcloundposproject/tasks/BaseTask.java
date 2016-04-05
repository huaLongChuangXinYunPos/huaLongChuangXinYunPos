package com.mall.hlcloundposproject.tasks;

import android.os.AsyncTask;

/**
 * 异步任务的    父类  封装类   该类  为一个抽象类
 * @author zhaoq_hero@163.com
 */
public abstract class BaseTask extends AsyncTask<String, Void, TaskResult> {

	/**
	 * 定义回调接口      用于回调数据
	 */
	private TaskCallBack taskCallback;

	/**
	 * 构造参数   
	 * @param taskCallback
	 */
	public BaseTask(TaskCallBack taskCallback) {
		this.taskCallback = taskCallback;
	}
	
	/**
	 * 重写该方法：
	 * 将   返回信息   回调给  主线程
	 */
	@Override
	protected void onPostExecute(TaskResult result) {
		if(result!=null){
			taskCallback.onTaskFinished(result);
		}
	}
	
}
