/**
 * @Title: ClassPathUtils.java 
 * @Package com.ls.test.utils 
 * @Description:
 * @author linsheng 
 * @email hi.linsheng@foxmail.com   
 * @date 2015年12月24日 下午3:16:39 
 * @version V1.0   
 */
package com.ls.service.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @Title: ClassPathUtils.java
 * @Package com.ls.test.utils
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2015年12月24日 下午3:16:39
 * @version V1.0
 */
public class ClassUtils {


	/**
	 * @Title:getClassSrc
	 * @Description:获取编译根路径
	 * @param @throws ClassNotFoundException
	 * @return String
	 */
	public static String getClassCompileRoot() {
		return ClassUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	}

	/**
	 * @Title:getProjectPath
	 * @Description: 获取项目路径
	 * @param @return
	 * @return String
	 */
	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}
	
	/**
	 * @Title:getClassInPackage
	 * @Description:获取包中的类类
	 * @param @param pkgName 类所在的报名
	 * @param @param path 包所在的跟路径
	 * @param @return
	 * @return List<String>
	 */
	public static List<Class<?>> getClassInPackage(String pkgName, String path) {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		String rPath = pkgName.replace(".", File.separator) + File.separator;
		try {
			File classPath = new File(path);
			// 文件
			if (classPath.isDirectory()) {
				File dir = new File(classPath, rPath);
				if (dir.list().length > 0) {
					for (File file : dir.listFiles()) {
						if (file.isFile()) {
							String clsName = file.getName();
							clsName = pkgName + "." + clsName.substring(0, clsName.length() - 6);
							ret.add(Class.forName(clsName));
						}
					}
				}
				// jar包
			} else {
				FileInputStream fis = new FileInputStream(classPath);
				JarInputStream jis = new JarInputStream(fis, false);
				JarEntry e = null;
				while ((e = jis.getNextJarEntry()) != null) {
					String eName = e.getName().replace("/", File.separator);
					if (eName.startsWith(rPath) && eName.endsWith(".class")) {
						ret.add(Class.forName(eName.replace(File.separator, ".").substring(0, eName.length() - 6)));
					}
					jis.closeEntry();
				}
				jis.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return ret;
	}

}
