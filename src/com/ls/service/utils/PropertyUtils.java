/**
 * @Title: PropertyUtils.java 
 * @Package com.ls.test.utils 
 * @Description:
 * @author linsheng 
 * @email hi.linsheng@foxmail.com   
 * @date 2015年11月26日 上午9:39:51 
 * @version V1.0   
 */
package com.ls.service.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * @Title: PropertyUtils.java
 * @Package com.ls.test.utils
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2015年11月26日 上午9:39:51
 * @version V1.0
 */
public class PropertyUtils {

	/**
	 * property 对象
	 */
	private Properties properties;

	
	@SuppressWarnings("unused")
	private PropertyUtils(){};
	/**
	 * 加载文件
	 */
	private File file;

	public PropertyUtils(String file) throws IOException {
		this.file = new File(file);
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(file)) {
			properties.load(fis);
		}
	}
	
	/**
	 * @param file
	 * @throws IOException
	 */
	public PropertyUtils(File file) throws IOException {
		this.file = file;
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(file)) {
			properties.load(fis);
		}
	}

	/**
	 * @Title:getProperties
	 * @Description:获取Properties
	 * @return Properties
	 */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * @Title:getValue 
	 * @Description:通过key获取值
	 * @param @param key
	 * @param @return
	 * @return String
	 */
	public String getValue(String key){
		return properties.getProperty(key);
	}

	/**
	 * @Title:writeKeyValue
	 * @Description:写入文件
	 * @param @param key
	 * @param @param value
	 * @param @param comments
	 * @param @throws FileNotFoundException
	 * @param @throws IOException
	 */
	public void writeKeyValue(String key, String value, String comments) throws FileNotFoundException, IOException {

		if (properties.containsKey(key)) {
			String propertyValue = "";
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				StringBuilder sb = new StringBuilder();
				for (;;) {
					String line = reader.readLine();
					if (line == null)
						break;
					sb.append(line + "\n");
				}
				String oldSet = key + "=" + properties.getProperty(key);
				String newSet = key + "=" + value;
				if (null != comments && !"".equals(comments)) {
					newSet = "#" + comments + "\n" + newSet;
				}
				propertyValue = sb.toString().replace(" ", "").replace(oldSet, newSet);
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(propertyValue);
				writer.flush();
			}
			return;
		}

		try (FileOutputStream out = new FileOutputStream(file, true)) {
			Properties temp = new Properties();
			temp.setProperty(key, value);
			properties.store(out, comments);
		}
	}
	
	
	/**
	 * @Title:writeProperties 
	 * @Description:将整个property写入
	 * @param @param properties
	 * @param @param comments
	 * @param @throws IOException
	 * @return void
	 */
	public void writeProperties(Properties properties,String comments) throws IOException{
		try (FileOutputStream out = new FileOutputStream(file, false)) {
			properties.store(out, comments);
		}
	}
}
