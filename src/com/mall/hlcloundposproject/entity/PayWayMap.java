package com.mall.hlcloundposproject.entity;

/**
 * com.mall.hlcloundposproject.entity
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-2-26
 */
public final class PayWayMap {
	
	private int imageView;
	private String textView;
	
	
	public int getImageView() {
		return imageView;
	}
	public void setImageView(int imageView) {
		this.imageView = imageView;
	}

	public String getTextView() {
		return textView;
	}

	public void setTextView(String textView) {
		this.textView = textView;
	}

	@Override
	public String toString() {
		return "PayWayMap [imageView=" + imageView + ", textView=" + textView
				+ "]";
	}

}
