/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hotel.entity.device.HotelDevice;
import com.thinkgem.jeesite.modules.hotel.service.device.HotelDeviceService;

/**
 * 设备管理Controller
 * @author 罗天文
 * @version 2019-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/device/hotelDevice")
public class HotelDeviceController extends BaseController {

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
	
	@RequiresPermissions("hotel:device:hotelDevice:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelDevice hotelDevice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HotelDevice> page = hotelDeviceService.findPage(new Page<HotelDevice>(request, response), hotelDevice); 
		model.addAttribute("page", page);
		return "modules/hotel/device/hotelDeviceList";
	}

	@RequiresPermissions("hotel:device:hotelDevice:view")
	@RequestMapping(value = "form")
	public String form(HotelDevice hotelDevice, Model model) {
		model.addAttribute("hotelDevice", hotelDevice);
		return "modules/hotel/device/hotelDeviceForm";
	}

	@RequiresPermissions("hotel:device:hotelDevice:edit")
	@RequestMapping(value = "save")
	public String save(HotelDevice hotelDevice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hotelDevice)){
			return form(hotelDevice, model);
		}
		hotelDeviceService.save(hotelDevice);
		addMessage(redirectAttributes, "保存设备管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/device/hotelDevice/?repage";
	}
	
	@RequiresPermissions("hotel:device:hotelDevice:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelDevice hotelDevice, RedirectAttributes redirectAttributes) {
		hotelDeviceService.delete(hotelDevice);
		addMessage(redirectAttributes, "删除设备管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/device/hotelDevice/?repage";
	}

}