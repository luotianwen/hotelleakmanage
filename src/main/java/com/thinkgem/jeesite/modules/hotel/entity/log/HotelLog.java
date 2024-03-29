/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.entity.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.hotel.entity.device.HotelDevice;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 * 酒店日志Entity
 * @author 罗天文
 * @version 2019-10-05
 */
public class HotelLog extends DataEntity<HotelLog> {
	
	private static final long serialVersionUID = 1L;
	private Hotel h;		// 酒店id
	private HotelDevice d;		// 设备id
	private String pto;		// 照片
	private String state;		// 处理状态
	private String updateName;		// 处理人名称
	private String type;		// 类型
	private String content;		// 内容
	private Date startDate;		// 进入时间
	private Date outDate;		// 离开时间
	private String num;		// 房间号
	private Date beginStartDate;		// 开始 进入时间
	private Date endStartDate;		// 结束 进入时间
	private Date beginOutDate;		// 开始 离开时间
	private Date endOutDate;		// 结束 离开时间

	public User getUpdateBy() {
		return updateBy;
	}

	public HotelLog() {
		super();
	}

	public HotelLog(String id){
		super(id);
	}

	public Hotel getH() {
		return h;
	}

	public void setH(Hotel h) {
		this.h = h;
	}
	
	public HotelDevice getD() {
		return d;
	}

	public void setD(HotelDevice d) {
		this.d = d;
	}
	
	public String getPto() {
		return pto;
	}

	public void setPto(String pto) {
		this.pto = pto;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
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
		
}