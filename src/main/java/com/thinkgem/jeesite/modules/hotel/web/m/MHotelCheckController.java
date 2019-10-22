/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hotel.web.m;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hotel.entity.check.HotelCheck;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.entity.log.HotelLog;
import com.thinkgem.jeesite.modules.hotel.service.check.HotelCheckService;
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
 * 入住管理Controller
 * @author 罗天文
 * @version 2019-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hotel/m/hotelCheck")
public class MHotelCheckController extends BaseController {

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
	@Autowired
	private HotelService hotelService;
	@RequiresPermissions("hotel:m:hotelCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(HotelCheck hotelCheck, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelCheck.setH(list.get(0));
		Page<HotelCheck> page = hotelCheckService.findPage(new Page<HotelCheck>(request, response), hotelCheck); 
		model.addAttribute("page", page);
		return "modules/hotel/m/hotelCheckList";
	}

	@RequiresPermissions("hotel:m:hotelCheck:view")
	@RequestMapping(value = "form")
	public String form(HotelCheck hotelCheck, Model model) {
		model.addAttribute("hotelCheck", hotelCheck);
		return "modules/hotel/m/hotelCheckForm";
	}

	@Autowired
	private HotelLogService hotelLogService;
	@RequiresPermissions("hotel:m:hotelCheck:edit")
	@RequestMapping(value = "save")
	public String save(HotelCheck hotelCheck, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, hotelCheck)){
			return form(hotelCheck, model);
		}
		Hotel h=new Hotel();
		h.setUser(UserUtils.getUser());
		List<Hotel> list=hotelService.findList(h);
		if(list==null||list.size()!=1){
			throw new Exception("没有权限");
		}
		hotelCheck.setH(list.get(0));
		hotelCheckService.save(hotelCheck);


		HotelLog hl = new HotelLog();
		hl.setType("2");
		hl.setState("0");
		hl.setH(h);
		hl.setStartDate(hotelCheck.getStartDate());
		hl.setOutDate(hotelCheck.getEndOutDate());
		hl.setNum(hotelCheck.getNum());
		hl.setPto(hotelCheck.getPhos());
		hotelLogService.save(hl);


		addMessage(redirectAttributes, "保存入住管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelCheck/?repage";
	}
	
	@RequiresPermissions("hotel:m:hotelCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(HotelCheck hotelCheck, RedirectAttributes redirectAttributes) {
		hotelCheckService.delete(hotelCheck);
		addMessage(redirectAttributes, "删除入住管理成功");
		return "redirect:"+Global.getAdminPath()+"/hotel/m/hotelCheck/?repage";
	}

}