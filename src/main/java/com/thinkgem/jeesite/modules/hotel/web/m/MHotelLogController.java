/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.m;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.entity.log.HotelLog;
import com.thinkgem.jeesite.modules.hotel.service.hotel.HotelService;
import com.thinkgem.jeesite.modules.hotel.service.log.HotelLogService;
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
 * 酒店日志Controller
 * @author 罗天文
 * @version 2019-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/m/hotelLog")
public class MHotelLogController extends BaseController {

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
	@Autowired
	private HotelService hotelService;
	@RequiresPermissions("hotel:m:hotelLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelLog hotelLog, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelLog.setH(list.get(0));
		Page<HotelLog> page = hotelLogService.findPage(new Page<HotelLog>(request, response), hotelLog); 
		model.addAttribute("page", page);
		return "modules/hotel/m/hotelLogList";
	}

	@RequiresPermissions("hotel:m:hotelLog:view")
	@RequestMapping(value = "form")
	public String form(HotelLog hotelLog, Model model) {
		model.addAttribute("hotelLog", hotelLog);
		return "modules/hotel/m/hotelLogForm";
	}

	@RequiresPermissions("hotel:m:hotelLog:edit")
	@RequestMapping(value = "save")
	public String save(HotelLog hotelLog, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, hotelLog)){
			return form(hotelLog, model);
		}Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelLog.setH(list.get(0));
		hotelLogService.save(hotelLog);
		addMessage(redirectAttributes, "保存酒店日志成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelLog/?repage";
	}
	@RequiresPermissions("hotel:m:hotelLog:edit")
	@RequestMapping(value = "allDeliver")
	public String allDeliver(String ids,  RedirectAttributes redirectAttributes) throws Exception {
		String ors[]=ids.split(",");
		for(String o:ors){
			hotelLogService.delete(new HotelLog(o));
		}

		addMessage(redirectAttributes, "保存酒店日志成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/log/hotelLog/?repage";
	}
	@RequiresPermissions("hotel:m:hotelLog:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelLog hotelLog, RedirectAttributes redirectAttributes) {
		hotelLogService.delete(hotelLog);
		addMessage(redirectAttributes, "删除酒店日志成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelLog/?repage";
	}

}