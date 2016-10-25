//package com.xinding.travel.controller;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.xinding.travel.pojo.PagedResult;
//import com.xinding.travel.pojo.User;
//import com.xinding.travel.service.IUserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)    //表示继承了SpringJUnit4ClassRunner类  
//@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"}) 
//public class TestMyBatis {
//
//	private static Logger log =  Logger.getLogger(TestMyBatis.class);
//	@Resource
//	private IUserService userService = null;
//	
////	@Test
////	public void test(){
////		User user = userService.getUserById(1);
////		log.info(JSON.toJSONString(user));
////	}
//	
//	@Test
//	public void queryByPage(){
//		 PagedResult<User>  pagedResult = userService.queryByPage(null,1,10);//null表示查全部	
//		 log.info("查找结果" + pagedResult);
//	}
//}
