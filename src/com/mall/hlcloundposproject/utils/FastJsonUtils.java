package com.mall.hlcloundposproject.utils;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.hlcloundposproject.entity.Goods;

/**
 * 解析  JSON工具类
 * com.hlrj.hlcloundpos.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-22
 */
public final class FastJsonUtils {
	
	public static <T> ArrayList<T> getListFromArray(JSONArray array,Class<T> t){
		ArrayList<T> ret = null;
		if(array.size()!= 0){
			ret = (ArrayList<T>)
					JSON.parseArray(array.toString(),t);//oom..
		}
		return ret;
	}

	
	/**
	cGoodsNo;cUnitedNo;cGoodsName;	cBarcode;
	 cUnit;cSpec;fNormalPrice; fVipPrice;
	 fVipScore; fVipScore_base;fVipPrice_student;
	bWeight; bHidePrice; bHideQty; bNoVipPrice;
	 bUpdate;
	 */
	public static ArrayList<Goods> getGoodsList(JSONArray jsonArray) {
		
		ArrayList<Goods> goods = new ArrayList<Goods>();
		
		for(int i=0;i<jsonArray.size();i++){
			JSONObject obj = jsonArray.getJSONObject(i);
			Goods go = new Goods();
			go.setcGoodsNo(obj.getString("cGoodsNo"));//cGoodsNo
			go.setcUnitedNo(obj.getString("cUnitedNo"));//cUnitedNo
			go.setcGoodsName(obj.getString("cGoodsName"));//cGoodsName
			go.setcBarcode(obj.getString("cBarcode"));//cBarcode
			go.setcUnit(obj.getString("cUnit"));//cUnit
			go.setcSpec(obj.getString("cSpec"));//cSpec
			
			go.setfNormalPrice(obj.getFloatValue("fNormalPrice"));//fNormalPrice
			go.setfVipPrice(obj.getFloatValue("fVipPrice"));//fVipPrice
			go.setfVipScore(obj.getFloatValue("fVipScore"));//fVipScore
			go.setfVipScore_base(obj.getFloatValue("fVipScore_base"));//fVipScore_base
			go.setfVipPrice_student(obj.getFloatValue("fVipPrice_student"));//fVipPrice_student
			
			go.setbWeight(obj.getIntValue("bWeight"));//bWeight
			go.setbHidePrice(obj.getIntValue("bHidePrice"));//bHidePrice
			go.setbHideQty(obj.getIntValue("bHideQty"));//bHideQty
			go.setbNoVipPrice(obj.getIntValue("bNoVipPrice"));//bNoVipPrice
			go.setbUpdate(obj.getIntValue("bUpdate"));//bUpdate
			
			goods.add(go);
		}
		return goods;
	}
	
}
