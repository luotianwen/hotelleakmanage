/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.log;

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
import com.thinkgem.jeesite.modules.hotel.entity.log.HotelLog;
import com.thinkgem.jeesite.modules.hotel.service.log.HotelLogService;

/**
 * 酒店日志Controller
 * @author 罗天文
 * @version 2019-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/log/hotelLog")
public class HotelLogController extends BaseController {

	@Autowired
	private HotelLogService hotelLogService;
	
	@ModelAttribute
	public HotelLog get(@RequestParam(required=false) String id) {
		HotelLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelLogService.get(id);
		}
		if (entity == null){
			entity = new HotelLog();
		}
		return entity;
	}
	
	@RequiresPermissions("hotel:log:hotelLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelLog hotelLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HotelLog> page = hotelLogService.findPage(new Page<HotelLog>(request, response), hotelLog); 
		model.addAttribute("page", page);
		return "modules/hotel/log/hotelLogList";
	}

	@RequiresPermissions("hotel:log:hotelLog:view")
	@RequestMapping(value = "form")
	public String form(HotelLog hotelLog, Model model) {
		model.addAttribute("hotelLog", hotelLog);
		return "modules/hotel/log/hotelLogForm";
	}

	@RequiresPermissions("hotel:log:hotelLog:edit")
	@RequestMapping(value = "save")
	public String save(HotelLog hotelLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hotelLog)){
			return form(hotelLog, model);
		}
		hotelLogService.save(hotelLog);
		addMessage(redirectAttributes, "保存酒店日志成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/log/hotelLog/?repage";
	}
	
	@RequiresPermissions("hotel:log:hotelLog:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelLog hotelLog, RedirectAttributes redirectAttributes) {
		hotelLogService.delete(hotelLog);
		addMessage(redirectAttributes, "删除酒店日志成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/log/hotelLog/?repage";
	}

}