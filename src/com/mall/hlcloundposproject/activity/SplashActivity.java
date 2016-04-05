package com.mall.hlcloundposproject.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.hlcloundposproject.Configs;
import com.mall.hlcloundposproject.Content;
import com.mall.hlcloundposproject.R;
import com.mall.hlcloundposproject.db.MyOpenHelper;
import com.mall.hlcloundposproject.db.OperationDbTableUtils;
import com.mall.hlcloundposproject.entity.JsType;
import com.mall.hlcloundposproject.entity.SpecialGoods;
import com.mall.hlcloundposproject.entity.User;
import com.mall.hlcloundposproject.entity.VIPGoods;
import com.mall.hlcloundposproject.utils.FastJsonUtils;
import com.mall.hlcloundposproject.utils.GetDeviceId;
import com.mall.hlcloundposproject.utils.SelledToServerUtils;
import com.mall.hlcloundposproject.utils.VolleyUtils;
import com.mall.hlcloundposproject.utils.VolleyUtils.VolleyCallback;

import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SplashActivity extends Activity implements Runnable,VolleyCallback{
	
	private MyOpenHelper userHelper;

	private MyOpenHelper goodsHelper;

	private static final int GET_USERS_DATA_AUTHORITY = 0;//获取  用户基本信息    标识
	
	private static final int GET_VIPGOODS_DATA_AUTHORITY = 1;//获取   VIP商品标识标识
	
	private static final int GET_SPECIALGOODS_DATA_AUTHORITY = 2;//获取   特价  商品标识
	
	private static final int GET_POS_ID = 20;  //获取    pos机id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		
		userHelper = new MyOpenHelper(SplashActivity.this,Content.USER_INFO_DB_NAME);
		goodsHelper = new MyOpenHelper(SplashActivity.this, Content.GOODS_DB_NAME);
		
		/**
		 * 获取     当前     手机唯一标识： 
		 */
		GetDeviceId getDeviceId = new GetDeviceId(this);
		String CombinedID = getDeviceId.getCombinedId();
		
		new Thread(this).start();
		
		//查询出所有     超出两天的    出售商品表单中的数据    并将已经上传到    服务器的数据删除
		initSellTableData();
		
		/**
		 * 根据    硬件标识    获取当前服务器返回数据标识：
		 */
		new VolleyUtils(this).getVolleyDataInfo(String.format(Configs.SERVER_BASE_URL+Configs.GET_POS_Id,CombinedID),
				this, GET_POS_ID);
		
	}

	private void initSellTableData() {
		goodsDb = goodsHelper.getReadableDatabase();
		goodsDb.beginTransaction();
		Cursor cursor = goodsDb.query("t_"+Content.TABLE_SELL_FORM, new String[]{"cSaleSheetNo","dSellTime","isUp"}, null,
				null, null, null, null);
		long currTime = System.currentTimeMillis();
		while(cursor.moveToNext()){
			long sellTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("dSellTime")));
			String isUp = cursor.getString(cursor.getColumnIndex("isUp"));
			if(((currTime-sellTime)/(60*60*1000)>48) && isUp.equals("1")){ //删除   当前    超过  24小时   并且已经上传到服务器的   商品数据
				goodsDb.delete("t_"+Content.TABLE_SELL_FORM, "dSellTime = '"+sellTime+"'", null);
			}
			if(isUp.equals("0")){//当前对象未发送至  服务器
				//获取当前对象的     销售单号
				String cSheet = cursor.getString(cursor.getColumnIndex("cSaleSheetNo"));
				//根据销售单号   将当前未销售对象发送至  服务器
				SelledToServerUtils.selledToServer(cSheet,goodsHelper,
						getSharedPreferences(Configs.APP_NAME, MODE_PRIVATE));
			}
		}
		goodsDb.setTransactionSuccessful();
		goodsDb.endTransaction();
	}

	@Override
	public void run() {
		try {
//			获取  结算  方式：,
			new VolleyUtils(this).getVolleyDataInfo(Configs.SERVER_BASE_URL+Configs.GET_PAY_CALCULATE_WAY,
					SplashActivity.this,Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY);
		
			
//			访问     服务器      获取所有用户信息     添加进      用户信息表
			new VolleyUtils(SplashActivity.this).getVolleyDataInfo(
					Configs.SERVER_BASE_URL+Configs.QUERY_ALL_USERS_DATA,
					SplashActivity.this,GET_USERS_DATA_AUTHORITY);
			
//			访问   服务器    获取     VIP  商品  信息：
			new VolleyUtils(SplashActivity.this).getVolleyDataInfo(
					Configs.SERVER_BASE_URL+Configs.QUERY_VIP_GOODS_DATA,
					SplashActivity.this,GET_VIPGOODS_DATA_AUTHORITY);
			
//			访问   服务器    获取    所有特价商品信息表：
			new VolleyUtils(SplashActivity.this).getVolleyDataInfo(
					Configs.SERVER_BASE_URL+Configs.QUERY_SPECIAL_GOODS,
					SplashActivity.this, GET_SPECIALGOODS_DATA_AUTHORITY);
			
			Thread.sleep(4000);
			
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private SQLiteDatabase userdb;
	private SQLiteDatabase goodsDb;
	/**
	 * volley  数据的回调：
	 */
	@Override
	public void volleyFinishedSuccess(final JSONArray array, int authority) {
		
		goodsDb = goodsHelper.getWritableDatabase();
		
		switch(authority){
		
			case GET_POS_ID:   //获取到  当前pos机标识
				
				JSONObject obj = array.getJSONObject(0);
				String serverPosId = obj.getString("posid");
				
				//获取  共享参数对象：
				SharedPreferences sp = getSharedPreferences(
						Configs.APP_NAME,MODE_PRIVATE);
				
				//获取  pos机Id  保存到  sp
				String localPosId = sp.getString(Configs.POS_ID, 
						"01");
				
				if(!localPosId.equals("serverPosId")){
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("posid", serverPosId);
					editor.commit();
				}
				
			break;
		
			case GET_USERS_DATA_AUTHORITY: //获取   用户 基本信息，存入到本地：
				
				new Thread(){

					public void run() {
						//创建线程   将数据  插入到数据表内：
						ArrayList<User> users = FastJsonUtils.getListFromArray(array,User.class);
						
						userdb = userHelper.getWritableDatabase();

						//清空当前表内所有信息：
						userdb.execSQL("delete from "+Content.TABLE_USERS_NAME);
						
						userdb.beginTransaction();
						for(User user :users){
							//插入信息  到  用户  数据表内：
							OperationDbTableUtils.insertUsersTable(userdb,user);
						}
						userdb.setTransactionSuccessful();
						userdb.endTransaction();
					};
				}.start();
				break;
				
			case GET_VIPGOODS_DATA_AUTHORITY://获取   vip用户  享有的特价商品的    信息  存到本地：
				
				new Thread(){

					public void run() {
						ArrayList<VIPGoods> vipGoods = FastJsonUtils.getListFromArray(array,VIPGoods.class);
						
						//清空当前表内所有信息：
						goodsDb.execSQL("delete from t_"+Content.TABLE_VIPGOODS_PRICE);
						
						goodsDb.beginTransaction();
						for(VIPGoods vipGood :vipGoods){
							//插入信息  到  用户  数据表内：
							OperationDbTableUtils.insertVipGoodsTable(goodsDb,vipGood);
						}
						goodsDb.setTransactionSuccessful();
						goodsDb.endTransaction();
					};
				}.start();
				break;
				
			case GET_SPECIALGOODS_DATA_AUTHORITY: //处理  特价商品信息：
				
				new Thread(){
					public void run() {
						ArrayList<SpecialGoods> spGoods = FastJsonUtils.getListFromArray(array,SpecialGoods.class);
						
						//清空当前表内所有信息：
						goodsDb.execSQL("delete from t_" + Content.TABLE_SPECIALPRICE);
						
						goodsDb.beginTransaction();
						for(SpecialGoods vipGood : spGoods){
							//插入信息  到  用户  数据表内：
							OperationDbTableUtils.insertSpecialGoodsToTable(goodsDb, vipGood,Content.TABLE_SPECIALPRICE);
						}
						goodsDb.setTransactionSuccessful();
						goodsDb.endTransaction();
					};
				}.start();
				break;
			
			case Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY:  //获取 结算方式；  保存到本地数据库：
				new Thread(){
					public void run() {
						ArrayList<JsType> jsTypes = FastJsonUtils.getListFromArray(array,JsType.class);
						
						//清空当前表内所有信息：
						goodsDb.execSQL("delete from t_" + Content.TABLE_JSTYPE_NAME);
						
						goodsDb.beginTransaction();
						
						for(JsType jstype : jsTypes){
							//插入信息  到  用户  数据表内：
							OperationDbTableUtils.insertTempJsType(goodsDb, jstype,Content.TABLE_JSTYPE_NAME);
						}
						goodsDb.setTransactionSuccessful();
						goodsDb.endTransaction();
					};
				}.start();
				
				break;
				
				default:break;
		}
	}

	@Override
	public void vollayFinishedFail(int authority) {
		switch(authority){
		
		case GET_USERS_DATA_AUTHORITY: //获取   用户 基本信息，存入到本地：
			
			//扉页  不处理  失败数据：   提示用户
			Toast.makeText(this, "当前未开启服务器", 1).show();
			
			break;
		}
	}
	
}
