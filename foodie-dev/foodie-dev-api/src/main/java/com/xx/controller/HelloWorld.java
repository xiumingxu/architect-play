package com.xx.controller;


import com.xx.enums.YesOrNo;
import com.xx.pojo.Carousel;
import com.xx.utils.JSONResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorld {

//    @ApiOperation(value="frontPageCarouselList", notes="carousel list")
//    @GetMapping("/carousel")
//    public Object getCarousels(){
//        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
//        return  JSONResult.ok(list);
//    }
}
