package com.mall.hlcloundposproject.db;

import java.util.ArrayList;

import com.mall.hlcloundposproject.Content;
import com.mall.hlcloundposproject.entity.Consumer;
import com.mall.hlcloundposproject.entity.Goods;
import com.mall.hlcloundposproject.entity.JsType;
import com.mall.hlcloundposproject.entity.SpecialGoods;
import com.mall.hlcloundposproject.entity.User;
import com.mall.hlcloundposproject.entity.VIPGoods;
import com.mall.hlcloundposproject.utils.TimeUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * 操作  数据库表    的  工具类
 * com.hlrj.hlcloundpos.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-27
 */
public final class OperationDbTableUtils {

	/**
	 * 将数据插入到   特价商品表中：
	 * @param goodsDataDb
	 * @param goods
	 * @param tableSpecialprice
	 */
	public static void insertSpecialGoodsToTable(SQLiteDatabase goodsDataDb,
			SpecialGoods goods, String tableSpecialprice) {
		ContentValues values = new ContentValues();
		
		values.put("fVipValue", goods.getfVipValue());
		values.put("cPloyNo", goods.getcPloyNo());
		values.put("cGoodsNo", goods.getcGoodsNo());
		values.put("cGoodsName", goods.getcGoodsName());
		values.put("fQuantity_Ploy", goods.getfQuantity_Ploy());
		
		values.put("fPrice_SO", goods.getfPrice_SO());
		values.put("dDateStart", goods.getdDateStart());
		values.put("cTimeStart", goods.getcTimeStart());
		values.put("dDateEnd", goods.getdDateEnd());
		values.put("cTimeEnd", goods.getcTimeEnd());
		
		values.put("iPriority", goods.getiPriority());
		values.put("bSO", goods.getbSO());
		values.put("bPresent", goods.getbPresent());
		values.put("cPresentPloyNo", goods.getcPresentPloyNo());
		values.put("cPloyTypeNo", goods.getcPloyTypeNo());
		
		values.put("cPloyTypeName", goods.getcPloyTypeName());
		values.put("bEnabled", goods.getbEnabled());
		values.put("bLimitQty", goods.getbLimitQty());
		values.put("fLimitQty", goods.getfLimitQty());
		values.put("bJiOu", goods.getbJiOu());
		
		values.put("fQty_Ji", goods.getfQty_Ji());
		values.put("fQty_Ou", goods.getfQty_Ou());
		values.put("fPrice_ji", goods.getfPrice_ji());
		values.put("fPrice_Ou", goods.getfPrice_Ou());
		values.put("fRatio_JiOu", goods.getfRatio_JiOu());
		
		insertWeek(goods, values);
		insertHour(goods, values);
		
		goodsDataDb.insert("t_" + tableSpecialprice, null, values);
	}

	private static void insertHour(SpecialGoods goods, ContentValues values) {
		values.put("hour0", goods.getHour().getHour0());
		values.put("hour1", goods.getHour().getHour1());
		values.put("hour2", goods.getHour().getHour2());
		values.put("hour3", goods.getHour().getHour3());
		values.put("hour4", goods.getHour().getHour4());
		values.put("hour5", goods.getHour().getHour5());
		values.put("hour6", goods.getHour().getHour6());
		values.put("hour7", goods.getHour().getHour7());
		values.put("hour8", goods.getHour().getHour8());
		values.put("hour9", goods.getHour().getHour9());
		values.put("hour10", goods.getHour().getHour10());
		values.put("hour11", goods.getHour().getHour11());
		values.put("hour12", goods.getHour().getHour12());
		values.put("hour13", goods.getHour().getHour13());
		values.put("hour14", goods.getHour().getHour14());
		values.put("hour15", goods.getHour().getHour15());
		values.put("hour16", goods.getHour().getHour16());
		values.put("hour17", goods.getHour().getHour17());
		values.put("hour18", goods.getHour().getHour18());
		values.put("hour19", goods.getHour().getHour19());
		values.put("hour21", goods.getHour().getHour20());
		values.put("hour20", goods.getHour().getHour21());
		values.put("hour22", goods.getHour().getHour22());
		values.put("hour23", goods.getHour().getHour23());
	}

	private static void insertWeek(SpecialGoods goods, ContentValues values) {
		values.put("week1", goods.getWeek().getWeek1());
		values.put("week2", goods.getWeek().getWeek2());
		values.put("week3", goods.getWeek().getWeek3());
		values.put("week4", goods.getWeek().getWeek4());
		values.put("week5", goods.getWeek().getWeek5());
		values.put("week6", goods.getWeek().getWeek6());
		values.put("week7", goods.getWeek().getWeek7());
	}

	/**
	 * 插入  数据到     商品基本信息表：
	 * @param goodsDataDb
	 * @param goods
	 * @param tableFnormalprice
	 */
	public static void insertGoodsToTable(SQLiteDatabase goodsDataDb,
			Goods goods, String tableFnormalprice) {
		ContentValues values = new ContentValues();
		values.put("cGoodsNo", goods.getcGoodsNo());
		values.put("cUnitedNo", goods.getcUnitedNo());
		values.put("cGoodsName", goods.getcGoodsName());
		values.put("cBarcode", goods.getcBarcode());
		values.put("cUnit", goods.getcUnit());
		
		values.put("cSpec", goods.getcSpec());
		values.put("fNormalPrice", goods.getfNormalPrice());
		values.put("fVipPrice", goods.getfVipPrice());
		values.put("fVipScore", goods.getfVipScore());
		values.put("bWeight", goods.getbWeight());
		
		values.put("fVipScore_base", goods.getfVipScore_base());
		values.put("fVipPrice_student", goods.getfVipPrice_student());
		
		values.put("bHidePrice", goods.getbHidePrice());
		values.put("bHideQty", goods.getbHideQty());
		values.put("bNoVipPrice", goods.getbNoVipPrice());
		values.put("bUpdate", goods.getbUpdate());
		
		goodsDataDb.insert("t_"+ tableFnormalprice, null, values);
	}

	/**
	 * 从商品基本信息表     中  查找    商品    根据条码:
	 * @param codrBar
	 * @param goodsDataHelper
	 * @return
	 */
	public static Cursor selectDataFromLocal(String codrBar,
			MyOpenHelper goodsDataHelper) {
		SQLiteDatabase goodsDataDb = goodsDataHelper.getReadableDatabase();
		Cursor cursor = goodsDataDb.query("t_"+Content.TABLE_FORMNALPRICE, new String[]{"*"}, 
					" cBarcode = '"+codrBar+"'", null, null, null, null);
		return cursor;
	}

	/**
	 * 将  查询到的   基本商品信息   封装成实体类：
	 * @param cursor
	 * @return
	 */
	public static Goods goodsCursorToEntity(Cursor cursor) {
		Goods goods = null;
		//遍历游标集合
		while(cursor.moveToNext()){
		    goods = new Goods();
			goods.setId(cursor.getInt(cursor.getColumnIndex("id")));
			goods.setcGoodsNo(cursor.getString(cursor.getColumnIndex("cGoodsNo")));
			goods.setcUnitedNo(cursor.getString(cursor.getColumnIndex("cUnitedNo")));
			goods.setcGoodsName(cursor.getString(cursor.getColumnIndex("cGoodsName")));
			goods.setcBarcode(cursor.getString(cursor.getColumnIndex("cBarcode")));
			
			goods.setcUnit(cursor.getString(cursor.getColumnIndex("cUnit")));
			goods.setcSpec(cursor.getString(cursor.getColumnIndex("cSpec")));
			goods.setfNormalPrice(cursor.getFloat(cursor.getColumnIndex("fNormalPrice")));
			goods.setfVipPrice(cursor.getFloat(cursor.getColumnIndex("fVipPrice")));
			goods.setfVipScore(cursor.getFloat(cursor.getColumnIndex("fVipScore")));
			goods.setfVipScore_base(cursor.getFloat(cursor.getColumnIndex("fVipScore_base")));
			
			goods.setfVipPrice_student(cursor.getFloat(cursor.getColumnIndex("fVipPrice_student")));
			goods.setbWeight(cursor.getInt(cursor.getColumnIndex("bWeight")));
			goods.setbHidePrice(cursor.getInt(cursor.getColumnIndex("bHidePrice")));
			goods.setbHideQty(cursor.getInt(cursor.getColumnIndex("bHideQty")));
			goods.setbNoVipPrice(cursor.getInt(cursor.getColumnIndex("bNoVipPrice")));
			
			goods.setbUpdate(cursor.getInt(cursor.getColumnIndex("bUpdate")));
			
			goods.setAmount(1);//设置默认  数量和    金额为当前价格
			goods.setPayMoney(cursor.getDouble(cursor.getColumnIndex("fNormalPrice")));
		}
		return goods;
	}
	
	/**
	 * 将  查询到的   基本商品信息   封装成实体类：
	 * @param cursor
	 * @return
	 */
	public static ArrayList<Goods> goodsCursorToList(Cursor cursor,String limit) {
		ArrayList<Goods> list = new ArrayList<Goods>();
		Goods goods = null;
		int i = 0;
		//遍历游标集合
		while(cursor.moveToNext()&&i<50){
		    goods = new Goods();
			goods.setId(cursor.getInt(cursor.getColumnIndex("id")));
			goods.setcGoodsNo(cursor.getString(cursor.getColumnIndex("cGoodsNo")));
			goods.setcUnitedNo(cursor.getString(cursor.getColumnIndex("cUnitedNo")));
			goods.setcGoodsName(cursor.getString(cursor.getColumnIndex("cGoodsName")));
			goods.setcBarcode(cursor.getString(cursor.getColumnIndex("cBarcode")));
			
			goods.setcUnit(cursor.getString(cursor.getColumnIndex("cUnit")));
			goods.setcSpec(cursor.getString(cursor.getColumnIndex("cSpec")));
			goods.setfNormalPrice(cursor.getFloat(cursor.getColumnIndex("fNormalPrice")));
			goods.setfVipPrice(cursor.getFloat(cursor.getColumnIndex("fVipPrice")));
			goods.setfVipScore(cursor.getFloat(cursor.getColumnIndex("fVipScore")));
			goods.setfVipScore_base(cursor.getFloat(cursor.getColumnIndex("fVipScore_base")));
			
			goods.setfVipPrice_student(cursor.getFloat(cursor.getColumnIndex("fVipPrice_student")));
			goods.setbWeight(cursor.getInt(cursor.getColumnIndex("bWeight")));
			goods.setbHidePrice(cursor.getInt(cursor.getColumnIndex("bHidePrice")));
			goods.setbHideQty(cursor.getInt(cursor.getColumnIndex("bHideQty")));
			goods.setbNoVipPrice(cursor.getInt(cursor.getColumnIndex("bNoVipPrice")));
			
			goods.setbUpdate(cursor.getInt(cursor.getColumnIndex("bUpdate")));
			
			goods.setAmount(1);//设置默认  数量和    金额为当前价格
			goods.setPayMoney(cursor.getDouble(cursor.getColumnIndex("fNormalPrice")));
			
			list.add(goods);
			
			if(limit.equals("") || limit==null){
				list.clear();
			}
			i++;//限制list中数据大小
		}
		return list;
	}

	/**
	 * 插入信息  到  用户表内：
	 * @param userdb
	 * @param user
	 */
	public static void insertUsersTable(SQLiteDatabase userdb, User user) {
		//存到本地：
		ContentValues values = new ContentValues();
		
		values.put("ki", user.getKi());
		values.put("name", user.getName());
		values.put("password", user.getPass());
		values.put("quanxian", user.getQuanxian());
		values.put("right", user.getRight());
		values.put("user",user.getUser());
					
		userdb.insert(Content.TABLE_USERS_NAME, null, values);
	}

	/**
	 * 插入  数据  到 vip商品信息表内：
	 * @param goodsDb
	 * @param vipGood
	 * cSheetno text,iLineNo text,cGoodsNo text,cBarcode text,fVipPrice text,fVipPrice_student text," +
	 "week1 boolean,week2 boolean,week3 boolean,week4 boolean,week5 boolean,week6 boolean,week7 boolean," +
	 "hour0 boolean,hour1 boolean,hour2 boolean,hour3 boolean,hour4 boolean,hour5 boolean,hour6 boolean,hour7 boolean,hour8 boolean," +
	 "hour9 boolean,hour10 boolean,hour11 boolean,hour12 boolean,hour13 boolean,hour14 boolean,hour15 boolean,hour16 boolean,hour17 boolean,hour18 boolean," +
	 "hour19 boolean,hour20 boolean,hour21 boolean,hour22 boolean,hour23 boolean)
	 */
	public static void insertVipGoodsTable(SQLiteDatabase goodsDb,
			VIPGoods vipGood) {
		
		ContentValues values = new ContentValues();
		values.put("cSheetno", vipGood.getcSheetno());
		values.put("iLineNo", vipGood.getiLineNo());
		values.put("cGoodsNo", vipGood.getcGoodsNo());
		values.put("cBarcode", vipGood.getcBarcode());
		values.put("fVipPrice", vipGood.getfVipPrice());
		values.put("fVipPrice_student", vipGood.getfVipPrice_student());
		
		insertVipGoodsWeek(vipGood, values);
		insertSpecialHour(vipGood, values);
		
		goodsDb.insert("t_"+Content.TABLE_VIPGOODS_PRICE, null, values);
		
	}

	private static void insertSpecialHour(VIPGoods vipGood, ContentValues values) {
		values.put("hour0", vipGood.getHour().getHour0());
		values.put("hour1", vipGood.getHour().getHour1());
		values.put("hour2", vipGood.getHour().getHour2());
		values.put("hour3", vipGood.getHour().getHour3());
		values.put("hour4", vipGood.getHour().getHour4());
		values.put("hour5", vipGood.getHour().getHour5());
		values.put("hour6", vipGood.getHour().getHour6());
		values.put("hour7", vipGood.getHour().getHour7());
		values.put("hour8", vipGood.getHour().getHour8());
		values.put("hour9", vipGood.getHour().getHour9());
		values.put("hour10", vipGood.getHour().getHour10());
		values.put("hour11", vipGood.getHour().getHour11());
		values.put("hour12", vipGood.getHour().getHour12());
		values.put("hour13", vipGood.getHour().getHour13());
		values.put("hour14", vipGood.getHour().getHour14());
		values.put("hour15", vipGood.getHour().getHour15());
		values.put("hour16", vipGood.getHour().getHour16());
		values.put("hour17", vipGood.getHour().getHour17());
		values.put("hour18", vipGood.getHour().getHour18());
		values.put("hour19", vipGood.getHour().getHour19());
		values.put("hour21", vipGood.getHour().getHour20());
		values.put("hour20", vipGood.getHour().getHour21());
		values.put("hour22", vipGood.getHour().getHour22());
		values.put("hour23", vipGood.getHour().getHour23());
	}

	private static void insertVipGoodsWeek(VIPGoods vipGood,
			ContentValues values) {
		values.put("week1", vipGood.getWeek().getWeek1());
		values.put("week2", vipGood.getWeek().getWeek2());
		values.put("week3", vipGood.getWeek().getWeek3());
		values.put("week4", vipGood.getWeek().getWeek4());
		values.put("week5", vipGood.getWeek().getWeek5());
		values.put("week6", vipGood.getWeek().getWeek6());
		values.put("week7", vipGood.getWeek().getWeek7());
	}
	
	public static void insertTempJsType(SQLiteDatabase tempDb, JsType jstype,
			String tableJstypeName) {
		//存到本地：
		ContentValues values = new ContentValues();
		
		values.put("jstype", jstype.getJsType());
		values.put("mianzhi", jstype.getMianzhi());
		values.put("shishou", jstype.getShishou());
		values.put("zhaoling", jstype.getZhaoling());
		values.put("zhekou",jstype.getZhekou());
		values.put("detail", jstype.getDetail());
					
		tempDb.insert("t_"+Content.TABLE_JSTYPE_NAME, null, values);
	}

	public static void sellGoodsInsertTable(SQLiteDatabase goodsDataDb,String[] payStrs, Goods goods
			,boolean isVip,String cVipNo,String vipScore,
			User user,String sheetNo,String jsType,
			String totalMoney,String shouldPayMoney) {
		
		long time = System.currentTimeMillis();
		
		Cursor spGoodsCursor = getSpGoodsCursor(goodsDataDb, TimeUtils.getSystemNowTime("yyyy-MM-dd"), goods);
		Cursor vipGoodsCursor = getVipCursor(goodsDataDb,goods);
		
		ContentValues values = new ContentValues();
		//获取   商品号：
		values.put("cGoodsNo", goods.getcGoodsNo());
		values.put("cBarCode", goods.getcBarcode());
		values.put("sellAmount", goods.getAmount());
		values.put("fNormalPrice", goods.getfNormalPrice());
		values.put("goodsMoney", goods.getPayMoney());  //该商品应该支付的    金额
		values.put("shouldMoney",jsType.equals("人民币")?shouldPayMoney:totalMoney);  //总共应该支付的     金额
		values.put("payMoney",jsType.equals("人民币")?payStrs[1]:totalMoney);  //实际支付的金额
		values.put("overPlus",jsType.equals("人民币")?payStrs[2]:"0.00");//剩余金额：
		
		if(spGoodsCursor.moveToFirst()){//当前为    特价商品
			float spPrice = Float.parseFloat(spGoodsCursor
					.getString(spGoodsCursor
							.getColumnIndex("fPrice_SO")));
			if(vipGoodsCursor.moveToFirst()){//当前     是vip商品
				float vipPrice = Float.parseFloat(vipGoodsCursor
						.getString(vipGoodsCursor
								.getColumnIndex("fVipPrice")));
				if(isVip){ //  vip用户
					values.put("executePrice",vipPrice<spPrice?vipPrice:spPrice);//执行
				}else{
					values.put("executePrice",spPrice);//执行特价
				}
			}else{
				values.put("executePrice",spPrice);//执行特价
			}
		}else{
			values.put("executePrice",goods.getfNormalPrice());//执行正常商品的  价格
		}
		/**
			integer primary key autoincrement,cGoodsNo text,cBarCode text,sellAmount text,fNormalPrice numeric(10,4)," +
			"goodsMoney numeric(10,4),shouldMoney numeric(10,4),payMoney numeric(10,4),overPlus numeric(10,4),"+
			"executePrice numeric(10,4),isSpeG text,spPrice numeric(10,4),isVip text,vipPrice numeric(10,4),vipScore numeric(10,4)," +
			"vipCardNo text,dSellTime text,jsType text,cOperationName text,cOperationNo text,cSaleSheetNo text,isUp text,exactlyTime text)";
		 */
		
		values.put("isSpeG", spGoodsCursor.moveToFirst()?1:0);
		values.put("spPrice",spGoodsCursor.moveToFirst()?spGoodsCursor
				.getString(spGoodsCursor.getColumnIndex("fPrice_SO")) : "0.0000");//无价格

		values.put("isVip", isVip ? 1:0);
		values.put("vipPrice",vipGoodsCursor.moveToFirst()? vipGoodsCursor
				.getString(vipGoodsCursor.getColumnIndex("fVipPrice")) : "0.0000");
		
		values.put("vipScore", isVip?vipScore:"0.0000");
		
		values.put("vipCardNo", isVip?cVipNo:"0");
		values.put("dSellTime", time);   //当前系统毫秒值
		values.put("jsType", jsType);
		values.put("cOperationName", user.getName());  //操作员姓名：
		values.put("cOperationNo", user.getUser());//保存销售员号
		values.put("cSaleSheetNo",sheetNo);  //商品销售单号:  02 20160226 0032
		values.put("isUp", 0);
		values.put("exactlyTime", TimeUtils.getSystemNowTime("yyyy-MM-dd HH:mm:ss"));
		
		goodsDataDb.insert("t_" + Content.TABLE_SELL_FORM, null, values);
	}
	
	/**
	 * 查询 vip特价商品信息：
	 */
	public static Cursor getVipCursor(SQLiteDatabase goodsDataDb,Goods item) {
		Cursor vipGoodsCursor = null;
		vipGoodsCursor = goodsDataDb.rawQuery(
				"select * from t_" + Content.TABLE_VIPGOODS_PRICE
						+ " where cBarCode = '" + item.getcBarcode() + "'",
				null);
		return vipGoodsCursor;
	}

	/**
	 * 到 本地商品 特价 商品中查询 是否有此特价 商品信息：
	 */
	public static Cursor getSpGoodsCursor(SQLiteDatabase goodsDataDb,String nowTime, Goods item) {
		Cursor spGoodsCursor = null;
		spGoodsCursor = goodsDataDb.rawQuery(
				"select b.* from t_" + Content.TABLE_FORMNALPRICE + " a,"
						+ "t_" + Content.TABLE_SPECIALPRICE + " b "
						+ "where a.cGoodsNo = b.cGoodsNo and a.[cBarcode] = '"
						+ item.getcBarcode() + "' " + "and '" + nowTime
						+ "' between b.[dDateStart]" + " and b.[dDateEnd]",
				null);
		return spGoodsCursor;
	}

	//将     销售出来的      表单信息      转换成      商品基本信息
	public static Goods sellGoodsToGoodsEntity(Cursor cursor,Goods goods) {
		
		//遍历游标集合
		goods.setcBarcode(cursor.getString(cursor.getColumnIndex("cBarCode")));
		goods.setAmount(Float.parseFloat(cursor.getString(cursor.getColumnIndex("sellAmount"))));//设置默认  数量和    金额为当前价格
		goods.setfNormalPrice(cursor.getFloat(cursor.getColumnIndex("fNormalPrice")));
		goods.setPayMoney(cursor.getDouble(cursor.getColumnIndex("goodsMoney")));

		return goods;
	}

	public static Consumer consumerCursorToEntity(Cursor cursor) {
		Consumer consumer = new Consumer();
		
		consumer.setName(cursor.getString(cursor.getColumnIndex("name")));
		consumer.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
		consumer.setAddress(cursor.getString(cursor.getColumnIndex("address")));
		consumer.setSex(cursor.getString(cursor.getColumnIndex("sex")));
		
		return consumer;
	};

}
