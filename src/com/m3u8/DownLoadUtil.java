package com.m3u8;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载网络文件工具类
 * @author willdas
 *
 */
public class DownLoadUtil {
	
	/**
	 * 下载网络文件
	 * @param tsFileUrl
	 * @param filePath
	 * @throws Exception
	 */
	public static boolean  downLoadTs(String tsFileUrl,String filePath) {
        try {
        	int byteread = 0;
            //url连接
    		URL url = new URL(tsFileUrl);
    		HttpURLConnection  conn = (HttpURLConnection )url.openConnection();
    		//获取状态码
    		int responseCode = conn.getResponseCode();
    		//获取文件流
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(filePath);
            byte[] buffer = new byte[1204];
            if (responseCode == 200) {
            	//写入到目标文件中
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                fs.close();
                inStream.close();
			}
        } catch (Exception e) {
        	return false;
		}
        return true;
	}
}
