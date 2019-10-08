/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.service.check;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hotel.entity.check.HotelCheck;
import com.thinkgem.jeesite.modules.hotel.dao.check.HotelCheckDao;

/**
 * 入住管理Service
 * @author 罗天文
 * @version 2019-10-05
 */
@Service
@Transactional(readOnly = true)
public class HotelCheckService extends CrudService<HotelCheckDao, HotelCheck> {

	public HotelCheck get(String id) {
		return super.get(id);
	}
	
	public List<HotelCheck> findList(HotelCheck hotelCheck) {
		return super.findList(hotelCheck);
	}
	
	public Page<HotelCheck> findPage(Page<HotelCheck> page, HotelCheck hotelCheck) {
		return super.findPage(page, hotelCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelCheck hotelCheck) {
		super.save(hotelCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelCheck hotelCheck) {
		super.delete(hotelCheck);
	}
	
}