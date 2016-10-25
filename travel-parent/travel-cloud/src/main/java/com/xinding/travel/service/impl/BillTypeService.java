package com.xinding.travel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xinding.travel.mapper.BillTypeMapper;
import com.xinding.travel.pojo.CompayBillType;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.pojo.PersonalBillType;
import com.xinding.travel.service.IBillTypeService;
import com.xinding.travel.util.BeanUtil;
@Service
public class BillTypeService implements IBillTypeService{

	@Autowired
	private BillTypeMapper billTypeMapper;
	/**
	 * 查询企业票据类型
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	@Override
	public PagedResult<CompayBillType> pages(Map p) {
		Integer pageNo = Integer.valueOf(String.valueOf(p.get("pageNo")));
		Integer pageSize = Integer.valueOf(String.valueOf(p.get("pageSize")));
 		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(billTypeMapper.compayBillTypeList(p));
	}
	/**
	 * 根据企业税号查询企业票据类型
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	@Override
	public List<CompayBillType> compayBillTypeListByTax(Map p) {
		// TODO Auto-generated method stub
		return billTypeMapper.compayBillTypeListByTax(p);
	}
	/**
	 * 企业票据类型新增
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	@Override
	public void addCompayBill(CompayBillType ct) {
		// TODO Auto-generated method stub
		billTypeMapper.addCompayBill(ct);
	}
	/**
	 * 企业票据类型修改
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	@Override
	public void updateCompayBill(CompayBillType ct) {
		// TODO Auto-generated method stub
		billTypeMapper.updateCompayBill(ct);
	}
	/**
	 * 企业票据类型删除
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	@Override
	public void deleteCompayBill(CompayBillType ct) {
		// TODO Auto-generated method stub
		billTypeMapper.deleteCompayBill(ct);
	}
	/**
	 * 个人票据类型
	 * @author dongjun
	 * @date 2016年7月5日 下午3:58:26
	 * @param p
	 * @return
	 * @see
	 */
	@Override
	public PagedResult<PersonalBillType> personalPages(Map p) {
		Integer pageNo = Integer.valueOf(String.valueOf(p.get("pageNo")));
		Integer pageSize = Integer.valueOf(String.valueOf(p.get("pageSize")));
 		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		return BeanUtil.toPagedResult(billTypeMapper.personalBillTypeList(p));
	}
	/**
	 * 个人票据类型新增
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	@Override
	public void addPersonalBill(PersonalBillType pt) {
		// TODO Auto-generated method stub
		billTypeMapper.addPersonalBill(pt);
	}
	/**
	 * 个人票据类型修改
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	@Override
	public void updatePersonalBill(PersonalBillType pt) {
		// TODO Auto-generated method stub
		billTypeMapper.updatePersonalBill(pt);
	}
	/**
	 * 个人票据类型删除
	 * @author dongjun
	 * @date 2016年7月7日 下午3:29:44
	 * @param ct
	 * @see
	 */
	@Override
	public void deletePersonalBill(PersonalBillType pt) {
		// TODO Auto-generated method stub
		billTypeMapper.deletePersonalBill(pt);
	}


}
