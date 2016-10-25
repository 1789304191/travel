package com.xinding.travel.service;

import java.util.List;
import java.util.Map;

import com.xinding.travel.pojo.CompayBillType;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.PersonalBillType;

public interface IBillTypeService {
	/**
	 * 查询企业票据类型
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	PagedResult<CompayBillType> pages(Map p);
	
	/**
	 * 根据企业税号查询企业票据类型
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	List<CompayBillType> compayBillTypeListByTax(Map p);
	/**
	 * 企业票据类型新增
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void addCompayBill(CompayBillType ct);
	/**
	 * 企业票据类型修改
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void updateCompayBill(CompayBillType ct);
	/**
	 * 企业票据类型删除
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void deleteCompayBill(CompayBillType ct);
	/**
	 * 个人票据类型
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	PagedResult<PersonalBillType> personalPages(Map p);
	/**
	 * 个人票据类型新增
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void addPersonalBill(PersonalBillType pt);
	/**
	 * 个人票据类型修改
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void updatePersonalBill(PersonalBillType pt);
	/**
	 * 个人票据类型删除
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void deletePersonalBill(PersonalBillType pt);
}
