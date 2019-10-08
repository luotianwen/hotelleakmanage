/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.staff;

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
import com.thinkgem.jeesite.modules.hotel.entity.staff.HotelStaff;
import com.thinkgem.jeesite.modules.hotel.service.staff.HotelStaffService;

/**
 * 酒店员工Controller
 * @author 罗天文
 * @version 2019-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/staff/hotelStaff")
public class HotelStaffController extends BaseController {

	@Autowired
	private HotelStaffService hotelStaffService;
	
	@ModelAttribute
	public HotelStaff get(@RequestParam(required=false) String id) {
		HotelStaff entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelStaffService.get(id);
		}
		if (entity == null){
			entity = new HotelStaff();
		}
		return entity;
	}
	
	@RequiresPermissions("hotel:staff:hotelStaff:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelStaff hotelStaff, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HotelStaff> page = hotelStaffService.findPage(new Page<HotelStaff>(request, response), hotelStaff); 
		model.addAttribute("page", page);
		return "modules/hotel/staff/hotelStaffList";
	}

	@RequiresPermissions("hotel:staff:hotelStaff:view")
	@RequestMapping(value = "form")
	public String form(HotelStaff hotelStaff, Model model) {
		model.addAttribute("hotelStaff", hotelStaff);
		return "modules/hotel/staff/hotelStaffForm";
	}

	@RequiresPermissions("hotel:staff:hotelStaff:edit")
	@RequestMapping(value = "save")
	public String save(HotelStaff hotelStaff, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hotelStaff)){
			return form(hotelStaff, model);
		}
		hotelStaffService.save(hotelStaff);
		addMessage(redirectAttributes, "保存酒店员工成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/staff/hotelStaff/?repage";
	}
	
	@RequiresPermissions("hotel:staff:hotelStaff:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelStaff hotelStaff, RedirectAttributes redirectAttributes) {
		hotelStaffService.delete(hotelStaff);
		addMessage(redirectAttributes, "删除酒店员工成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/staff/hotelStaff/?repage";
	}

}