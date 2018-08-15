/**
 * @Title: StringUtils.java 
 * @Package com.ls.codeBuilder.utils.lang 
 * @Description:
 * @author linsheng 
 * @email hi.linsheng@foxmail.com   
 * @date 2015年12月23日 上午9:13:01 
 * @version V1.0   
 */
package com.ls.service.utils;

/**
 * @Title: StringUtils.java
 * @Package com.ls.codeBuilder.utils.lang
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2015年12月23日 上午9:13:01
 * @version V1.0
 */
public class StringUtils {

	public static boolean notBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isBlank(String str) {
		return (null == str || str.trim().length() == 0);
	}

}
