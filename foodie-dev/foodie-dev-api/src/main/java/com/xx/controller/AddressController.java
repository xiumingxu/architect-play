package com.xx.controller;


import com.xx.pojo.bo.ShopcartBO;
import com.xx.service.AddressService;
import com.xx.utils.JSONResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value= "Address related: add del edit get")
@RequestMapping("address")
@RestController
public class AddressController {

    @Autowired AddressService addressService;

    @GetMapping("/list")
    public JSONResult getAddressList(
            @RequestParam String userId){
        if(StringUtils.isBlank(userId))
            return JSONResult.errorMsg("User Id cannot be blank");
        return JSONResult.ok(addressService.queryAll(userId));
    }

    @PostMapping("/list")
     public JSONResult addAddress(
             @RequestParam String userId,
             @RequestBody  ShopcartBO shopcartBO,
             HttpServletRequest request,
             HttpServletResponse response){
        if(StringUtils.isBlank(userId))
            return JSONResult.errorMsg("User Id cannot be blank");
        return JSONResult.ok();
    }

     @GetMapping("/del")
     public JSONResult deleteAddress(
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
