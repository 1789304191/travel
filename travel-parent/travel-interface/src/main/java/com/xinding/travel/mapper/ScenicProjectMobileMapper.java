package com.xinding.travel.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.ScenicProject;

@Repository
public interface ScenicProjectMobileMapper {

	/**
	 * <p>商品列表</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午4:17:51
	 * @return
	 * @see
	 */
	List<ScenicProject> list(String projectCode);
	/**
	 * <p>商品详情</p> 
	 * @author dongjun
	 * @date 2016年6月27日 下午4:18:02
	 * @param id
	 * @return
	 * @see
	 */
	ScenicProject detail(Integer id);
	
}
