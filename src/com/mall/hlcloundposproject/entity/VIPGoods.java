package com.mall.hlcloundposproject.entity;

/**
 * vip 商品信息表：
 * com.hlrj.hlcloundpos.entity
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-27
 */
public final class VIPGoods {
	
	/**
	 * {"resultStatus":1,
	 * "data":[{"cSheetno":"VIP2015-000010","iLineNo":"1",
	 * "cGoodsNo":"214554","cBarcode":"6956367338680","fVipPrice":"42.0000",
	 * "fVipPrice_student":"42.0000",
	 * 
	 * "week":{"week1":"False","week2":"False","week3":"False","week4":"False","week5":"False","week6":"False","week7":"False"},
	 * "hour":{"hour0":"False","hour1":"False","hour2":"False","hour3":"False","hour4":"False","hour5":"False","hour6":"False","hour7":"False","hour8":"False","hour9":"False","hour10":"False","hour11":"False","hour12":"False","hour13":"False","hour14":"False","hour15":"False","hour16":"False","hour17":"False","hour18":"False","hour19":"False","hour20":"False","hour21":"False","hour22":"False","hour23":"False"},
	 * 
	 * "bLimitQty":"","fLimitQty":"2.0000","fShiDuanQty":"42.0000","fVipValue":"42.0000"}]}
	 */
	
	private String cSheetno;
	private String iLineNo;
	private String cGoodsNo;
	private String cBarcode;
	private String fVipPrice;
	
	private String fVipPrice_student;
	private String bLimitQty;
	private String fLimitQty;
	private String fShiDuanQty;
	private String fVipValue;
	
	private Week week;
	
	private Hour hour;

	public String getcSheetno() {
		return cSheetno;
	}

	public void setcSheetno(String cSheetno) {
		this.cSheetno = cSheetno;
	}

	public String getiLineNo() {
		return iLineNo;
	}

	public void setiLineNo(String iLineNo) {
		this.iLineNo = iLineNo;
	}

	public String getcGoodsNo() {
		return cGoodsNo;
	}

	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
	}

	public String getcBarcode() {
		return cBarcode;
	}

	public void setcBarcode(String cBarcode) {
		this.cBarcode = cBarcode;
	}

	public String getfVipPrice() {
		return fVipPrice;
	}

	public void setfVipPrice(String fVipPrice) {
		this.fVipPrice = fVipPrice;
	}

	public String getfVipPrice_student() {
		return fVipPrice_student;
	}

	public void setfVipPrice_student(String fVipPrice_student) {
		this.fVipPrice_student = fVipPrice_student;
	}

	public String getbLimitQty() {
		return bLimitQty;
	}

	public void setbLimitQty(String bLimitQty) {
		this.bLimitQty = bLimitQty;
	}

	public String getfLimitQty() {
		return fLimitQty;
	}

	public void setfLimitQty(String fLimitQty) {
		this.fLimitQty = fLimitQty;
	}

	public String getfShiDuanQty() {
		return fShiDuanQty;
	}

	public void setfShiDuanQty(String fShiDuanQty) {
		this.fShiDuanQty = fShiDuanQty;
	}

	public String getfVipValue() {
		return fVipValue;
	}

	public void setfVipValue(String fVipValue) {
		this.fVipValue = fVipValue;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public Hour getHour() {
		return hour;
	}

	public void setHour(Hour hour) {
		this.hour = hour;
	}

	@Override
	public String toString() {
		return "VIPGoods [cSheetno=" + cSheetno + ", iLineNo=" + iLineNo
				+ ", cGoodsNo=" + cGoodsNo + ", cBarcode=" + cBarcode
				+ ", fVipPrice=" + fVipPrice + ", fVipPrice_student="
				+ fVipPrice_student + ", bLimitQty=" + bLimitQty
				+ ", fLimitQty=" + fLimitQty + ", fShiDuanQty=" + fShiDuanQty
				+ ", fVipValue=" + fVipValue + ", week=" + week + ", hour="
				+ hour + "]";
	}
	
}
