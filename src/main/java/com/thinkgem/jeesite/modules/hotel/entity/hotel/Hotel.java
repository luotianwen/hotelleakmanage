/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.entity.hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 酒店管理Entity
 * @author 罗天文
 * @version 2019-10-06
 */
public class Hotel extends DataEntity<Hotel> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String lat;		// 经度
	private String lng;		// 纬度
	private Area province;		// 省
	private Area city;		// 市
	private Area area;		// 区
	private String address;		// 详细地址
	private String account;		// 账号
	private String pass;		// 密码
	private String leader;		// 负责人
	private String phone;		// 电话
	private String contract;		// 合同年限
	private String money;		// 缴费金额
	private Date startDate;		// 开通时间
	private String state;		// 开通状态
	private String eContract;		// 电子合同
	private String outTime;		// 访客离开时间
	private String remind;		// 提前提醒时间
	private User user;		// 用户
	private Date beginStartDate;		// 开始 开通时间
	private Date endStartDate;		// 结束 开通时间
	
	public Hotel() {
		super();
	}

	public Hotel(String id){
		super(id);
	}

	@Length(min=0, max=255, message="名称闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="经度闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	@Length(min=0, max=255, message="纬度闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}
	
	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="详细地址闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="账号闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Length(min=0, max=255, message="密码闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	@Length(min=0, max=255, message="负责人闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	@Length(min=0, max=255, message="电话闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="合同年限闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=2, message="开通状态闀垮害蹇呴』浠嬩簬 0 鍜� 2 涔嬮棿")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=255, message="电子合同闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getEContract() {
		return eContract;
	}

	public void setEContract(String eContract) {
		this.eContract = eContract;
	}
	
	public String  getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	
	@Length(min=0, max=255, message="提前提醒时间闀垮害蹇呴』浠嬩簬 0 鍜� 255 涔嬮棿")
	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		
}