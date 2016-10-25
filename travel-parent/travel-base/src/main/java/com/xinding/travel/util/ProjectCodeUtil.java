package com.xinding.travel.util;

public class ProjectCodeUtil {

	public static String getProjectCode(String projectCode){
		//海湾项目编码
		if("hw".equals(projectCode)){
			projectCode = projectCode.toUpperCase();
		}
		
		
		return projectCode;
	}
}
