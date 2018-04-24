package com.m3u8;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * 用于显示进度条
 * @author willdas
 */
public class ProgressBarRealized extends SwingWorker<Void, Integer>{
		
	private List<String> fileList = new ArrayList<String>();
	private JTextField filePathText;
	private JTextField urlText;
	private JProgressBar progressBar;
	private boolean isComplete = true;
	
	/**
	 * 构造函数
	 * @param fileList
	 * @param filePathText
	 * @param progressBar
	 */
	public ProgressBarRealized(List<String> fileList, JTextField filePathText, JTextField urlText, JProgressBar progressBar) {
		this.fileList = fileList;
		this.filePathText = filePathText;
		this.urlText = urlText;
		this.progressBar = progressBar;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		String url = "";
		int size = fileList.size();   //所有的ts文件
		String fileName = filePathText.getText();  //获取文件名称
		String filePath = fileName.split("\\.")[0];
		//创建文件夹
		String finalFilePath = filePath + File.separator + filePath; 
		FileUtil.createFilePath(finalFilePath);
		for (int i = 0;i < size;i++) {
			//获取每一个ts文件
			String tsFileName = fileList.get(i);
			String[] tsFileNameSplit = tsFileName.split("/");
			fileName = tsFileNameSplit[tsFileNameSplit.length-1];
			//文件最终写入路径
			finalFilePath = filePath + File.separator + fileName;
			//下载地址URL
			url = urlText.getText() + tsFileName;
			//判断网络文件是否正确
			boolean result = DownLoadUtil.downLoadTs(url,finalFilePath);
			if (!result) {
				progressBar.setValue(0);
				JOptionPane.showMessageDialog(null,"请检查网址或者M3U8文件是否正确");
				this.cancel(true);
				isComplete = false;
				break;
			}
			//计算进度
			float pro = (float) i / size * 100;
			//计算结果格式化(四舍五入)
			BigDecimal bd = new BigDecimal(pro).setScale(0, BigDecimal.ROUND_HALF_UP);
			int k = Integer.parseInt(bd.toString());
			//存入数据
			publish(k);
		}
		return null;
	}
	/**
	 * 把结果加入到进度条中
	 */
	@Override
	protected void process(List<Integer> chunks) {
		progressBar.setValue(chunks.get(chunks.size() - 1));
	}
	/**
	 * 下载完成
	 */
	@Override
	protected void done() {
		if (isComplete) {
			progressBar.setValue(100);
			JOptionPane.showMessageDialog(null, "下载完成");
		}
	}
}
