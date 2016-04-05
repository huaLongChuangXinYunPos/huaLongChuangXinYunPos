package com.mall.hlcloundposproject.client;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mall.hlcloundposproject.utils.HttpTools;

/**
 * 所有    访问 网络的   请求类
 * @author zhaoq_hero@163.com
 */
public final class ClientApi {
	
	private ClientApi() {
    }
	
	public static JSONObject getClientInfo(String url) {
		
		JSONObject ret =null;
		
		if(url!=null){
			
			byte[] data = HttpTools.doGet(url);
			
			if(data!=null){
				
				String str;
				try {
					
					str = new String(data,"utf-8");
					ret = JSON.parseObject(str);
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	}

}
