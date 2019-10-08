/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.hotel;

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
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.service.hotel.HotelService;

/**
 * 酒店管理Controller
 * @author 罗天文
 * @version 2019-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/hotel/hotel")
public class HotelController extends BaseController {

	@Autowired
	private HotelService hotelService;
	
	@ModelAttribute
	public Hotel get(@RequestParam(required=false) String id) {
		Hotel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelService.get(id);
		}
		if (entity == null){
			entity = new Hotel();
		}
		return entity;
	}
	
	@RequiresPermissions("hotel:hotel:hotel:view")
	@RequestMapping(value = {"list", ""})
	public String list(Hotel hotel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Hotel> page = hotelService.findPage(new Page<Hotel>(request, response), hotel); 
		model.addAttribute("page", page);
		return "modules/hotel/hotel/hotelList";
	}

	@RequiresPermissions("hotel:hotel:hotel:view")
	@RequestMapping(value = "form")
	public String form(Hotel hotel, Model model) {
		model.addAttribute("hotel", hotel);
		return "modules/hotel/hotel/hotelForm";
	}

	@RequiresPermissions("hotel:hotel:hotel:edit")
	@RequestMapping(value = "save")
	public String save(Hotel hotel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hotel)){
			return form(hotel, model);
		}
		hotelService.save(hotel);
		addMessage(redirectAttributes, "保存酒店管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/hotel/?repage";
	}
	
	@RequiresPermissions("hotel:hotel:hotel:edit")
	@RequestMapping(value = "delete")
	public String delete(Hotel hotel, RedirectAttributes redirectAttributes) {
		hotelService.delete(hotel);
		addMessage(redirectAttributes, "删除酒店管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/hotel/hotel/?repage";
	}

}