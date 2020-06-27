package com.xx.controller;


import com.xx.enums.YesOrNo;
import com.xx.pojo.Carousel;
import com.xx.pojo.Category;
import com.xx.service.CarouselService;
import com.xx.service.CategoryService;
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
    @Autowired
    CategoryService categoryService;

    @ApiOperation(value="frontPageCarouselList", notes="carousel list")
    @GetMapping("/carousel")
    public Object getCarousels(){
        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
        return  JSONResult.ok(carouselList);
    }

    @ApiOperation(value="categories root level")
    @GetMapping("/cats")
    public Object getCategories(){
        List<Category> categories = categoryService.queryAllRootLevelCategory();
        return  JSONResult.ok(categories);
    }

    @ApiOperation(value="categories sub level")
    @GetMapping("/cats/{catId}")
    public Object getSubCategories(){
        List<Category> categories = categoryService.queryAllRootLevelCategory();
        return  JSONResult.ok(categories);
    }

}
