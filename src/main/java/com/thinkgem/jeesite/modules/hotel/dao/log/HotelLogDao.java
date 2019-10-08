/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.dao.log;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hotel.entity.log.HotelLog;

/**
 * 酒店日志DAO接口
 * @author 罗天文
 * @version 2019-10-05
 */
@MyBatisDao
public interface HotelLogDao extends CrudDao<HotelLog> {
	
}