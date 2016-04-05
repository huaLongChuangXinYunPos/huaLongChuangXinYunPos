package com.mall.hlcloundposproject.entity;


/**
 * 特殊商品的实体类
 * com.hlrj.hlcloundpos.entity
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-25
 */
//建议加上注解， 混淆后表名不受影响
public final class SpecialGoods {
	/**
	 * {"resultStatus":1,
	 * "data":[ 
	 * 			{"fVipValue":"0.0000","cPloyNo":"20151020联营","cGoodsNo":"41192",
	 * 			"cGoodsName":"460ml依风乳液滋养焗油护发素","fQuantity_Ploy":"1.0000",
	 * 			"fPrice_SO":"19.9000","dDateStart":"2016-01-01","cTimeStart":"13:52:10 ",
	 * 			"dDateEnd":"2016-02-20","cTimeEnd":"13:52:10 ","iPriority":"1","bSO":"1",
	 * 			"bPresent":"0","cPresentPloyNo":"","cPloyTypeNo":"8004","cPloyTypeName":"海报特价",
	 * 			"bEnabled":"1","bLimitQty":"0","fLimitQty":"",
	 * 
	 * 			"week":{"week1":1,"week2":1,"week3":1,"week4":1,"week5":1,"week6":1,"week7":1},
	 * 			"hour":{"hour0":1,"hour1":1,"hour2":1,"hour3":1,"hour4":1,"hour5":1,"hour6":1,
	 * 				"hour7":1,"hour8":1,"hour9":1,"hour10":1,"hour11":1,"hour12":1,"hour13":1,
	 * 				"hour14":1,"hour15":1,"hour16":1,"hour17":1,"hour18":1,"hour19":1,"hour20":1,
	 * 				"hour21":1,"hour22":1,"hour23":1},
	 * 
	 * 		"bJiOu":"0","fQty_Ji":"","fPrice_ji":"","fQty_Ou":"","fPrice_Ou":"","fRatio_JiOu":""},{"fVipValue
	 * 			...
	 * 		 ]
	 * }
	 */
	private int id;
	
	private String fVipValue;
	
	private String cPloyNo;
	private String cGoodsNo;
	private String cGoodsName;
	private String fQuantity_Ploy;
	private String fPrice_SO;
	private String dDateStart;
	private String cTimeStart;
	private String dDateEnd;
	private String cTimeEnd;
	private String iPriority;
	private String bSO;
	private String bPresent;
	private String cPresentPloyNo;
	private String cPloyTypeNo;
	private String cPloyTypeName;
	private String bEnabled;
	private String bLimitQty;
	private String fLimitQty;
	private String bJiOu;
	private String fQty_Ji;
	private String fQty_Ou;
	private String fPrice_ji;
	private String fPrice_Ou;
	private String fRatio_JiOu;
	
	private Week week;
	
	private Hour hour;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getfVipValue() {
		return fVipValue;
	}
	public void setfVipValue(String fVipValue) {
		this.fVipValue = fVipValue;
	}
	public String getcPloyNo() {
		return cPloyNo;
	}
	public void setcPloyNo(String cPloyNo) {
		this.cPloyNo = cPloyNo;
	}
	public String getcGoodsNo() {
		return cGoodsNo;
	}
	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
	}
	public String getcGoodsName() {
		return cGoodsName;
	}
	public void setcGoodsName(String cGoodsName) {
		this.cGoodsName = cGoodsName;
	}
	public String getfQuantity_Ploy() {
		return fQuantity_Ploy;
	}
	public void setfQuantity_Ploy(String fQuantity_Ploy) {
		this.fQuantity_Ploy = fQuantity_Ploy;
	}
	public String getfPrice_SO() {
		return fPrice_SO;
	}
	public void setfPrice_SO(String fPrice_SO) {
		this.fPrice_SO = fPrice_SO;
	}
	public String getdDateStart() {
		return dDateStart;
	}
	public void setdDateStart(String dDateStart) {
		this.dDateStart = dDateStart;
	}
	public String getcTimeStart() {
		return cTimeStart;
	}
	public void setcTimeStart(String cTimeStart) {
		this.cTimeStart = cTimeStart;
	}
	public String getdDateEnd() {
		return dDateEnd;
	}
	public void setdDateEnd(String dDateEnd) {
		this.dDateEnd = dDateEnd;
	}
	public String getcTimeEnd() {
		return cTimeEnd;
	}
	public void setcTimeEnd(String cTimeEnd) {
		this.cTimeEnd = cTimeEnd;
	}
	public String getiPriority() {
		return iPriority;
	}
	public void setiPriority(String iPriority) {
		this.iPriority = iPriority;
	}
	public String getcPresentPloyNo() {
		return cPresentPloyNo;
	}
	public void setcPresentPloyNo(String cPresentPloyNo) {
		this.cPresentPloyNo = cPresentPloyNo;
	}
	public String getcPloyTypeNo() {
		return cPloyTypeNo;
	}
	public void setcPloyTypeNo(String cPloyTypeNo) {
		this.cPloyTypeNo = cPloyTypeNo;
	}
	public String getcPloyTypeName() {
		return cPloyTypeName;
	}
	public void setcPloyTypeName(String cPloyTypeName) {
		this.cPloyTypeName = cPloyTypeName;
	}
	public String getfLimitQty() {
		return fLimitQty;
	}
	public void setfLimitQty(String fLimitQty) {
		this.fLimitQty = fLimitQty;
	}
	public String getfQty_Ji() {
		return fQty_Ji;
	}
	public void setfQty_Ji(String fQty_Ji) {
		this.fQty_Ji = fQty_Ji;
	}
	public String getfQty_Ou() {
		return fQty_Ou;
	}
	public void setfQty_Ou(String fQty_Ou) {
		this.fQty_Ou = fQty_Ou;
	}
	public String getfPrice_ji() {
		return fPrice_ji;
	}
	public void setfPrice_ji(String fPrice_ji) {
		this.fPrice_ji = fPrice_ji;
	}
	public String getfPrice_Ou() {
		return fPrice_Ou;
	}
	public void setfPrice_Ou(String fPrice_Ou) {
		this.fPrice_Ou = fPrice_Ou;
	}
	public String getfRatio_JiOu() {
		return fRatio_JiOu;
	}
	public void setfRatio_JiOu(String fRatio_JiOu) {
		this.fRatio_JiOu = fRatio_JiOu;
	}
	
	public String getbSO() {
		return bSO;
	}
	public void setbSO(String bSO) {
		this.bSO = bSO;
	}
	public String getbPresent() {
		return bPresent;
	}
	public void setbPresent(String bPresent) {
		this.bPresent = bPresent;
	}
	public String getbEnabled() {
		return bEnabled;
	}
	public void setbEnabled(String bEnabled) {
		this.bEnabled = bEnabled;
	}
	public String getbLimitQty() {
		return bLimitQty;
	}
	public void setbLimitQty(String bLimitQty) {
		this.bLimitQty = bLimitQty;
	}
	public String getbJiOu() {
		return bJiOu;
	}
	public void setbJiOu(String bJiOu) {
		this.bJiOu = bJiOu;
	}
	@Override
	public String toString() {
		return "SpecialGoods [fVipValue=" + fVipValue + ", cPloyNo=" + cPloyNo
				+ ", cGoodsNo=" + cGoodsNo + ", cGoodsName=" + cGoodsName
				+ ", fQuantity_Ploy=" + fQuantity_Ploy + ", fPrice_SO="
				+ fPrice_SO + ", dDateStart=" + dDateStart + ", cTimeStart="
				+ cTimeStart + ", dDateEnd=" + dDateEnd + ", cTimeEnd="
				+ cTimeEnd + ", iPriority=" + iPriority + ", bSO=" + bSO
				+ ", bPresent=" + bPresent + ", cPresentPloyNo="
				+ cPresentPloyNo + ", cPloyTypeNo=" + cPloyTypeNo
				+ ", cPloyTypeName=" + cPloyTypeName + ", bEnabled=" + bEnabled
				+ ", bLimitQty=" + bLimitQty + ", fLimitQty=" + fLimitQty
				+ ", bJiOu=" + bJiOu + ", fQty_Ji=" + fQty_Ji + ", fQty_Ou="
				+ fQty_Ou + ", fPrice_ji=" + fPrice_ji + ", fPrice_Ou="
				+ fPrice_Ou + ", fRatio_JiOu=" + fRatio_JiOu + ", week=" + week
				+ ", hour=" + hour + "]";
	}
}
