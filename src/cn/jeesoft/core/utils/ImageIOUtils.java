package cn.jeesoft.core.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片处理工具类
 * @version v0.1 king 2015年4月21日
 */
public class ImageIOUtils {
	
	/**
	 * 保存图片
	 * @param image 内存中的图片对象
	 * @param target 保存的目标文件
	 * @throws IOException 文件写入异常
	 */
	public static void saveImage(BufferedImage image, File target) throws IOException {
//		FileOutputStream out = new FileOutputStream(imgdist);
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		encoder.encode(tag);
//		out.close();
		
		String filePath = target.getAbsolutePath();
		String formatName = filePath.substring(filePath.lastIndexOf(".") + 1);  
		ImageIO.write(image, formatName, target);
	}
	
}
