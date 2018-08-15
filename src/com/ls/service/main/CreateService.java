package com.ls.service.main;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.ls.service.model.CreateInfo;
import com.ls.service.utils.FileUtils;

/**
 * @Title: CreateService.java
 * @Package com.ls.service.main
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2016年2月2日 下午4:31:40
 * @version V1.0
 */
public class CreateService {

	private static long now_time;
	private static long mody_time;
	/**
	 * 项目路径
	 */
	private static String project_file;
	/**
	 * 编译路径
	 */
	private static String class_file;
	/**
	 * web路径
	 */
	private static String web_file;
	/**
	 * 输出路径
	 */
	private static String out_file;
	/**
	 * 项目文件长度
	 */
	private static int profile_length;

	public boolean getFile(CreateInfo info) throws IOException {

		now_time = new Date().getTime();
		mody_time = info.getModify_datetime().getTime();

		if (now_time < mody_time)
			return true;
		project_file = info.getProject_path().getAbsolutePath();
		class_file = info.getProject_class().getAbsolutePath();
		web_file = info.getProject_webRoot().getAbsolutePath();
		out_file = info.getProject_out_path().getAbsolutePath();
		profile_length = project_file.length();

		// class文件复制
		copyClassFile(new File(project_file + File.separator + "src"));
		// 其它目录复制
		copyFile(new File(web_file));

		return true;
	}

	private void copyFile(File file) throws IOException {

		if (file.getAbsolutePath().equals(class_file))
			return;

		if (file.isDirectory()) {
			File[] fileList = file.listFiles();
			for (File f : fileList) {
				copyFile(f);
			}
		} else {
			long fileTime = file.lastModified();
			if (now_time > fileTime && fileTime > mody_time) {
				String sourcepath = file.getAbsolutePath();
				String copypath = out_file + sourcepath.substring(profile_length);
				FileUtils.copyFile(file, new File(copypath));

			}
		}

	}

	private void copyClassFile(File file) throws IOException {

		if (file.isDirectory() && !file.getAbsolutePath().equals(class_file)) {
			File[] fileList = file.listFiles();
			for (File f : fileList) {
				copyClassFile(f);
			}
		} else {
			long fileTime = file.lastModified();
			if (now_time > fileTime && fileTime > mody_time) {
				String temp = file.getAbsolutePath();
				String sourcepath = "";
				String copypath = "";
				if (temp.endsWith("java")) {
					temp = temp.substring(0, temp.lastIndexOf("java")) + "class";
				}
				temp = temp.substring(profile_length + 4);
				sourcepath = class_file + temp;
				copypath = out_file + sourcepath.substring(profile_length);
				FileUtils.copyFile(new File(sourcepath), new File(copypath));
			}
		}

	}

}
