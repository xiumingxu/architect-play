package com.xx.controller;


import com.xx.pojo.Items;
import com.xx.pojo.ItemsImg;
import com.xx.pojo.ItemsParam;
import com.xx.pojo.ItemsSpec;
import com.xx.pojo.vo.ItemInfoVO;
import com.xx.service.ItemService;
import com.xx.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="item details page", tags={"query item images, params etc"})
@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value="Search for item details", httpMethod="GET")
    @GetMapping("/info/{itemId}")
    public JSONResult getItemDetails(
            @ApiParam(name="itemId", required = true)
            @PathVariable String itemId){
        if(StringUtils.isBlank(itemId))
            return JSONResult.errorMsg("Item Id cannot be blank");
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParams = itemService.queryItemParam(itemId);
        // return a VO, without any try catch
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemSpecList);
        itemInfoVO.setItemParams(itemsParams);

        return JSONResult.ok(itemInfoVO);
    }
}
