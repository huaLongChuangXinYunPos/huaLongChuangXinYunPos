package com.mall.hlcloundposproject.tasks;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.client.ClientApi;

public class UserLoginTask extends BaseTask {

	public UserLoginTask(TaskCallBack taskCallback) {
		/**
		 * 访问父类    构造参数
		 */
		super(taskCallback);
	}

	@Override
	protected TaskResult doInBackground(String... params) {
		TaskResult result = new TaskResult();//创建返回结果对象
		//定义该异步任务的     标识   
		result.task_id = Configs.USER_LOGIN_TASK_AUTHORITY;
		
		if(params!=null){
			//获取网络数据
			JSONObject object = ClientApi.getClientInfo(params[0]);
			
			if(object!=null){
				//获取数据    状态信息 ：
				try {
					//信息  状态码：
					result.resultStatus = object.getIntValue("resultStatus");
					//代表获取到结果
					result.resultData = object.getJSONArray("data");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
