package com.m3u8;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * m3u8客户端窗口
 * 
 * @author willdas
 *
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField filePathText;
	private JTextField urlText;
	private JProgressBar progressBar;
	private List<String> fileList = new ArrayList<String>();

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setAutoRequestFocus(false);
		setBounds(350, 200, 721, 430);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 网址
		urlText = new JTextField("请输入网站域名");
		urlText.setBounds(5, 6, 200, 27);
		urlText.setEditable(true);
		urlText.setColumns(10);
		add(urlText);
		// 文件路径
		filePathText = new JTextField("请选择m3u8文件");
		filePathText.setBounds(215, 6, 285, 27);
		filePathText.setEditable(false);
		filePathText.setColumns(50);
		add(filePathText);
		// 选择按钮
		JButton selectFileButton = new JButton("选择文件");
		selectFileButton.setBounds(510, 6, 100, 27);
		setLayout(null);
		selectFile(selectFileButton);
		add(selectFileButton);
		// 下载按钮
		JButton parseFileButton = new JButton("开始下载");
		parseFileButton.setBounds(620, 6, 90, 27);
		parseFile(parseFileButton);
		add(parseFileButton);
		// 进度条
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setBounds(0, 377, getWidth(), 25);
		add(progressBar);
	}

	/**
	 * 选择文件
	 * 
	 * @param selectFileButton
	 */
	private void selectFile(JButton selectFileButton) {
		// 监听按钮事件
		selectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 创建选择框
					JFileChooser jfc = new JFileChooser();
					// 设置选择m3u8文件
					jfc.setSelectedFile(new File("xxx.m3u8"));
					jfc.setCurrentDirectory(new File("."));
					// 提示框信息
					jfc.showDialog(new JLabel(), "请选择M3U8文件");
					// 只能选择文件
					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					// 获取选择的文件
					File f = jfc.getSelectedFile();
					if (f.isFile()) {
						String path = f.getPath();
						//判断是否为m3u8文件
						if (f.getName().endsWith(".m3u8")) {
							filePathText.setText(path);
							fileList = FileUtil.readM3U8(filePathText.getText());
							progressBar.setValue(0);
						} else {
							// 错误提示框
							JOptionPane.showMessageDialog(null, "只能选择M3U8类型文件");
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}

	/**
	 * 开始下载文件
	 * @param parseFileButton
	 */
	private void parseFile(JButton parseFileButton){
		//监听按钮事件
		parseFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = filePathText.getText();
				String url = urlText.getText();
				//判断选择的文件是否为m3u8文件
				if (!regexUrl(url)) {
					JOptionPane.showMessageDialog(null,"请输入正确网址");
				}else if (!fileName.endsWith(".m3u8")) {
					JOptionPane.showMessageDialog(null,"请选择M3U8类型文件");
				}else{
					new ProgressBarRealized(fileList, filePathText, urlText,progressBar).execute();
				}
			}
		});
	}

	/**
	 * 正则网址
	 * @param url
	 * @return
	 */
	private static boolean regexUrl(String url) {
		Pattern pattern = Pattern
				.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
		if (pattern.matcher(url).matches()) {
			return true;
		}
		return false;
	}
}
