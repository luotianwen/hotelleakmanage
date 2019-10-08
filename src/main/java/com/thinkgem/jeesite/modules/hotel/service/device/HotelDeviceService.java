/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.service.device;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hotel.entity.device.HotelDevice;
import com.thinkgem.jeesite.modules.hotel.dao.device.HotelDeviceDao;

/**
 * 设备管理Service
 * @author 罗天文
 * @version 2019-10-05
 */
@Service
@Transactional(readOnly = true)
public class HotelDeviceService extends CrudService<HotelDeviceDao, HotelDevice> {

	public HotelDevice get(String id) {
		return super.get(id);
	}
	
	public List<HotelDevice> findList(HotelDevice hotelDevice) {
		return super.findList(hotelDevice);
	}
	
	public Page<HotelDevice> findPage(Page<HotelDevice> page, HotelDevice hotelDevice) {
		return super.findPage(page, hotelDevice);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelDevice hotelDevice) {
		super.save(hotelDevice);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelDevice hotelDevice) {
		super.delete(hotelDevice);
	}
	
}