/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.m;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.entity.visitor.HotelVisitor;
import com.thinkgem.jeesite.modules.hotel.service.hotel.HotelService;
import com.thinkgem.jeesite.modules.hotel.service.visitor.HotelVisitorService;
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
 * 访客管理Controller
 * @author 罗天文
 * @version 2019-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/m/hotelVisitor")
public class MHotelVisitorController extends BaseController {

	@Autowired
	private HotelVisitorService hotelVisitorService;
	
	@ModelAttribute
	public HotelVisitor get(@RequestParam(required=false) String id) {
		HotelVisitor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelVisitorService.get(id);
		}
		if (entity == null){
			entity = new HotelVisitor();
		}
		return entity;
	}
	@Autowired
	private HotelService hotelService;
	@RequiresPermissions("hotel:m:hotelVisitor:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelVisitor hotelVisitor, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelVisitor.setH(list.get(0));
		Page<HotelVisitor> page = hotelVisitorService.findPage(new Page<HotelVisitor>(request, response), hotelVisitor); 
		model.addAttribute("page", page);
		return "modules/hotel/m/hotelVisitorList";
	}

	@RequiresPermissions("hotel:m:hotelVisitor:view")
	@RequestMapping(value = "form")
	public String form(HotelVisitor hotelVisitor, Model model) {
		model.addAttribute("hotelVisitor", hotelVisitor);
		return "modules/hotel/m/hotelVisitorForm";
	}

	@RequiresPermissions("hotel:m:hotelVisitor:edit")
	@RequestMapping(value = "save")
	public String save(HotelVisitor hotelVisitor, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, hotelVisitor)){
			return form(hotelVisitor, model);
		}
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelVisitor.setH(list.get(0));
		hotelVisitorService.save(hotelVisitor);
		addMessage(redirectAttributes, "保存访客管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelVisitor/?repage";
	}
	
	@RequiresPermissions("hotel:m:hotelVisitor:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelVisitor hotelVisitor, RedirectAttributes redirectAttributes) {
		hotelVisitorService.delete(hotelVisitor);
		addMessage(redirectAttributes, "删除访客管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelVisitor/?repage";
	}

}