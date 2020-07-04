package com.xx.controller;


import com.xx.pojo.bo.ShopcartBO;
import com.xx.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("shopcart")
@RestController
public class ShopcartController {

    @GetMapping("/add")
     public JSONResult addShopcartItems(
             @RequestParam String userId,
             @RequestBody  ShopcartBO shopcartBO,
             HttpServletRequest request,
             HttpServletResponse response){
        if(StringUtils.isBlank(userId))
            return JSONResult.errorMsg("User Id cannot be blank");
        //todo 前端用户在登录的情况下,添加商品到购物车, 会同时在后端同步购物车到 redis 缓存
        return JSONResult.ok();
    }

    @GetMapping("/del")
     public JSONResult deleteShopcartItems(
             @RequestParam String userId,
             @RequestParam String itemSpecId,
             HttpServletRequest request,
             HttpServletResponse response){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId))
            return JSONResult.errorMsg("Params cannot be blank");
        //TODO 用户在删除的数据, 若用户已经登录, 需要把后端的购物车的东西也删除
        return JSONResult.ok();
    }
}
