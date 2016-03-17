package com.example.hlcloundposproject.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.hlcloundposproject.Configs;
import com.example.hlcloundposproject.Content;
import com.example.hlcloundposproject.db.MyOpenHelper;
import com.example.hlcloundposproject.entity.User;

/**
 * com.example.hlcloundposproject.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-3-10
 */
public final class SelledToServerUtils {
	
	/**
	 * 将销售过得   商品信息  发送至服务器    根据当前单号   获取商品信息
	 */
	public static void selledToServer(final String saleSheetNo,MyOpenHelper goodsDataHelper,
			SharedPreferences sp) {
		//遍历当前   已销售商品信息
		final SQLiteDatabase goodsDataDb = goodsDataHelper.getReadableDatabase();
		Cursor cursor  = goodsDataDb.query("t_"+Content.TABLE_SELL_FORM,
				new String[]{"*"}, " cSaleSheetNo='"+saleSheetNo+"'", 
				null, null, null, null);

		//遍历数据
		ArrayList<String> jsonList = new ArrayList<String>(); 
		int i=0;
		while(cursor.moveToNext()){
			i++;
			JSONObject obj = new JSONObject();
			obj.put("id", i+"");  // "id": 1,
			obj.put("posId", sp.getString(Configs.POS_ID, "01")); //"posId": "01",
			obj.put("jsType", cursor.getString(cursor.getColumnIndex("jsType"))); // "jsType": "支付宝",  "人民币"

			obj.put("cGoodsNo", cursor.getString(cursor.getColumnIndex("cGoodsNo"))); //"cGoodsNo": "002",
			obj.put("cBarCode", cursor.getString(cursor.getColumnIndex("cBarCode")));//"cBarCode": "020323232323",
			obj.put("sellAmount", cursor.getString(cursor.getColumnIndex("sellAmount")));//"sellAmount": "2",
			obj.put("fNormalPrice", cursor.getString(cursor.getColumnIndex("fNormalPrice")));// "fNormalPrice": "3.0",
			obj.put("goodsMoney", cursor.getString(cursor.getColumnIndex("goodsMoney")));//  "goodsMoney": "0.6",
			obj.put("shouldMoney", cursor.getString(cursor.getColumnIndex("shouldMoney")));//   "shouleMoney": "1.5",
			obj.put("payMoney", cursor.getString(cursor.getColumnIndex("payMoney")));// "payMoney": "100",
			obj.put("overPlus", cursor.getString(cursor.getColumnIndex("overPlus")));// "overPlus": "98.5",
			obj.put("executePrice", cursor.getString(cursor.getColumnIndex("executePrice")));// "executePrice": "0.2",
			
			obj.put("isSpeG",cursor.getString(cursor.getColumnIndex("isSpeG")));   //--- "isSpecG": 0,
			obj.put("spPrice", cursor.getString(cursor.getColumnIndex("spPrice")));//---	“spPrice”:””,
			
			obj.put("isVip", cursor.getString(cursor.getColumnIndex("isVip")));  //  "isVip": 0,
			obj.put("vipPrice", cursor.getString(cursor.getColumnIndex("vipPrice")));//"vipPrice": "0.0000",
			obj.put("vipScore", cursor.getString(cursor.getColumnIndex("vipScore")));//----- "vipScore": "0.0000",
			obj.put("vipCardNo", cursor.getString(cursor.getColumnIndex("vipCardNo")));//"vipCardNo": "",
			obj.put("cOperationName", cursor.getString(cursor.getColumnIndex("cOperationName")));// "cOperationName": "陈店",
			obj.put("exactlyTime", cursor.getString(cursor.getColumnIndex("exactlyTime")));//   "exectlyTime": "2016-03-03/14:31:53",
			
			obj.put("cOperationNo", cursor.getString(cursor.getColumnIndex("cOperationNo")));//"cOperationNo": "232312123123",
			obj.put("cSaleSheetNo", saleSheetNo);//“cSaleSheetNo”:”231212121212”
				
			jsonList.add(JSON.toJSONString(obj));
		}
			final HashMap<String,String> map = new HashMap<String,String>();
			map.put("name", jsonList.toString());
			//将数据  发送至服务器：
			new Thread(new Runnable() {
				@Override
				public void run() {
					String result = HttpTools.doPost(Configs.SELLED_DATA_TO_SERVER,map);
					if(result!=null){
						JSONObject obj = JSON.parseObject(result);
						String status = obj.getString("resultStatus");
						if(status.equals("1")){
							ContentValues values = new ContentValues(1);
							values.put("isUp", 1);
							//上传成功     修改   isUp为 1
							goodsDataDb.update("t_"+Content.TABLE_SELL_FORM,
									values, " cSaleSheetNo = ?", 
									new String[]{saleSheetNo});
						}
					}
				}
			}).start();
	}

}
