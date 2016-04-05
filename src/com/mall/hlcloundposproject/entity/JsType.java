package com.mall.hlcloundposproject.entity;

public final class JsType {
	
	private String jsType;
	
	private double mianzhi;
	
	private double zhekou;
	
	private double shishou;
	
	private double zhaoling;
	
	private String detail;

	public String getJsType() {
		return jsType;
	}

	public void setJsType(String jsType) {
		this.jsType = jsType;
	}

	public double getMianzhi() {
		return mianzhi;
	}

	public void setMianzhi(double mianzhi) {
		this.mianzhi = mianzhi;
	}

	public double getZhekou() {
		return zhekou;
	}

	public void setZhekou(double zhekou) {
		this.zhekou = zhekou;
	}

	public double getShishou() {
		return shishou;
	}

	public void setShishou(double shishou) {
		this.shishou = shishou;
	}

	public double getZhaoling() {
		return zhaoling;
	}

	public void setZhaoling(double zhaoling) {
		this.zhaoling = zhaoling;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "JsType [jsType=" + jsType + ", mianzhi=" + mianzhi
				+ ", zhekou=" + zhekou + ", shishou=" + shishou + ", zhaoling="
				+ zhaoling + ", detail=" + detail + "]";
	}

}
