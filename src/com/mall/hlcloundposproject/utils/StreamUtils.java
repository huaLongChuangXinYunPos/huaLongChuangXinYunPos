package com.mall.hlcloundposproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * 关闭   流的 工具类
 * @author hl_zq
 * zhaoq_hero@163.com
 */
public final class StreamUtils {
	
	private StreamUtils(){}

	/**
	 * 关闭流  或者网络连接的 方法
	 * @param conn
	 */
	public static void close(Object o) {
		
		if(o!=null){
			try {
				if(o instanceof InputStream){
					((InputStream) o).close();
				}else if(o instanceof OutputStream){
					((OutputStream) o).close();
				}else if(o instanceof Reader){
					((Reader) o).close();
				}else if(o instanceof Writer){
					((Writer) o).close();
				}else if(o instanceof HttpURLConnection){
					((HttpURLConnection) o).disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 输入字节流  读出字节数组：
	 * @param fin
	 * @return
	 */
	public static byte[] readStream(InputStream in) throws IOException{
		byte[] ret  = null;
		
		if(in!=null){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[128];
			int len;//读取长度
			while(true){
				len = in.read(buffer);
				if(len == -1){
					break;
				}
				bos.write(buffer,0,len);
			}
			//注意   部分必须要进行 置空操作  减少内存 溢出
			buffer = null;
			ret = bos.toByteArray();
			bos.close();
		}
		return ret;
	}
}
