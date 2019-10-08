/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.m;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.entity.staff.HotelStaff;
import com.thinkgem.jeesite.modules.hotel.service.hotel.HotelService;
import com.thinkgem.jeesite.modules.hotel.service.staff.HotelStaffService;
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
 * 酒店员工Controller
 * @author 罗天文
 * @version 2019-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/m/hotelStaff")
public class MHotelStaffController extends BaseController {

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
	@Autowired
	private HotelService hotelService;
	@RequiresPermissions("hotel:m:hotelStaff:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelStaff hotelStaff, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelStaff.setH(list.get(0));
		Page<HotelStaff> page = hotelStaffService.findPage(new Page<HotelStaff>(request, response), hotelStaff); 
		model.addAttribute("page", page);
		return "modules/hotel/m/hotelStaffList";
	}

	@RequiresPermissions("hotel:m:hotelStaff:view")
	@RequestMapping(value = "form")
	public String form(HotelStaff hotelStaff, Model model) {
		model.addAttribute("hotelStaff", hotelStaff);
		return "modules/hotel/m/hotelStaffForm";
	}

	@RequiresPermissions("hotel:m:hotelStaff:edit")
	@RequestMapping(value = "save")
	public String save(HotelStaff hotelStaff, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, hotelStaff)){
			return form(hotelStaff, model);
		}
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelStaff.setH(list.get(0));
		hotelStaffService.save(hotelStaff);
		addMessage(redirectAttributes, "保存酒店员工成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelStaff/?repage";
	}
	
	@RequiresPermissions("hotel:m:hotelStaff:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelStaff hotelStaff, RedirectAttributes redirectAttributes) {
		hotelStaffService.delete(hotelStaff);
		addMessage(redirectAttributes, "删除酒店员工成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelStaff/?repage";
	}

}