package com.example.hlcloundposproject.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import android.os.Build;

/**
 * ��װ�������   �Ĺ�����
 * @author hl_zq
 * zhaoq_hero@163.com
 */
public final class HttpTools {
	
	/**
	 * ˽�л����췽��   ���� �����Ա�ʵ����
	 */
	private HttpTools(){}
	
	//�������ӳ�ʱʱ��
	private static final int CONNECTION_TIMEOUT= 10000;
	//����  ��ȡ��ʱʱ�䣺
	private static final int READ_TIMEOUT = 10000;
//	//�����������ݵ�����ͷ��Ϣ��
	public static final String USER_AGENT = 
			"hlrjCloundPos_1.0.0(" + Build.MODEL + "," + Build.VERSION.SDK_INT + ")";
	
	@SuppressWarnings("unused")
	private static InputStream in = null;

	/**
	 * ʹ��   doGet���� ����  ��������   �����ֽ����顢
	 * @param url
	 * @return
	 */
	public static byte[] doGet(String urlStr) {
		byte[] ret = null;
	
		if(urlStr!=null){
			HttpURLConnection conn = null;
			try {
				URL url = new URL(urlStr);
				conn = (HttpURLConnection) url.openConnection();
				
				//���ó�ʱʱ��
				conn.setConnectTimeout(CONNECTION_TIMEOUT);
				conn.setReadTimeout(READ_TIMEOUT);
				
				conn.setRequestMethod("GET");
				//����Acceptͷ��Ϣ    
				conn.setRequestProperty("Accept", "appliction/*,text/*,image/*,*/*");
				//��������ѹ����ʽ
				conn.setRequestProperty("Accept-Encoding", "gzip");
//				//����  user-agent
				conn.setRequestProperty("User-Agent", USER_AGENT);
				
				conn.connect();
				
				int code = conn.getResponseCode();
				if(code == 200){
					InputStream fin = conn.getInputStream();
					//����������������ѹ����    ��ȡͷ�ֶ� 
					String encoding = conn.getHeaderField("Content-Encoding");
					
					if("gzip".equals(encoding)){
						fin = new GZIPInputStream(fin);//ʹ��  gzip ѹ����ʽ
					}
					
					//��ȡ  �ֽ���
					ret = StreamUtils.readStream(fin);
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				StreamUtils.close(conn);
			}
		}
		return ret;
	}
	
	/**
	 * doPost������
	 * @throws UnsupportedEncodingException 
	 */
	public static void doPost(String url,HashMap<String,String> map) throws UnsupportedEncodingException{
		
		if(url!=null){
			StringBuilder sb = new StringBuilder();
			
			for(Map.Entry<String,String> en : map.entrySet()){
				sb.append(en.getKey())
						.append("=")
						.append(URLEncoder.encode(en.getValue(),"utf-8"));
			}
			
			HttpURLConnection connection = null;
			
			try {
				URL u = new URL(url);
				
				connection = (HttpURLConnection) u.openConnection();
				
				//�������ã�
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(CONNECTION_TIMEOUT);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				
				//���� �ύ�����ݵ����ͣ�
				connection.setRequestProperty("Content-Type", 
						"application/x-www-form-urlencoded");
				connection.connect();
				//�����ύ�����ݣ�
				byte[] b = sb.toString().getBytes();
				
				//�ύ����   �������  д������
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(b,0,b.length);
				outputStream.close();
				
				in = null;
				
				if(connection.getResponseCode()==200){
					in=connection.getInputStream();
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
