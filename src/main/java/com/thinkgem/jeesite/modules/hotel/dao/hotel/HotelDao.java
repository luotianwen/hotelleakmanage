/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.dao.hotel;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;

import java.util.List;

/**
 * 酒店管理DAO接口
 * @author 罗天文
 * @version 2019-10-06
 */
@MyBatisDao
public interface HotelDao extends CrudDao<Hotel> {

    List<Hotel> findGroup();
}