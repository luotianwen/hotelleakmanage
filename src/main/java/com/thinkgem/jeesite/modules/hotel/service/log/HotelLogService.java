/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.service.log;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hotel.entity.log.HotelLog;
import com.thinkgem.jeesite.modules.hotel.dao.log.HotelLogDao;

/**
 * 酒店日志Service
 * @author 罗天文
 * @version 2019-10-05
 */
@Service
@Transactional(readOnly = true)
public class HotelLogService extends CrudService<HotelLogDao, HotelLog> {

	public HotelLog get(String id) {
		return super.get(id);
	}
	
	public List<HotelLog> findList(HotelLog hotelLog) {
		return super.findList(hotelLog);
	}
	
	public Page<HotelLog> findPage(Page<HotelLog> page, HotelLog hotelLog) {
		return super.findPage(page, hotelLog);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelLog hotelLog) {
		super.save(hotelLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelLog hotelLog) {
		super.delete(hotelLog);
	}
	
}