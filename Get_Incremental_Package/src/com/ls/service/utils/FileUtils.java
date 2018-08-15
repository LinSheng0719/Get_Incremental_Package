package com.ls.service.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: FileUtils.java
 * @Package com.ls.test.utils
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2015年11月11日 下午2:32:41
 * @version V1.0
 */
public class FileUtils {

	public static String ENCODING_UTF8 = "UTF-8";

	public static String ENCODING_UNICODE = "Unicode";

	public static String ENCODING_UTF16BE = "UTF-16BE";

	public static String ENCODING_GBK = "GBK";

	/**
	 * @Title:formatFilePath
	 * @Description:格式化文件路径
	 * @param @param path
	 * @return String
	 */
	public static String formatFilePath(String path) {
		return Paths.get(path).toString();
	}

	/**
	 * @Title:isEx 
	 * @Description:文件是否存在
	 * @param @param path
	 * @param @return
	 * @return boolean
	 */
	public static boolean isExists(String path){
		return new File(path).exists();
	}
	/**
	 * @Title:newBufferedReader
	 * @Description:新建bufferedReader
	 * @param @param file
	 * @param @param encoding
	 * @param @throws UnsupportedEncodingException
	 * @param @throws FileNotFoundException
	 * @return BufferedReader
	 */
	public static BufferedReader newBufferedReader(File file, String encoding) throws UnsupportedEncodingException, FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
	}

	/**
	 * @Title:newBufferedWriter
	 * @Description:新建BufferedWriter
	 * @param @param file
	 * @param @param encoding
	 * @param @param append
	 * @param @throws IOException
	 * @return BufferedWriter
	 */
	public static BufferedWriter newBufferedWriter(File file, String encoding, boolean append) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encoding));
	}

	/**
	 * @Title:getFileEncoding
	 * @Description:获取文件编码
	 * @param @param file
	 * @param @throws IOException
	 * @return String
	 */
	public static String getFileEncoding(File file) throws IOException {

		String encoding = null;
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int p = (bis.read() << 8) + bis.read();
		switch (p) {
		case 0xefbb:
			encoding = "UTF-8";
			break;
		case 0xfffe:
			encoding = "Unicode";
			break;
		case 0xfeff:
			encoding = "UTF-16BE";
			break;
		default:
			encoding = "GBK";
		}
		if (bis != null)
			bis.close();
		return encoding;
	}

	/**
	 * @Title:readTextFileOutList
	 * @Description:读取小文件文本，返回List
	 * @param @param file
	 * @param @return
	 * @param @throws IOException
	 * @return List<String>
	 */
	public static List<String> readTextFileOutList(File file) throws IOException {
		return readTextFileOutList(file, getFileEncoding(file));
	}

	/**
	 * @Title:readTextFileOutList
	 * @Description:读取返回List
	 * @param @param file
	 * @param @param encoding
	 * @param @throws IOException
	 * @return List<String>
	 */
	public static List<String> readTextFileOutList(File file, String encoding) throws IOException {
		try (BufferedReader reader = newBufferedReader(file, encoding)) {
			List<String> result = new ArrayList<>();
			for (;;) {
				String line = reader.readLine();
				if (line == null)
					break;
				result.add(line);
			}
			reader.close();
			return result;
		}
	}

	/**
	 * @Title:readTextFileOutString
	 * @Description:读取小文本，返回String
	 * @param @param file
	 * @param @return
	 * @param @throws IOException
	 * @return String
	 */
	public static String readTextFileOutString(File file) throws IOException {
		return readTextFileOutString(file, getFileEncoding(file));
	}

	/**
	 * @Title:readTextFileOutString
	 * @Description:读取返回String
	 * @param @param file
	 * @param @param encoding
	 * @param @throws IOException
	 * @return String
	 */
	public static String readTextFileOutString(File file, String encoding) throws IOException {
		try (BufferedReader reader = newBufferedReader(file, encoding)) {
			StringBuilder sb = new StringBuilder();
			for (;;) {
				String line = reader.readLine();
				if (line == null)
					break;
				sb.append(line + "\n");
			}
			return sb.toString();
		}
	}

	/**
	 * @Title:readStream
	 * @Description: 获取文件字节流
	 * @param @param is
	 * @param @return
	 * @param @throws IOException
	 * @return byte[]
	 */
	public static byte[] readFileOutByte(File file) throws IOException {
		return readFileOutByte(new BufferedInputStream(new FileInputStream(file)));
	}

	/**
	 * @Title:readFileOutByte
	 * @Description:获取文件字节流
	 * @param @param is
	 * @param @return
	 * @param @throws IOException
	 * @return byte[]
	 */
	public static byte[] readFileOutByte(BufferedInputStream is) throws IOException {

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = is.read(b, 0, b.length)) != -1) {
				baos.write(b, 0, len);
			}
			return baos.toByteArray();
		}
	}

	/**
	 * @Title:writeFileByte
	 * @Description:写入字节数组
	 * @param @param temp
	 * @param @param file
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean writeFileByte(byte[] temp, File file) throws IOException {

		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(temp, 0, temp.length);
			fos.flush();
			return true;
		}
	}

	/**
	 * @Title:wrieteTextAppend
	 * @Description:追加方式添加文件
	 * @param @param content
	 * @param @param file
	 * @param @param encoding
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean writeTextAppend(String content, File file, String encoding) throws IOException {
		return writeTextFile(content, file, encoding, true);
	}

	public static boolean writeTextAppend(String content, File file) throws IOException {
		return writeTextFile(content, file, ENCODING_UTF8, true);
	}

	/**
	 * @Title:wrieteTextNew
	 * @Description:覆盖方式添加文件
	 * @param @param content
	 * @param @param file
	 * @param @param encoding
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean writeTextNew(String content, File file, String encoding) throws IOException {
		return writeTextFile(content, file, encoding, false);
	}

	public static boolean writeTextNew(String content, File file) throws IOException {
		return writeTextFile(content, file, ENCODING_UTF8, false);
	}

	/**
	 * @Title:writeTextFile
	 * @Description:文本写入文本文件
	 * @param @param content
	 * @param @param file
	 * @param @param append
	 * @param @return
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean writeTextFile(String content, File file, String encoding, boolean append) throws IOException {

		createFile(file);
		try (BufferedWriter bufferWritter = newBufferedWriter(file, encoding, append)) {
			bufferWritter.write(content);
			bufferWritter.flush();
			return true;
		}
	}

	/**
	 * @Title:createDir
	 * @Description:创建文件夹
	 * @param @param dir
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean createDir(File dir) throws IOException {

		if (!dir.exists()) {
			return dir.mkdirs();
		}
		return true;
	}

	/**
	 * @Title:createFile
	 * @Description:创建文件
	 * @param @param file
	 * @return void
	 * @throws IOException
	 */
	public static boolean createFile(File file) throws IOException {

		if (!file.exists()) {
			if (createDir(file.getParentFile()))
				return file.createNewFile();
			return false;
		}
		return true;
	}

	/**
	 * @Title:deleteFolder
	 * @Description:删除文件夹
	 * @param @param file
	 * @return boolean
	 */
	public static boolean deleteFolder(File file) {

		File[] fileList = file.listFiles();
		if (fileList != null) {
			for (File f : fileList) {
				deleteFolder(f);
				f.delete();
			}
		}
		if (file.exists())
			file.delete();
		return true;
	}

	/**
	 * @Title:reNameFile
	 * @Description:重命名文件
	 * @param @param file
	 * @param @param newName
	 * @return boolean
	 */
	public static boolean reNameFile(File file, String newName) {

		if (!file.exists())
			return false;
		File newFile = new File(file.getAbsolutePath() + File.separator + newName);
		return moveFile(file, newFile);
	}

	/**
	 * @Title:moveFile
	 * @Description:移动文件
	 * @param @param source
	 * @param @param target
	 * @param @return
	 * @return boolean
	 */
	public static boolean moveFile(File source, File target) {
		if (!source.exists())
			return false;
		File parent = target.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		return source.renameTo(target);
	}

	/**
	 * @Title:copyFile
	 * @Description:
	 * @param @param source
	 * @param @param target
	 * @param @return
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean copy(File source, File target) throws IOException {

		if (!source.exists())
			return false;
		if (target.exists())
			return false;

		if (source.isDirectory()) {
			return copyFolder(source, target);
		} else {
			return copyFile(source, target);
		}
	}
	
	/**
	 * @Title:copy 
	 * @Description:复制文件
	 * @param @param source
	 * @param @param target
	 * @param @return
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean copy(String source, String target) throws IOException {
		return copy(new File(source), new File(target));
	}

	/**
	 * @Title:copyFile
	 * @Description:复制文件
	 * @param @param source
	 * @param @param target
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean copyFile(File source, File target) throws IOException {

		if (!source.exists())
			return false;
		if (target.exists()){
			return false;
		}else{
			createDir(target.getParentFile());
		}
			

		try (FileInputStream fin = new FileInputStream(source); FileOutputStream fout = new FileOutputStream(target); FileChannel inc = fin.getChannel(); FileChannel outc = fout.getChannel()) {
			long size = getFolderLength(source);
			return outc.transferFrom(inc, 0, inc.size()) == size;
		}
	}

	/**
	 * @Title:copyFolder
	 * @Description:复制文件夹
	 * @param @param sourceFolder
	 * @param @param targetFolder
	 * @param @throws IOException
	 * @return boolean
	 */
	public static boolean copyFolder(File sourceFolder, File targetFolder) throws IOException {

		if (!sourceFolder.exists() || !sourceFolder.isDirectory())
			return false;
		createDir(targetFolder);

		File[] fileList = sourceFolder.listFiles();
		if (fileList == null)
			return true;

		for (File file : fileList) {
			File targetFile = new File(targetFolder.getAbsoluteFile() + File.separator + file.getName());
			if (file.isDirectory()) {
				copyFolder(file, targetFile);
			} else {
				copyFile(file, targetFile);
			}
		}

		return true;
	}

	/**
	 * @Title:getFolderLength
	 * @Description:获取文件夹大小
	 * @param @param file
	 * @param @return
	 * @return long
	 */
	public static long getFolderLength(File file) {

		long totalLength = file.length();
		File[] fileList = file.listFiles();
		if (fileList != null) {
			for (File f : fileList) {
				totalLength += getFolderLength(f);
			}
		}
		return totalLength;
	}

	/**
	 * @Title:formatFileSize
	 * @Description:文件大小格式转化
	 * @param @param fileSize
	 * @param @return
	 * @return String
	 */
	public static String formatFileSize(long fileSize) {
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("0.00");
		if (fileSize < 1024) {
			sb.append(fileSize);
			sb.append("B");
		} else if (1024 * 1024 > fileSize && fileSize >= 1024) {
			Double num = fileSize / 1024.0;
			sb.append(df.format(num));
			sb.append("KB");
		} else if (1024 * 1024 * 1024 > fileSize && fileSize >= 1024 * 1024) {
			Double num = fileSize / (1024 * 1024.0);
			sb.append(df.format(num));
			sb.append("MB");
		} else if (1024 * 1024 * 1024 * 1024 >= fileSize && fileSize >= 1024 * 1024 * 1024) {
			Double num = fileSize / (1024 * 1024 * 1024.0);
			sb.append(df.format(num));
			sb.append("GB");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String f1 = "D:\\zzz_my_project\\spring_freemaker\\WebRoot\\WEB-INF\\classes\\com\\ls\\freemarker\\FreeMarkerTest.class";
		String f2 = "C:\\Users\\linsheng\\Desktop\\pack\\com\\FreeMarkerTest.class";
		try {
			copyFile(new File(f1), new File(f2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
