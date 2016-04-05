package com.mall.hlcloundposproject.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * volley 的回调  数据信息类：
 * com.hlrj.hlcloundpos.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-28
 */
public final class VolleyUtils {
	
	private VolleyCallback volleyCallback;//创建   接口参数
	
//	实现、、构造参数  回调：
	public VolleyUtils(VolleyCallback volleyCallback){
		this.volleyCallback = volleyCallback;
	}
	
	/**
	 * 使用   volley   回调数据信息：
	 * @param url
	 * @param context
	 */
	public void getVolleyDataInfo(String url,final Context context,final int authority) {
		
		//实例化请求对象
		StringRequest request = new StringRequest(url,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
					if(response!=null){
						
						//回调数据：
						JSONObject Object = JSON.parseObject(response);
						
						if(Object.getIntValue("resultStatus")==1){
							JSONArray array = Object.getJSONArray("data");
							volleyCallback.volleyFinishedSuccess(array,authority);
						}
						
					}
				}
			},new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					volleyCallback.vollayFinishedFail(authority);
				}
			});
								
			//3. 将请求添加到Volley的请求对列中
			Volley.newRequestQueue(context).add(request);
		}
	
	/**
	 * 回调接口     
	 * com.hlrj.hlcloundpos.utils
	 * @Email zhaoq_hero@163.com
	 * @author zhaoQiang : 2016-1-28
	 */
	public interface VolleyCallback{
		/**
		 * 回调  方法  用于实现   回调
		 * @param array  回调 回去的  数据
		 * @param authority   标识：
		 */
		void volleyFinishedSuccess(JSONArray array,int authority);
		void vollayFinishedFail(int authority);
	} 
}
