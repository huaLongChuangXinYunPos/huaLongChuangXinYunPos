package com.mall.hlcloundposproject.fragments;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.Content;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.activity.LoginActivity;
import com.mall.hlcloundposproject.db.MyOpenHelper;
import com.mall.hlcloundposproject.entity.User;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 更新  用户密码的  fragment
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-22
 */
public class UpdateUserPwdDialog extends DialogFragment implements OnClickListener {
	
	private View view;
	
	@ViewInject(R.id.update_password_sure)
	private Button upSureBtn;
	@ViewInject(R.id.update_password_exit)
	private Button upExitBtn;
	
	@ViewInject(R.id.update_currPwd)
	private EditText currPwdEt;
	@ViewInject(R.id.update_newPwd)
	private EditText newPwdEt;
	@ViewInject(R.id.update_rePwd)
	private EditText rePwdEt;
	
	private String currStr;
	private String newPwdStr;
	private String rePwdStr;
	
	private MyOpenHelper userDbHelper;

	private SQLiteDatabase userdb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		//获取数据库  帮助类
		userDbHelper = new MyOpenHelper(getActivity(),Content.USER_INFO_DB_NAME);
		
		getDialog().setTitle("修改密码");
		
		view = inflater.inflate(R.layout.update_password_fragment, container,false);
		
		ViewUtils.inject(this,view);
		
		initListener();
		
		return view;
	}

	//初始化    事件：
	private void initListener() {
		upSureBtn.setOnClickListener(this);
		upExitBtn.setOnClickListener(this);
	}

	/**
	 * 单击事件：
	 */
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.update_password_sure:
				
				currStr = currPwdEt.getText().toString().trim();
				newPwdStr = newPwdEt.getText().toString().trim();
				rePwdStr = rePwdEt.getText().toString().trim();

				user = (User) getArguments().getSerializable("user");
				
				if(currStr==null||currStr.equals("")){
					currPwdEt.setError("请验证当前密码");
					currPwdEt.requestFocus();
					break;
				}else if(newPwdStr==null||newPwdStr.equals("")){
					newPwdEt.setError("请验证输入密码");
					newPwdEt.requestFocus();
					break;
				}else if(rePwdStr==null||rePwdStr.equals("")){
					rePwdEt.setError("请验证当前密码");
					rePwdEt.requestFocus();
					break;
				}else if(!currStr.equals(    user.getPass())){
					currPwdEt.setError("当前密码输入不正确");
					currPwdEt.requestFocus();
					break;
				}else if(!rePwdStr.equals(newPwdStr)){
					rePwdEt.setError("验证密码和输入密码不匹配");
					rePwdEt.requestFocus();
					break;
				}else if(currStr.equals(newPwdStr)){
					newPwdEt.setError("新密码和当前密码不能相同");
					newPwdEt.requestFocus();
					break;
				}
				
				//满足条件：将   用户信息  修改了：发送数据到   服务器：
				updateServerUserInfo(Configs.SERVER_BASE_URL+String.format(Configs.UPDATE_PWD_TO_SERVER,user.getUser(),rePwdStr));
				break;
				
			case R.id.update_password_exit:
				//取消   按钮：
				onDestroyView();
				break;
		}
	}

	private void updateServerUserInfo(final String url) {
		//实例化请求对象
				StringRequest request = new StringRequest(url,new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
							if(response!=null){
								//回调数据：
								JSONObject Object = JSON.parseObject(response);
								if(Object.getIntValue("resultStatus")==1){
									
								userdb = userDbHelper.getWritableDatabase();
									
								ContentValues values = new ContentValues();
									
								values.put("password",rePwdStr);
									
								userdb.update(Content.TABLE_USERS_NAME,
								values, " user = ?", 
								new String[]{user.getUser()});
									
								onDestroyView();
									
								Toast.makeText(getActivity(), "密码修改成功,请重新登陆", Toast.LENGTH_LONG).show();
									
								Intent intent = new Intent();
								intent.setClass(getActivity(), LoginActivity.class);
								startActivity(intent);
									
								getActivity().finish();
							}else{
								Toast.makeText(getActivity(), "用户信息更新失败，请检查用户信息", 1).show();
							}
						}
					}
				},new ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), "用户信息更新失败，请检查服务器", 1).show();
					}
				});
										
			//3. 将请求添加到Volley的请求对列中
			Volley.newRequestQueue(getActivity()).add(request);
		}

	private static UpdateUserPwdDialog updateUserPassword;

	private User user;
	
	/**
	 * 获取     实例方法：
	 * @param user2
	 * @return
	 */
	public static UpdateUserPwdDialog getInstance(User user) {
			updateUserPassword = new UpdateUserPwdDialog();
			//添加数据：
			Bundle bundle = new Bundle();
			bundle.putSerializable("user", user);
			updateUserPassword.setArguments(bundle);
			
		return updateUserPassword;
	}
	
	@Override
	public void onDestroy() {
		if(userdb.isOpen()&&userdb!=null){
			userdb.close();
		}
		super.onDestroy();
	}
	
}
