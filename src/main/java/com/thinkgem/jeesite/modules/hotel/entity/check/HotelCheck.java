/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.entity.check;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 入住管理Entity
 * @author 罗天文
 * @version 2019-10-05
 */
public class HotelCheck extends DataEntity<HotelCheck> {
	
	private static final long serialVersionUID = 1L;
	private Hotel h;		// 酒店id
	private String num;		// 房间号
	private String name;		// 名称
	private String phos;		// 照片
	private Date endDate;		// 离开时间
	private Date startDate;		// 进来时间
	private String state;		// 开通状态
	
	public HotelCheck() {
		super();
	}

	public HotelCheck(String id){
		super(id);
	}

	public Hotel getH() {
		return h;
	}

	public void setH(Hotel h) {
		this.h = h;
	}
	
	@Length(min=0, max=255, message="房间号长度必须介于 0 和 255 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="照片长度必须介于 0 和 255 之间")
	public String getPhos() {
		return phos;
	}

	public void setPhos(String phos) {
		this.phos = phos;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=2, message="开通状态长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}