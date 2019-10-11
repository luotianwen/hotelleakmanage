/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.service.check;

import com.ha.facecamera.configserver.pojo.Face;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hotel.dao.check.HotelCheckDao;
import com.thinkgem.jeesite.modules.hotel.entity.check.HotelCheck;
import com.thinkgem.jeesite.modules.hotel.entity.device.HotelDevice;
import com.thinkgem.jeesite.modules.hotel.face.FaceUtils;
import com.thinkgem.jeesite.modules.hotel.service.device.HotelDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.util.List;

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
		try {
			saveToFace(hotelCheck);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private HotelDeviceService hotelDeviceService;
	private void saveToFace(HotelCheck agent) throws Exception {
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
			f.setId("c"+agent.getNum());
			f.setName(agent.getName());
			f.setRole(1);
			f.setStartDate(agent.getStartDate());
			f.setExpireDate(agent.getEndDate());
			f.setJpgFileContent(new byte[][]{data});
			//f.setJpgFilePath(new String[]{"D:\\face\\192.168.1.80\\2019-10-08\\截图\\17_35_00.803951.jpg"});
			if(FaceUtils.listFace(f.getId(),hs1.getIp())){
				if(!FaceUtils.modifyFace(f, hs1.getIp())){
					throw new Exception("图片不合格");
				}

			}
			else {
				if (!FaceUtils.addFace(f, hs1.getIp())) {
					throw new Exception("图片不合格");
				}

			}

		}

	}
	
	@Transactional(readOnly = false)
	public void delete(HotelCheck hotelCheck) {
		super.delete(hotelCheck);
	}
	
}