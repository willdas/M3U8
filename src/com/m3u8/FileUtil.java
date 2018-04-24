package com.m3u8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取文件工具类
 * @author willdas
 *
 */
public class FileUtil {

	/**
	 * 获取所有文件
	 * @param fileName
	 * @return
	 */
	public static List<String> readM3U8(String fileName) {
		List<String> fileList = new ArrayList<String>();
		FileInputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = new FileInputStream(fileName);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String tsFileName = null;
			//循环添加到集合中
			while ((tsFileName = bufferedReader.readLine()) != null) {
				if (tsFileName.contains(".ts")) {
					fileList.add(tsFileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			closeIO(inputStream,bufferedReader);
		}
		return fileList;
	}
	/**
	 * 创建文件夹
	 * @param filePath
	 */
	public static void createFilePath(String filePath){
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	/**
	 * 关闭流
	 * @param inputStream
	 * @param bufferedReader
	 */
	private static void closeIO(FileInputStream inputStream, BufferedReader bufferedReader){
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
