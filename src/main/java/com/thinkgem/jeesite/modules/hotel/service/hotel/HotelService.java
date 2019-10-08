/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.service.hotel;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hotel.dao.hotel.HotelDao;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 酒店管理Service
 * @author 罗天文
 * @version 2019-10-06
 */
@Service
@Transactional(readOnly = true)
public class HotelService extends CrudService<HotelDao, Hotel> {

	public Hotel get(String id) {
		return super.get(id);
	}
	
	public List<Hotel> findList(Hotel hotel) {
		return super.findList(hotel);
	}
	
	public Page<Hotel> findPage(Page<Hotel> page, Hotel hotel) {
		return super.findPage(page, hotel);
	}
	
	@Transactional(readOnly = false)
	public void save(Hotel hotel) {
		if("1".equals(hotel.getState())&& StringUtils.isEmpty(hotel.getUser().getId())){
			saveUser(hotel);
		}
		super.save(hotel);
	}
	private void saveUser(Hotel agent){
		User user=new User();
		user.setPassword(SystemService.entryptPassword(agent.getPass()));
		user.setLoginName(agent.getAccount());
		user.setCompany(new Office("1"));
		user.setOffice(new Office("2"));
		user.setName(agent.getName());
		List<Role> roleList = Lists.newArrayList();
		Role r=new Role();
		r.setId("c1ba08469db14a8da44bf1c1ae06e1a0");
		roleList.add(r);
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		agent.setUser(user);

	}
	@Autowired
	private SystemService systemService;
	@Transactional(readOnly = false)
	public void delete(Hotel hotel) {
		super.delete(hotel);
	}
	
}