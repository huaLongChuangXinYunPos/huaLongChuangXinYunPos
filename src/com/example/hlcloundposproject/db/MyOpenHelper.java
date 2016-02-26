package com.example.hlcloundposproject.db;

import com.example.hlcloundposproject.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
	
	private String name;

	public MyOpenHelper(Context context, String name) {
		super(context, name, null, 1);
		this.name= name;//��ȡ���ݿ�����
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(name.equals(Constants.GOODS_DB_NAME)){
			/**
			 * ����     ��Ʒ������Ϣ��
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_GOODS_ENTITY, Constants.TABLE_FORMNALPRICE));
			/**
			 * ����    �ؼ���Ʒ��Ϣ��
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_SPECTIAL_GOODS_ENTITY, Constants.TABLE_SPECIALPRICE));
			/**
			 * ����    VIP��Ʒ����Ϣ��
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_VIPGOODS_ENTITY,Constants.TABLE_VIPGOODS_PRICE));
			/**
			 * ����   �������ͱ�
			 */
			db.execSQL(String.format(Constants.CREATE_TEMP_JSTYPE_ENTITY,Constants.TABLE_JSTYPE_NAME));
			/**
			 * ����   ������Ʒ��     ��Ʒ���۱�
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_SELL_FORM_ENTITY, Constants.TABLE_SELL_FORM));
			
		}else if(name.equals(Constants.USER_INFO_DB_NAME)){
			/**
			 * ����   �û�������Ϣ��
			 */
			db.execSQL(Constants.CREATE_TABLE_USER_ENTITY);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
