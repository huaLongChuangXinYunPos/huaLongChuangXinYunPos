package com.mall.hlcloundposproject.db;

import com.mall.hlcloundposproject.Content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract.Constants;

public class MyOpenHelper extends SQLiteOpenHelper {
	
	private String name;

	public MyOpenHelper(Context context, String name) {
		super(context, name, null, 1);
		this.name= name;//获取数据库名称
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(name.equals(Content.GOODS_DB_NAME)){
			/**
			 * 创建    出售商品的      商品出售表：
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_SELL_FORM_ENTITY, Content.TABLE_SELL_FORM));
			
			/**
			 * 创建     商品基本信息表：
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_GOODS_ENTITY, Content.TABLE_FORMNALPRICE));
			/**
			 * 创建    特价商品信息表：
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_SPECTIAL_GOODS_ENTITY, Content.TABLE_SPECIALPRICE));
			/**
			 * 创建    VIP商品的信息表：
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_VIPGOODS_ENTITY,Content.TABLE_VIPGOODS_PRICE));
			/**
			 * 创建   结算类型表：
			 */
			db.execSQL(String.format(Content.CREATE_TEMP_JSTYPE_ENTITY,Content.TABLE_JSTYPE_NAME));
			
		}else if(name.equals(Content.USER_INFO_DB_NAME)){
			/**
			 * 创建   用户基本信息表：
			 */
			db.execSQL(Content.CREATE_TABLE_USER_ENTITY);
			/**
			 * 创建  消费者表
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_CONSUMER_ENTITY,Content.TABLR_CONSUMER_NAME));
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
