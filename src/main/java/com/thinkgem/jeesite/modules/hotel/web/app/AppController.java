package com.thinkgem.jeesite.modules.hotel.web.app;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.service.hotel.HotelService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.management.resources.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${frontPath}/app")
public class AppController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private HotelService hotelService;
    @RequestMapping(value ="login",method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData login(String name,String pwd ) {
        ResponseData d=new ResponseData();
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(pwd)){
            d.setCode("0");
            d.setMsg("用户名或者密码不能为空");
            return d;
        }
        User user= systemService.getUserByLoginName(name);
        if(null==user){
            d.setCode("0");
            d.setMsg("用户不存在");
            return d;
        }
        if (!SystemService.validatePassword(pwd, user.getPassword())){
            d.setCode("0");
            d.setMsg("密码错误");
            return d;
        }
        Hotel h=new Hotel();
        h.setUser(UserUtils.getUser());
        List<Hotel> list=hotelService.findList(h);
        if(list==null||list.size()!=1){
            d.setCode("0");
            d.setMsg("没有所属酒店请联系管理员");
            return d;
        }
        Map map=new HashMap<>();
        map.put("id",user.getId());
        map.put("name",user.getLoginName());
        map.put("hid",list.get(0).getId());
        d.setData(map);
        return d;
    }
    @RequestMapping(value ="forget",method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData forget(String id,String opwd ,String pwd) {
        ResponseData d=new ResponseData();
        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(pwd)||StringUtils.isEmpty(opwd)){
            d.setCode("0");
            d.setMsg("用户名或者密码不能为空");
            return d;
        }
        if (!opwd.equals(pwd)){
            d.setCode("0");
            d.setMsg("两次密码错误");
            return d;
        }
        User user= systemService.getUser(id);
        if(null==user){
            d.setCode("0");
            d.setMsg("用户不存在");
            return d;
        }
        if (!SystemService.validatePassword(opwd, user.getPassword())){
            d.setCode("0");
            d.setMsg("密码错误");
            return d;
        }
        user.setPassword(SystemService.entryptPassword(pwd));
        try {
            systemService.saveUser(user);
        }catch (Exception e){
            d.setCode("0");
            d.setMsg("设置密码出错了");
        }
        return d;
    }


}
