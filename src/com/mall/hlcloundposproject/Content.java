package com.mall.hlcloundposproject;

//数据库操作的     字符串封装类：
public final class Content {
	
	/**
	 * 定义         访问          操作 数据库的 provider 的标识：
	 */
	public static final String PROVIDER_AUTHORITY = "com.hlrj.hlcloundpos.userdatadb.t_users";
	
	/**
	 * 定义   数据  商品信息的     基本库名称
	 */
	public static final String GOODS_DB_NAME = "goodsDataDb";
	
	/**
	 * 存放  商品基本信息
	 */
	public static final String TABLE_FORMNALPRICE = "goods";
	
	/**
	 * 存放   特殊商品信息
	 */
	public static final String TABLE_SPECIALPRICE = "spGoods";
	
	/**
	 * 保存用户基本信息的    数据库名称
	 */
	public static final String USER_INFO_DB_NAME = "usersDataDb";
	
	/**
	 * 保存所有 用户名的    users表名
	 */
	public static final String TABLE_USERS_NAME = "t_users";
	
	/**
	 * 定义  挂单 取单的数据库      名称
	 */
	public static final String TEMP_DATA_DB = "tempDataDb";
	
	/**
	 * 创建    temp中挂单   取单     的表数据信息 t_12_20_11
	 */
	public static final String CREATE_TABLE_TEMP_ENTITY = "create table if not exists t_%s(id integer primary key autoincrement," +
			"cBarcode text,amount numeric(10,2),payMoney numeric(10,4))";
	
	/**
	 * 插入  temp 表内数据  t_12_20_11
	 */
	public static final String INSERT_TABLE_TEMP = "insert into t_%s(cBarcode,amount,payMoney) " +
			"values('%s', %f, %f)";
	
	/**
	 * 创建   数据库表 t_users  保存用户信息
	 */
	public static final String CREATE_TABLE_USER_ENTITY = "create table if not exists t_users" +
			"(id integer primary key autoincrement,user text,password text,name text," +
			"right text,ki text,quanXian text)";
	
	/**
	 * 查询  数据表是否存在，若不存在    直接创建：：
	 * 表名为  t_区间   t_6902
	 * cGoodsNo,cUnitedNo,cGoodsName,cBarcode,cUnit,
	 * cSpec,fNormalPrice,fVipPrice,fVipScore ,bWeight,
	 * fVipScore_base,fVipPrice_student,bHidePrice,bHideQty,bNoVipPrice,
	 * bUpdate
	 */
	public static final String CREATE_TABLE_GOODS_ENTITY = "create table if not exists t_%s" + 
	 "(id integer primary key autoincrement,cGoodsNo text,cUnitedNo text,cGoodsName text," +
	 "cBarcode text,cUnit text,cSpec text,fNormalPrice numeric(10,4),fVipPrice numeric(10,4)," +
	 "fVipScore numeric(10,4),bWeight text,fVipScore_base numeric(10,4),fVipPrice_student numeric(10,4)," +
	 "bHidePrice text,bHideQty text,bNoVipPrice text,bUpdate text)";
	
	/**
	 * 创建   特价商品信息表：
	 */
	public static final String CREATE_TABLE_SPECTIAL_GOODS_ENTITY = "create table if not exists t_%s" + 
			 "(id integer primary key autoincrement,fVipValue text,cPloyNo text,cGoodsNo text,cGoodsName text," +
			 "fQuantity_Ploy text,fPrice_SO text,dDateStart text,cTimeStart text,dDateEnd text," +
			 "cTimeEnd text,iPriority text,bSO text,bPresent text,cPresentPloyNo text," +
			 "cPloyTypeNo text,cPloyTypeName text,bEnabled text,bLimitQty text,fLimitQty text," +
			 "bJiOu text,fQty_Ji text,fPrice_ji text,fQty_Ou text,fPrice_Ou text,fRatio_JiOu text," +
			 "week1 text,week2 text,week3 text,week4 text,week5 text,week6 text,week7 text," +
			 "hour0 text,hour1 text,hour2 text,hour3 text,hour4 text,hour5 text,hour6 text,hour7 text,hour8 text," +
			 "hour9 text,hour10 text,hour11 text,hour12 text,hour13 text,hour14 text,hour15 text,hour16 text,hour17 text,hour18 text," +
			 "hour19 text,hour20 text,hour21 text,hour22 text,hour23 text)";
	
	/**
	 * 创建    VIP商品信息表名：
	 */
	public static final String TABLE_VIPGOODS_PRICE = "vipGoods";

	
	/**
	 * 创建     VIP商品信息表：
	 */
	public static final String CREATE_TABLE_VIPGOODS_ENTITY = "create table if not exists t_%s" + 
	 "(id integer primary key autoincrement,cSheetno text,iLineNo text,cGoodsNo text,cBarcode text,fVipPrice text,fVipPrice_student text," +
	 "week1 text,week2 text,week3 text,week4 text,week5 text,week6 text,week7 text," +
	 "hour0 text,hour1 text,hour2 text,hour3 text,hour4 text,hour5 text,hour6 text,hour7 text,hour8 text," +
	 "hour9 text,hour10 text,hour11 text,hour12 text,hour13 text,hour14 text,hour15 text,hour16 text,hour17 text,hour18 text," +
	 "hour19 text,hour20 text,hour21 text,hour22 text,hour23 text)";
	
	/**
	 * 保存  结算类型的 表：
	 */
	public static final String TABLE_JSTYPE_NAME = "jsType";

	/**
	 * 创建  结算类型  表：
	 */
	public static final String CREATE_TEMP_JSTYPE_ENTITY = "create table if not exists t_%s" +
			"(id integer primary key autoincrement,jstype text,detail text,mianzhi numeric(10,4),zhekou numeric(10,4)," +
			"shishou numeric(10,4),zhaoling numeric(10,4))";
	
	/**
	 * 记录当前销售情况的  销售表：
	 */
	public static final String TABLE_SELL_FORM = "sell";
	
	/**
	 * 创建   销售情况的销售表：
	 */
	public static final String CREATE_TABLE_SELL_FORM_ENTITY = "create table if not exists t_%s" +
			"(id integer primary key autoincrement,cGoodsNo text,cBarCode text,sellAmount text,fNormalPrice numeric(10,4)," +
			"goodsMoney numeri  c(10,4),shouldMoney numeric(10,4),payMoney numeric(10,4),overPlus numeric(10,4),"+
			"executePrice numeric(10,4),isSpeG text,spPrice numeric(10,4),isVip text,vipPrice numeric(10,4),vipScore numeric(10,4)," +
			"vipCardNo text,dSellTime text,jsType text,cOperationName text,cOperationNo text,cSaleSheetNo text,isUp text,exactlyTime text)";
	
	
	/**
	 * 消费者表信息
	 */
	public static final String TABLR_CONSUMER_NAME = "consumer";
	/**
	 * 创建   消费者表
	 */
	public static final String CREATE_TABLE_CONSUMER_ENTITY = "create table if not exists t_%s" +
			"(id integer primary key autoincrement,name text,phone text,address text,sex text)";
	
	
	public static final String CHAR_SET = "GBK";  //编码集
	
	//商户加密  私钥
	public static final String PRIVATE_KEY = "9c1Ick1pYX50YB3GOcWNsZCEi9m0XL9S";//商户私钥
	
	//商户pid  支付宝签约商户    会提供
	public static final String SHOP_PID ="3088677747502334";
	
	//服务器    地址
	public static final String SERVER_IP = "121.199.41.235";
	
	//服务器   端口：
	public static final int SERVER_PORT = 6666;//连接端口号
}
