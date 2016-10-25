package com.xinding.travel.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.CompayBillType;
import com.xinding.travel.pojo.PersonalBillType;
@Repository
public interface BillTypeMapper {

	/**
	 * 查询企业票据类型
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	List<CompayBillType> compayBillTypeList(Map p);
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
	 * 查询企业票据类型新增
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void addCompayBill(CompayBillType ct);
	/**
	 * 查询企业票据类型修改
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	void updateCompayBill(CompayBillType ct);
	/**
	 * 查询企业票据类型删除
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
	List<PersonalBillType> personalBillTypeList(Map p);
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
