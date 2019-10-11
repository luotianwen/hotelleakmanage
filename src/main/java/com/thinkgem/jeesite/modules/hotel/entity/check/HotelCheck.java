/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.entity.check;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;

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
	private Date beginStartDate;		// 开始 进入时间
	private Date endStartDate;		// 结束 进入时间
	private Date beginOutDate;		// 开始 离开时间
	private Date endOutDate;		// 结束 离开时间

	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}

	public Date getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}

	public Date getBeginOutDate() {
		return beginOutDate;
	}

	public void setBeginOutDate(Date beginOutDate) {
		this.beginOutDate = beginOutDate;
	}

	public Date getEndOutDate() {
		return endOutDate;
	}

	public void setEndOutDate(Date endOutDate) {
		this.endOutDate = endOutDate;
	}

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
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
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
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}