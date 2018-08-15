package com.ls.service.model;

import java.io.File;
import java.util.Date;

/**
 * @Title: CreateInfo.java 
 * @Package com.ls.service.model 
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2016年2月2日 下午3:58:14 
 * @version V1.0   
 */
public class CreateInfo {

	private Date modify_datetime;
	private File project_path;
	private File project_webRoot;
	private File project_class;
	private File project_out_path;
	
	public CreateInfo(){}
	
	/**
	 * @param modify_datetime
	 * @param project_path
	 * @param project_webRoot
	 * @param project_class
	 * @param project_out_path
	 */
	public CreateInfo(Date modify_datetime, File project_path, File project_webRoot, File project_class, File project_out_path) {
		this.modify_datetime = modify_datetime;
		this.project_path = project_path;
		this.project_webRoot = project_webRoot;
		this.project_class = project_class;
		this.project_out_path = project_out_path;
	}
	
	public Date getModify_datetime() {
		return modify_datetime;
	}
	public void setModify_datetime(Date modify_datetime) {
		this.modify_datetime = modify_datetime;
	}
	public File getProject_path() {
		return project_path;
	}
	public void setProject_path(File project_path) {
		this.project_path = project_path;
	}
	public File getProject_webRoot() {
		return project_webRoot;
	}
	public void setProject_webRoot(File project_webRoot) {
		this.project_webRoot = project_webRoot;
	}
	public File getProject_class() {
		return project_class;
	}
	public void setProject_class(File project_class) {
		this.project_class = project_class;
	}
	public File getProject_out_path() {
		return project_out_path;
	}
	public void setProject_out_path(File project_out_path) {
		this.project_out_path = project_out_path;
	}
	
	
}
