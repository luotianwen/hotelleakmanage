package com.thinkgem.jeesite.modules.hotel.web.face;

import com.thinkgem.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${frontPath}/face")
public class FaceController extends BaseController {
    @ResponseBody
    @RequestMapping(value = "tiao")
    public String tiao( HttpServletRequest request, HttpServletResponse response) {
         String data=readData(request);
        System.out.println(data);
         return "ok";
    }

}
