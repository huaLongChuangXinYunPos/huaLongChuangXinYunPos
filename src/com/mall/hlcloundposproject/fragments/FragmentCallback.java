package com.mall.hlcloundposproject.fragments;

/**
 * 所有   需要  回调 数据   fragment的  封装类
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-26
 */
public interface FragmentCallback {

	/**
	 * 回调结果  和  fragment的标识
	 * @param result
	 * @param fragmentAuthority
	 */
	void fragmentCallback(String result,int fragmentAuthority);

}
