package com.m3u8;

import javax.swing.SwingUtilities;

/**
 * m3u8客户端下载工具 启动类
 * @author willdas
 *
 */
public class Main {
	public static void main(String[] args) {
		try {
			//启动工具类
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					Window window = new Window();
					window.setVisible(true); // 显示窗口
					window.setResizable(false); //禁止改变大小
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
