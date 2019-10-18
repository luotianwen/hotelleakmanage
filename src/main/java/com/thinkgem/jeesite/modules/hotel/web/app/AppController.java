package com.thinkgem.jeesite.modules.hotel.web.app;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hotel.entity.hotel.Hotel;
import com.thinkgem.jeesite.modules.hotel.entity.log.HotelLog;
import com.thinkgem.jeesite.modules.hotel.service.hotel.HotelService;
import com.thinkgem.jeesite.modules.hotel.service.log.HotelLogService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${frontPath}/app")
public class AppController extends BaseController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelLogService hotelLogService;
    @RequestMapping(value ="baojing" )
    @ResponseBody
    public ResponseData baojing(String hid ,int pageNo) {
        Page page1=new Page<HotelLog>();
        ResponseData<Page> d = new ResponseData();
        HotelLog hotelLog=new HotelLog();
        hotelLog.setH(new Hotel(hid));
        hotelLog.setType("1");
        hotelLog.setState("0");
        page1.setPageNo(pageNo);
        page1.setOrderBy("a.start_date desc ");
        Page<HotelLog> page = hotelLogService.findPage(page1, hotelLog);
        d.setData(page);
        return d;
    }
    @RequestMapping(value ="logupdate")
    @ResponseBody
    public ResponseData logupdate(String uid ,String name,String logId,String state) {
        ResponseData d = new ResponseData();
        HotelLog log=hotelLogService.get(logId);
        log.setState(state);
        log.setUpdateBy(new User(uid));
        log.setUpdateName(name);
        hotelLogService.save(log);
        return d;
    }

    @RequestMapping(value ="tishi")
    @ResponseBody
    public ResponseData tishi(String hid ,int pageNo) {
        Page page1=new Page<HotelLog>();
        ResponseData<Page> d = new ResponseData();
        HotelLog hotelLog=new HotelLog();
        hotelLog.setH(new Hotel(hid));
        hotelLog.setType("1");
        hotelLog.setState("0");
        page1.setPageNo(pageNo);
        page1.setOrderBy("a.start_date desc ");
        Page<HotelLog> page = hotelLogService.findPage(page1, hotelLog);
        d.setData(page);
        return d;
    }


    @RequestMapping(value ="login")
    @ResponseBody
    public ResponseData login( String name, String pwd ) {
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
        h.setUser(user);
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
    @RequestMapping(value ="forget")
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
