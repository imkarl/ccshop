package cn.jeesoft.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 多线程文件写入帮助类
 * @author king
 */
public class FileWriteHelper {
	
	private File file;
	private FileOutputStream out;
	private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private Thread mThread;
	private boolean isStop = false;

	public FileWriteHelper(String file) throws FileNotFoundException {
		this(new File(file));
	}
	public FileWriteHelper(File file) throws FileNotFoundException {
		this.file = file;
		this.out = new FileOutputStream(file, true);
	}

	public void start() {
		mThread = new Thread() {
			@Override
			public void run() {
				while (!isStop) {// 循环监听
					try {
						out.write('\n');
						out.write(queue.take().getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				mThread = null;
				file = null;
				try {
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
		};
		mThread.start();
	}
	
	public void stop() {
		isStop = true;
	}


	public void append(String text) {
		queue.add(text);
	}
	
	public File getFile() {
		return file;
	}

}
