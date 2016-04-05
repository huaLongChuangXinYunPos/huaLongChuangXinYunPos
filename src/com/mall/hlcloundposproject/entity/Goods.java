package com.mall.hlcloundposproject.entity;

import java.io.Serializable;

/**
 * 正常商品的    实体类
 * com.hlrj.hlcloundpos.entity
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-25
 */
public final class Goods implements Serializable{
	/**
	 * "cGoodsNo,cUnitedNo,cGoodsName,cBarcode,cUnit,cSpec," +
		"fNormalPrice,fVipPrice,fVipScore,bWeight,fVipScore_base," +
		"fVipPrice_student,bHidePrice,bHideQty,bNoVipPrice,bUpdate)
		
		{"resultStatus":1,"data":[{"cGoodsNo":"02","cUnitedNo":"02",
		"cGoodsName":"小袋子","cBarcode":"02","cUnit":"个","cSpec":"",
		"fNormalPrice":0.2000,"fVipPrice":0.2000,"fVipScore":0.2000,"bWeight":0,
		"fVipScore_base":0.2000,
		"fVipPrice_student":0.2000,"bHidePrice":0,"bHideQty":0,"bNoVipPrice":0,"bUpdate":0},
	 */
	
	private Integer id;
	private String cGoodsNo;
	private String cUnitedNo;
	private String cGoodsName;
	private String cBarcode;
	private String cUnit;
	private String cSpec;
	
	private float fNormalPrice;
	private float fVipPrice;
	private float fVipScore;
	private float fVipScore_base;
	private float fVipPrice_student;
	
	private int bWeight;
	private int bHidePrice;
	private int bHideQty;
	private int bNoVipPrice;
	private int bUpdate;
	
	
	private float amount;//商品的  购买数量：  不保存至  商品  基本信息表    忽略该字段

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	private double payMoney;  //商品的  金额    等于  商品数量乘以现价：


	public double getPayMoney() {
		String text = new java.text.DecimalFormat("##########.0000").format(amount*fNormalPrice);
		return Double.parseDouble(text);
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getcGoodsNo() {
		return cGoodsNo;
	}

	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
	}

	public String getcUnitedNo() {
		return cUnitedNo;
	}

	public void setcUnitedNo(String cUnitedNo) {
		this.cUnitedNo = cUnitedNo;
	}

	public String getcGoodsName() {
		return cGoodsName;
	}

	public void setcGoodsName(String cGoodsName) {
		this.cGoodsName = cGoodsName;
	}

	public String getcBarcode() {
		return cBarcode;
	}

	public void setcBarcode(String cBarcode) {
		this.cBarcode = cBarcode;
	}

	public String getcUnit() {
		return cUnit;
	}

	public void setcUnit(String cUnit) {
		this.cUnit = cUnit;
	}

	public String getcSpec() {
		return cSpec;
	}

	public void setcSpec(String cSpec) {
		this.cSpec = cSpec;
	}

	public float getfNormalPrice() {
		return fNormalPrice;
	}

	public void setfNormalPrice(float fNormalPrice) {
		this.fNormalPrice = fNormalPrice;
	}

	public float getfVipPrice() {
		return fVipPrice;
	}

	public void setfVipPrice(float fVipPrice) {
		this.fVipPrice = fVipPrice;
	}

	public float getfVipScore() {
		return fVipScore;
	}

	public void setfVipScore(float fVipScore) {
		this.fVipScore = fVipScore;
	}

	public float getfVipScore_base() {
		return fVipScore_base;
	}

	public void setfVipScore_base(float fVipScore_base) {
		this.fVipScore_base = fVipScore_base;
	}

	public float getfVipPrice_student() {
		return fVipPrice_student;
	}

	public void setfVipPrice_student(float fVipPrice_student) {
		this.fVipPrice_student = fVipPrice_student;
	}

	public int getbWeight() {
		return bWeight;
	}

	public void setbWeight(int bWeight) {
		this.bWeight = bWeight;
	}

	public int getbHidePrice() {
		return bHidePrice;
	}

	public void setbHidePrice(int bHidePrice) {
		this.bHidePrice = bHidePrice;
	}

	public int getbHideQty() {
		return bHideQty;
	}

	public void setbHideQty(int bHideQty) {
		this.bHideQty = bHideQty;
	}

	public int getbNoVipPrice() {
		return bNoVipPrice;
	}

	public void setbNoVipPrice(int bNoVipPrice) {
		this.bNoVipPrice = bNoVipPrice;
	}

	public int getbUpdate() {
		return bUpdate;
	}

	public void setbUpdate(int bUpdate) {
		this.bUpdate = bUpdate;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", cGoodsNo=" + cGoodsNo + ", cUnitedNo="
				+ cUnitedNo + ", cGoodsName=" + cGoodsName + ", cBarcode="
				+ cBarcode + ", cUnit=" + cUnit + ", cSpec=" + cSpec
				+ ", fNormalPrice=" + fNormalPrice + ", fVipPrice=" + fVipPrice
				+ ", fVipScore=" + fVipScore + ", bWeight=" + bWeight
				+ ", fVipScore_base=" + fVipScore_base + ", fVipPrice_student="
				+ fVipPrice_student + ", bHidePrice=" + bHidePrice
				+ ", bHideQty=" + bHideQty + ", bNoVipPrice=" + bNoVipPrice
				+ ", bUpdate=" + bUpdate + ", amount=" + amount + ", payMoney="
				+ payMoney + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + bHidePrice;
		result = prime * result + bHideQty;
		result = prime * result + bNoVipPrice;
		result = prime * result + bUpdate;
		result = prime * result + bWeight;
		result = prime * result
				+ ((cBarcode == null) ? 0 : cBarcode.hashCode());
		result = prime * result
				+ ((cGoodsName == null) ? 0 : cGoodsName.hashCode());
		result = prime * result
				+ ((cGoodsNo == null) ? 0 : cGoodsNo.hashCode());
		result = prime * result + ((cSpec == null) ? 0 : cSpec.hashCode());
		result = prime * result + ((cUnit == null) ? 0 : cUnit.hashCode());
		result = prime * result
				+ ((cUnitedNo == null) ? 0 : cUnitedNo.hashCode());
		result = prime * result + Float.floatToIntBits(fNormalPrice);
		result = prime * result + Float.floatToIntBits(fVipPrice);
		result = prime * result + Float.floatToIntBits(fVipPrice_student);
		result = prime * result + Float.floatToIntBits(fVipScore);
		result = prime * result + Float.floatToIntBits(fVipScore_base);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(payMoney);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goods other = (Goods) obj;
		if (cBarcode == null) {
			if (other.cBarcode != null)
				return false;
		} else if (!cBarcode.equals(other.cBarcode))
			return false;
		
		return true;
	}

	

}
