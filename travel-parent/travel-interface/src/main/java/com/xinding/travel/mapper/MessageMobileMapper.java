package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.MessageMobile;
@Repository
public interface MessageMobileMapper {
	/**
	 * <p>保存短信</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:44:22
	 * @param msg
	 * @see
	 */
	void save(MessageMobile msg);
	/**
	 * <p>根据手机号，验证码，项目编码查询短信息</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午3:26:42
	 * @param p
	 * @return
	 * @see
	 */
	List<MessageMobile> list(Map p);
	/**
	 * <p>根据手机号，token，项目编码查询短信息</p> 
	 * @author dongjun
	 * @date 2016年6月30日 下午2:40:02
	 * @param p
	 * @return
	 * @see
	 */
	List<MessageMobile> listByToken(Map p);
}
