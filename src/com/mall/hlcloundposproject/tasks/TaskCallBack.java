package com.mall.hlcloundposproject.tasks;

/**
 * 异步任务的    回调接口  用于回调  获得的数据信息
 * @author hl
 */
public interface TaskCallBack {

	/**
	 * 异步任务执行完毕后的  回调方法,参数为  回调回来的数据对象
	 */
	public void onTaskFinished(TaskResult result);

}
