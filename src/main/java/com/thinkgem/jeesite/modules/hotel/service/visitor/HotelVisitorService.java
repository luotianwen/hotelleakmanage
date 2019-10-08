/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.service.visitor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hotel.entity.visitor.HotelVisitor;
import com.thinkgem.jeesite.modules.hotel.dao.visitor.HotelVisitorDao;

/**
 * 访客管理Service
 * @author 罗天文
 * @version 2019-10-05
 */
@Service
@Transactional(readOnly = true)
public class HotelVisitorService extends CrudService<HotelVisitorDao, HotelVisitor> {

	public HotelVisitor get(String id) {
		return super.get(id);
	}
	
	public List<HotelVisitor> findList(HotelVisitor hotelVisitor) {
		return super.findList(hotelVisitor);
	}
	
	public Page<HotelVisitor> findPage(Page<HotelVisitor> page, HotelVisitor hotelVisitor) {
		return super.findPage(page, hotelVisitor);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelVisitor hotelVisitor) {
		super.save(hotelVisitor);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelVisitor hotelVisitor) {
		super.delete(hotelVisitor);
	}
	
}