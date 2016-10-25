package com.xinding.travel.pojo;

import java.sql.Timestamp;
/**
 * 
 * TODO(景点消费项目)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dongjun,date:2016年6月16日 下午3:42:44,content:TODO </p>
 * @author dongjun
 * @date 2016年6月16日 下午3:42:44
 * @since
 * @version
 */
public class ScenicProject {

	private Long id;
	/**
	 * 项目名称
	 */
	private String name;
	/**
	 * 图片
	 */
	private String firstPic;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 开始时间
	 */
	private Timestamp startDatetime;
	/**
	 * 结束时间
	 */
	private Timestamp endDatetime;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 驻户Id
	 */
	private Long customerId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 创建者Id
	 */
	private Long createUserId;
	/**
	 * 创建时间
	 */
	private Timestamp createDatetime;
	/**
	 * 修改者Id
	 */
	private Long lastUpdatedUserId;
	/**
	 * 修改时间
	 */
	private Timestamp lastUpdatedDatetime;
	/**
	  * @return  the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	  * @return  the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	  * @return  the firstPic
	 */
	public String getFirstPic() {
		return firstPic;
	}

	/**
	 * @param firstPic the firstPic to set
	 */
	public void setFirstPic(String firstPic) {
		this.firstPic = firstPic;
	}

	/**
	  * @return  the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	  * @return  the startDatetime
	 */
	public Timestamp getStartDatetime() {
		return startDatetime;
	}

	/**
	 * @param startDatetime the startDatetime to set
	 */
	public void setStartDatetime(Timestamp startDatetime) {
		this.startDatetime = startDatetime;
	}

	/**
	  * @return  the endDatetime
	 */
	public Timestamp getEndDatetime() {
		return endDatetime;
	}

	/**
	 * @param endDatetime the endDatetime to set
	 */
	public void setEndDatetime(Timestamp endDatetime) {
		this.endDatetime = endDatetime;
	}

	/**
	  * @return  the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	  * @return  the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	  * @return  the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	  * @return  the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	/**
	  * @return  the createDatetime
	 */
	public Timestamp getCreateDatetime() {
		return createDatetime;
	}

	/**
	 * @param createDatetime the createDatetime to set
	 */
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}

	/**
	  * @return  the lastUpdatedUserId
	 */
	public Long getLastUpdatedUserId() {
		return lastUpdatedUserId;
	}

	/**
	 * @param lastUpdatedUserId the lastUpdatedUserId to set
	 */
	public void setLastUpdatedUserId(Long lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}

	/**
	  * @return  the lastUpdatedDatetime
	 */
	public Timestamp getLastUpdatedDatetime() {
		return lastUpdatedDatetime;
	}

	/**
	 * @param lastUpdatedDatetime the lastUpdatedDatetime to set
	 */
	public void setLastUpdatedDatetime(Timestamp lastUpdatedDatetime) {
		this.lastUpdatedDatetime = lastUpdatedDatetime;
	}

}
