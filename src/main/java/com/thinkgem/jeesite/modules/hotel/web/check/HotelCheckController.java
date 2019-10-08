/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.check;

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
import com.thinkgem.jeesite.modules.hotel.entity.check.HotelCheck;
import com.thinkgem.jeesite.modules.hotel.service.check.HotelCheckService;

/**
 * 入住管理Controller
 * @author 罗天文
 * @version 2019-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/check/hotelCheck")
public class HotelCheckController extends BaseController {

	@Autowired
	private HotelCheckService hotelCheckService;
	
	@ModelAttribute
	public HotelCheck get(@RequestParam(required=false) String id) {
		HotelCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelCheckService.get(id);
		}
		if (entity == null){
			entity = new HotelCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("hotel:check:hotelCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelCheck hotelCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HotelCheck> page = hotelCheckService.findPage(new Page<HotelCheck>(request, response), hotelCheck); 
		model.addAttribute("page", page);
		return "modules/hotel/check/hotelCheckList";
	}

	@RequiresPermissions("hotel:check:hotelCheck:view")
	@RequestMapping(value = "form")
	public String form(HotelCheck hotelCheck, Model model) {
		model.addAttribute("hotelCheck", hotelCheck);
		return "modules/hotel/check/hotelCheckForm";
	}

	@RequiresPermissions("hotel:check:hotelCheck:edit")
	@RequestMapping(value = "save")
	public String save(HotelCheck hotelCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hotelCheck)){
			return form(hotelCheck, model);
		}
		hotelCheckService.save(hotelCheck);
		addMessage(redirectAttributes, "保存入住管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/check/hotelCheck/?repage";
	}
	
	@RequiresPermissions("hotel:check:hotelCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelCheck hotelCheck, RedirectAttributes redirectAttributes) {
		hotelCheckService.delete(hotelCheck);
		addMessage(redirectAttributes, "删除入住管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/check/hotelCheck/?repage";
	}

}