/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.service.staff;

import com.google.common.collect.Lists;
import com.ha.facecamera.configserver.pojo.Face;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hotel.dao.staff.HotelStaffDao;
import com.thinkgem.jeesite.modules.hotel.entity.device.HotelDevice;
import com.thinkgem.jeesite.modules.hotel.entity.staff.HotelStaff;
import com.thinkgem.jeesite.modules.hotel.face.FaceUtils;
import com.thinkgem.jeesite.modules.hotel.service.device.HotelDeviceService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.util.List;

/**
 * 酒店员工Service
 * @author 罗天文
 * @version 2019-10-06
 */
@Service
@Transactional(readOnly = true)
public class HotelStaffService extends CrudService<HotelStaffDao, HotelStaff> {

	public HotelStaff get(String id) {
		return super.get(id);
	}
	
	public List<HotelStaff> findList(HotelStaff hotelStaff) {
		return super.findList(hotelStaff);
	}
	
	public Page<HotelStaff> findPage(Page<HotelStaff> page, HotelStaff hotelStaff) {
		return super.findPage(page, hotelStaff);
	}
	@Autowired
	private SystemService systemService;
	@Transactional(readOnly = false)
	public void save(HotelStaff hotelStaff)   {
		if("1".equals(hotelStaff.getState())&& StringUtils.isEmpty(hotelStaff.getUser().getId())){
			saveUser(hotelStaff);

		}
		super.save(hotelStaff);

		try {
			saveToFace(hotelStaff);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Autowired
	private HotelDeviceService hotelDeviceService;
	private void saveToFace(HotelStaff agent) throws Exception {
		HotelDevice hd=new HotelDevice();
		hd.setH(agent.getH());
		List<HotelDevice> hds=hotelDeviceService.findList(hd);
		Face f=new Face();
		String imgBase64 = agent.getPhos().replaceAll("data:image/png;base64,","");
		BASE64Decoder d = new BASE64Decoder();
		byte[] data = d.decodeBuffer(imgBase64);
		for (HotelDevice hs1:hds
			 ) {
			f.setCustom(agent.getId());
			f.setId("s"+agent.getNum());
			f.setName(agent.getName());
			f.setRole(1);
			f.setJpgFileContent(new byte[][]{data});
			//f.setJpgFilePath(new String[]{"D:\\face\\192.168.1.80\\2019-10-08\\截图\\17_35_00.803951.jpg"});
			if(FaceUtils.listFace(f.getId(),hs1.getIp())){
				if(FaceUtils.modifyFace(f, hs1.getIp())){

				}
				else {

				}
			}
			else {
				if (FaceUtils.addFace(f, hs1.getIp())) {

				}
				else{

				}
			}

		}

	}
	private void saveUser(HotelStaff agent){
		User user=new User();
		user.setPassword(SystemService.entryptPassword(agent.getPass()));
		user.setLoginName(agent.getAccount());
		user.setCompany(new Office("1"));
		user.setOffice(new Office("2"));
		user.setName(agent.getName());
		List<Role> roleList = Lists.newArrayList();
		Role r=new Role();
		r.setId("091721ec8d104003ab1c73dc48b69c30");
		roleList.add(r);
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		agent.setUser(user);

	}
	@Transactional(readOnly = false)
	public void delete(HotelStaff hotelStaff) {
		super.delete(hotelStaff);
	}
	
}