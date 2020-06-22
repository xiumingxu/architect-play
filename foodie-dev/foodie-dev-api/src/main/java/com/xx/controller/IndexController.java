package com.xx.controller;


import com.xx.enums.YesOrNo;
import com.xx.pojo.Carousel;
import com.xx.service.CarouselService;
import com.xx.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="main page", tags={"The front page showcase apis"})
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    CarouselService carouselService;

    @ApiOperation(value="frontPageCarouselList", notes="carousel list")
    @GetMapping("/carousel")
    public Object getCarousels(){
        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
        return  JSONResult.ok(carouselList);
    }

}
