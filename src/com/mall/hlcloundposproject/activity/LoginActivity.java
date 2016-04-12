package com.mall.hlcloundposproject.activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.Content;
import com.mall.hlcloundposproject.MainActivity;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.db.MyOpenHelper;
import com.mall.hlcloundposproject.entity.User;
import com.mall.hlcloundposproject.tasks.TaskCallBack;
import com.mall.hlcloundposproject.tasks.TaskResult;
import com.mall.hlcloundposproject.tasks.UserLoginTask;
import com.mall.hlcloundposproject.utils.KeyboardUtil;
import com.mall.hlcloundposproject.utils.MyProgressDialog;
import com.mall.hlcloundposproject.utils.MyToast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录   界面
 */
public class LoginActivity extends Activity implements OnClickListener, TaskCallBack, OnTouchListener {

	@ViewInject(R.id.account)
	private EditText etAccount;
	@ViewInject(R.id.password)
	private EditText etPassword;

	@ViewInject(R.id.login_btn_sure)
	private Button loginSure;
	
	@ViewInject(R.id.login_btn_exit)
	private Button loginExit;
	
	@ViewInject(R.id.keys1)
	private ImageView keysIc1;
	
	@ViewInject(R.id.keys2)
	private ImageView keysIc2;
	
	/**
	 * 尝试登录时的      账户和密码值：
	 * */
	private String account;
	private String password;
	private SQLiteOpenHelper userHelper;
	private SQLiteDatabase userdb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login_in);
		
		ViewUtils.inject(this);
		
		userHelper = new MyOpenHelper(LoginActivity.this,
				Content.USER_INFO_DB_NAME);
		
		/**
		 * 初始化编辑事件    和登录事件
		 */
		loginSure.setOnClickListener(this);
		loginExit.setOnClickListener(this);
		
		keysIc1.setOnClickListener(this);
		keysIc2.setOnClickListener(this);
		
		
		
		etPassword.setOnTouchListener(this);
		etAccount.setOnTouchListener(this);
	}

	//登录和取消的    单击事件：
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.login_btn_sure:
				
				// 获取  数据值
				account = etAccount.getText().toString().trim();
				password = etPassword.getText().toString().trim();
				
				// 账户验证：
				if (TextUtils.isEmpty(account)) {
					//  如果为null
					MyToast.ToastIncenter(this, "账号不准为空").show();
					etAccount.requestFocus();
					break;
				} 
				
				// 密码验证
				if (TextUtils.isEmpty(password)) {
					MyToast.ToastIncenter(this, "密码不准为空").show();
					etPassword.requestFocus();
					break;
				} 
				
				userdb = userHelper.getReadableDatabase();
				
				Cursor cursor = userdb.query(Content.TABLE_USERS_NAME,
						new String[]{"user","password","name","right","ki","quanXian"},
						"user = '"+ account+"'", null,null, null, null);
				
				MyProgressDialog.showProgress(this,"正在登录...",null);
				/**
				 * 验证存在用户：
				 */
				switch(cursor.getCount()){
					case 0://没有查询到用户   到服务器端查找
						new UserLoginTask(this).execute(String.format(Configs.LOGIN_USER,account));
						break;
					case 1://查询到 一位用户：判断密码  是否一样
						User user = new User();
						while(cursor.moveToNext()){
							if(cursor.getString(cursor.getColumnIndex("password")).equals(password)){
								//获取user 对象：
								user.setKi(cursor.getString(cursor.getColumnIndex("ki")));
								user.setName(cursor.getString(cursor.getColumnIndex("name")));
								user.setPass(cursor.getString(cursor.getColumnIndex("password")));
								user.setQuanxian(cursor.getString(cursor.getColumnIndex("quanXian")));
								user.setRight(cursor.getString(cursor.getColumnIndex("right")));
								user.setUser(cursor.getString(cursor.getColumnIndex("user")));
								
								toMainActivity(user);
								
								finish();
							}else{
								Toast.makeText(this, "请验证输入密码", Toast.LENGTH_SHORT).show();
							}
							MyProgressDialog.stopProgress();
							break;
						}
						
						break;
					default:
						//查询到 多位用户
						MyToast.ToastIncenter(this, "请联系管理员，当前用户名存在冲突。").show();
						MyProgressDialog.stopProgress();
						break;
					}
				
				break;
				
			case R.id.login_btn_exit:
				finish();
				break;
				
			case R.id.keys1:
				etAccount.setFocusable(true);
				etAccount.requestFocus();
				etAccount.setInputType(InputType.TYPE_NULL);
				new KeyboardUtil(this,getApplicationContext(),etAccount).showKeyboard();
				
				break;
			case R.id.keys2:
				etPassword.setFocusable(true);
				etPassword.requestFocus();
				etPassword.setInputType(InputType.TYPE_NULL);
				new KeyboardUtil(this,getApplicationContext(),etPassword).showKeyboard();
				
				break;

				
			default:
					break;
		}
	}

	//数据   回调完后的   回调方法
	@Override
	public void onTaskFinished(TaskResult result) {
		/**
		 * 判断返回数据  和异步任务的标识：
		 */
		if(result!=null && result.task_id == Configs.USER_LOGIN_TASK_AUTHORITY){
			
			if(result.resultStatus != 0 ){
				JSONArray array = (JSONArray) result.resultData;
					try {
						JSONObject jobj = array.getJSONObject(0);
						User user = User.userLoginParser(jobj);
						/**
						 * 密码一样    进行登录
						 */
						if(user.getPass().equals(password)){
							// 可以登录   界面
							toMainActivity(user);
							
							MyProgressDialog.stopProgress();
							
							/**
							 * 保存信息  到本地 数据库
							 */
							userdb = userHelper.getWritableDatabase();
							// 保存用户信息到本地
							ContentValues values = new ContentValues();
							values.put("ki", user.getKi());
							values.put("name", user.getName());
							values.put("password", user.getPass());
							values.put("quanxian", user.getQuanxian());
							values.put("right", user.getRight());
							values.put("user",user.getUser());
							
							userdb.insert(Content.TABLE_USERS_NAME, null, values);
						}else{
							MyProgressDialog.stopProgress();
							MyToast.ToastIncenter(this,"请验证输入密码").show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}else{
				MyProgressDialog.stopProgress();
				MyToast.ToastIncenter(this, "请验证用户名或密码").show();
			}
		}
	}

	/**
	 * 打开主界面：
	 * @param user
	 */
	private void toMainActivity(User user) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		intent.putExtra("user",user);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			
			switch(v.getId()){
				
				case R.id.account:
					etAccount.setInputType(InputType.TYPE_NULL);
					etAccount.setFocusable(true);
					etPassword.setFocusable(false);
					etAccount.requestFocus();
					keysIc1.setClickable(true);
					break;
				case R.id.password:
					etPassword.setInputType(InputType.TYPE_NULL);
					etPassword.setFocusable(true);
					etAccount.setFocusable(false);
					etPassword.requestFocus();
					keysIc2.setClickable(true);
					break;
				
				default:
					break;
			
			}
		}
		return false;
	}

}
