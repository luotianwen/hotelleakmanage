/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.m;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hotel.entity.device.HotelDevice;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.service.device.HotelDeviceService;
import com.thinkgem.jeesite.modules.hotel.service.hotel.HotelService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备管理Controller
 * @author 罗天文
 * @version 2019-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/m/hotelDevice")
public class MHotelDeviceController extends BaseController {

	@Autowired
	private HotelDeviceService hotelDeviceService;
	
	@ModelAttribute
	public HotelDevice get(@RequestParam(required=false) String id) {
		HotelDevice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelDeviceService.get(id);
		}
		if (entity == null){
			entity = new HotelDevice();
		}
		return entity;
	}
	@Autowired
	private HotelService hotelService;
	@RequiresPermissions("hotel:m:hotelDevice:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelDevice hotelDevice, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelDevice.setH(list.get(0));
		Page<HotelDevice> page = hotelDeviceService.findPage(new Page<HotelDevice>(request, response), hotelDevice); 
		model.addAttribute("page", page);
		return "modules/hotel/m/hotelDeviceList";
	}

	@RequiresPermissions("hotel:m:hotelDevice:view")
	@RequestMapping(value = "form")
	public String form(HotelDevice hotelDevice, Model model) {
		model.addAttribute("hotelDevice", hotelDevice);
		return "modules/hotel/m/hotelDeviceForm";
	}

	@RequiresPermissions("hotel:m:hotelDevice:edit")
	@RequestMapping(value = "save")
	public String save(HotelDevice hotelDevice, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, hotelDevice)){
			return form(hotelDevice, model);
		}Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelDevice.setH(list.get(0));
		hotelDeviceService.save(hotelDevice);
		addMessage(redirectAttributes, "保存设备管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelDevice/?repage";
	}
	
	@RequiresPermissions("hotel:m:hotelDevice:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelDevice hotelDevice, RedirectAttributes redirectAttributes) {
		hotelDeviceService.delete(hotelDevice);
		addMessage(redirectAttributes, "删除设备管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelDevice/?repage";
	}

}