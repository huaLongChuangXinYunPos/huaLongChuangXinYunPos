package com.example.hlcloundposproject.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ����ϵͳ  ʱ���  utils
 * com.example.hlcloundposproject.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-2-26
 */
public final class TimeUtils {
	
	/**
	 * ��ȡϵͳ��ǰʱ��   ��Ϊ �ҵ����ݵı���
	 * @return
	 */
	public static String getSystemNowTime(String formatPattern) {
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);
		long date = System.currentTimeMillis();															
		String time = df.format(new Date());
		return time;
	}
	

}
