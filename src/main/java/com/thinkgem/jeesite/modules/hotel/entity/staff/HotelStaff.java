/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.entity.staff;

import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒店员工Entity
 * @author 罗天文
 * @version 2019-10-06
 */
public class HotelStaff extends DataEntity<HotelStaff> {
	
	private static final long serialVersionUID = 1L;
	private Hotel h;		// 酒店id
	private String num;		// 序号
	private String name;		// 名称
	private String phos;		// 照片
	private String state;		// 开通状态
	private String account;		// 账号
	private String pass;		// 密码
	private String position;		// 职位
	private User user;		// 用户
	
	public HotelStaff() {
		super();
	}

	public HotelStaff(String id){
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
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}