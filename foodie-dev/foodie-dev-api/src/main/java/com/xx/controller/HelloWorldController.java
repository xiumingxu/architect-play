package com.xx.controller;


import com.xx.enums.YesOrNo;
import com.xx.pojo.Carousel;
import com.xx.utils.JSONResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class HelloWorldController {

//    @ApiOperation(value="frontPageCarouselList", notes="carousel list")
//    @GetMapping("/carousel")
//    public Object getCarousels(){
//        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
//        return  JSONResult.ok(list);
//    }
    @GetMapping("/setSession")
     public Object getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
//        session.removeAttribute("userInfo");
        return "ok";

    }
}
