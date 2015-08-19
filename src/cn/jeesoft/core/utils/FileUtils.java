package cn.jeesoft.core.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.jeesoft.mvc.Config;
import cn.jeesoft.mvc.helper.RequestHolder;

public class FileUtils {
	
	private static final ExecutorService executor = Executors.newFixedThreadPool(10);;
	
	public static enum FileType {
		JPG,
		JPEG,
		PNG,
		BMP,
		GIF,
		ICO,
		UNKOWN
		;

		public static FileType getFileType(String fileName) {
			if (StringUtils.isEmpty(fileName)) {
				return FileType.UNKOWN;
			}
			
			fileName = fileName.toUpperCase();
			for (FileType fileType : values()) {
				if (fileName.endsWith(fileType.name())) {
					return fileType;
				}
			}
			return FileType.UNKOWN;
		}
		
	}
	
	/**
	 * 根据文件名，获取文件类型
	 * @param fileName
	 * @return
	 */
	public static FileType getFileType(String fileName) {
		return FileType.getFileType(fileName);
	}	
	/**
	 * 根据文件，获取文件类型
	 * @param file
	 * @return
	 */
	public static FileType getFileType(File file) {
		if (file == null) {
			return FileType.UNKOWN;
		}
		return FileType.getFileType(file.getName());
	}

    
	/**
	 * 根据文件类型，生成随机文件名
	 * @param fileType
	 * @return
	 */
	public static String getRandomFileName(FileType fileType) {
		if (fileType == FileType.UNKOWN) {
			fileType = FileType.JPG;
		}
    	return DateUtils.format(new Date(), "yyyyMMddHHmmsss") + StringUtils.getRandomNum(4)
    			+ "." + fileType.name().toLowerCase();
    }
	/**
	 * 根据原始文件名，生成随机文件名
	 * @param fileName
	 * @return
	 */
	public static String getRandomFileName(String fileName) {
		return FileUtils.getRandomFileName(FileUtils.getFileType(fileName));
	}

	/**
	 * 获取工程根目录的路径
	 * @return
	 */
	public static String getRootPath() {
		String rootDir = null;
		try {
			rootDir = RequestHolder.getSession().getServletContext().getRealPath("");
		} catch (Exception e) {
		}
		if (StringUtils.isEmpty(rootDir)) {
			rootDir = File.separator;
		} else if (!rootDir.endsWith(File.separator)) {
			rootDir += File.separator;
		}
		return rootDir;
	}
	/**
	 * 获取相对于工程的路径
	 * @return
	 */
	public static String getRelativePath(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		return getRelativePath(new File(path));
	}
	/**
	 * 获取相对于工程的路径
	 * @return
	 */
	public static String getRelativePath(File path) {
		if (path == null) {
			return null;
		}
		
		String rootDir = getRootPath();
		String absPath = path.getAbsolutePath();
		if (absPath.startsWith(rootDir)) {
			return absPath.substring(rootDir.length() - 1);
		}
		return absPath;
	}
	
	/**
	 * 多文件上传保存
	 * @param request
	 * @return
	 */
	public static Map<String, File> saveMultipartFiles(HttpServletRequest request, String phone) {
		Map<String, File> files = new HashMap<String, File>();
		
    	//创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile multiFile = multiRequest.getFile(iter.next());
				if (multiFile != null && !multiFile.isEmpty()) {
					// 重命名上传后的文件名
					String fileName = FileUtils.getRandomFileName(multiFile.getOriginalFilename());
					// 定义上传路径
					File uploadDir = new File(FileUtils.getRootPath(), "uploads");
					uploadDir = new File(uploadDir, phone);
					uploadDir.mkdir();
					// 接收并保存上传的文件
					final File uploadFile = new File(uploadDir, fileName);
					try {
						multiFile.transferTo(uploadFile);
						files.put(multiFile.getName(), uploadFile);
						
						// 异步执行图片压缩
						executor.execute(new Runnable() {
							@Override
							public void run() {
								Pic pic = new Pic(uploadFile);
								pic.resizeBy(Config.MAX_IMAGE_WIDTH, Config.MAX_IMAGE_HEIGHT);
								pic.save();
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return files;
	}
	
	
}
