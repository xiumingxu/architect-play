package com.xx.controller;


import com.xx.enums.YesOrNo;
import com.xx.pojo.Carousel;
import com.xx.pojo.Category;
import com.xx.pojo.vo.NewItemsVO;
import com.xx.service.CarouselService;
import com.xx.service.CategoryService;
import com.xx.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/*
 */
@Api(value="main page", tags={"The front page showcase apis"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    CarouselService carouselService;
    @Autowired
    CategoryService  categoryService;

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
    public JSONResult getSubCategories(){
        List<Category> categories = categoryService.queryAllRootLevelCategory();
        return JSONResult.ok(categories);
    }

    @ApiOperation(value="search for six new items", notes="search for six new items")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult getSixNewItemsLazy(
            @ApiParam(name="rootCatId", value="first category id", required=true)
            @PathVariable Integer rootCatId
    ){
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return JSONResult.ok(list);
    }

}
