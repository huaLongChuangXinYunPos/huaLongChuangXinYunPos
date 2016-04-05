package com.mall.hlcloundposproject.tasks;

/**
 * 该类  用于 记录异步任务   的返回标识   和返回数据对象
 * 
 * 返回对象数据   和异步任务标识的   封装类
 * @author hl
 */
public final class TaskResult {
	
	/**
	 * 异步任务的唯一标识     可用于解决数据错乱问题
	 */
	public int task_id; 
	
	/**
	 * 服务器返回数据结果    1，代表有数据，0代表无数据  默认无数据
	 */
	public int resultStatus = 0;
	
	/**
	 * 任意数据类型，保存异步任务返回的数据对象
	 */
	public Object resultData;

}
